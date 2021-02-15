package com.cyb.authority.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyb.authority.dao.SysModelMapper;
import com.cyb.authority.domain.SysModel;
import com.cyb.authority.domain.UserSysModel;
import com.cyb.authority.vo.SysModelSearchVO;
import com.cyb.authority.vo.SysModelVO;
import com.cyb.common.pagination.Pagination;
import com.cyb.common.utils.WrapperUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author 陈迎博
 * @Description 系统模块服务层
 * @Date 2021/1/21
 */
@Slf4j
@Service
public class SysModelService extends ServiceImpl<SysModelMapper, SysModel> {

    @Resource
    private WrapperUtil wrapperUtil;

    @Resource
    private SysModelMapper sysModelMapper;

    @Resource
    private UserSysModelService userSysModelService;

    public int deleteById(String id) {
        return sysModelMapper.deleteById(id);
    }

    public int insert(SysModel record) {
        LocalDateTime now = LocalDateTime.now();
        record.setCreateDateTime(now);
        return sysModelMapper.insert(record);
    }

    public SysModel selectById(String id) {
        return sysModelMapper.selectById(id);
    }

    public SysModel selectByUrl(String url){
        if(StringUtils.isBlank(url)){
            return null;
        }else {
            return sysModelMapper.selectOne(new LambdaQueryWrapper<SysModel>().eq(SysModel::getUrl, url));
        }
    }

    public SysModel selectByTitle(String title){
        if(StringUtils.isBlank(title)){
            return null;
        }else {
            return sysModelMapper.selectOne(new LambdaQueryWrapper<SysModel>().eq(SysModel::getTitle, title));
        }
    }

    public int selectCount(SysModelSearchVO sysModel){
        return sysModelMapper.selectCount(commonGetQueryWrapper(sysModel));
    }

    public int selectParentCount(SysModelSearchVO sysModel){
        LambdaQueryWrapper<SysModel> queryWrapper = commonGetQueryWrapper(sysModel);
        queryWrapper.and(wrapper -> wrapper.isNull(SysModel::getUrl).or().eq(SysModel::getUrl,""));
        return sysModelMapper.selectCount(queryWrapper);
    }

    /**
     * @Author 陈迎博
     * @Title 查询用户未拥有系统模块总数
     * @Description
     * @Date 2021/1/24
     */
    public int selectCountHavNo(String userId){
        if(StringUtils.isNotBlank(userId)){
            //查询用户已拥有的系统模块
            List<UserSysModel> userSysModelList = userSysModelService.selectBySysModelId(userId);
            if(!CollectionUtils.isEmpty(userSysModelList)){

                List<String> sysModelIds = userSysModelList.stream().map(UserSysModel::getSysModelId).collect(Collectors.toList());
                return sysModelMapper.selectCount(new LambdaQueryWrapper<SysModel>().notIn(SysModel::getId, sysModelIds));
            }else{
                return sysModelMapper.selectCount(null);
            }
        }
        return 0;
    }

    /**
     * @Author 陈迎博
     * @Title 分页查询用户已拥有系统模块
     * @Description 分页查询用户已拥有系统模块
     * @Date 2021/1/16
     */
    public IPage<SysModel> selectPageHav(String userId, Pagination pagination) {

        if(StringUtils.isNotBlank(userId)){

            LambdaQueryWrapper<SysModel> queryWrapper = new LambdaQueryWrapper<SysModel>()
                    .orderByAsc(SysModel::getSort)
                    .orderByDesc(SysModel::getCreateDateTime);

            //查询用户已拥有的系统模块
            List<UserSysModel> userSysModelList = userSysModelService.selectBySysModelId(userId);
            if(!CollectionUtils.isEmpty(userSysModelList)){

                List<String> sysModelIds = userSysModelList.stream().map(UserSysModel::getSysModelId).collect(Collectors.toList());
                queryWrapper.in(SysModel::getId, sysModelIds);
            }
            Page page = null;
            if(null != pagination){
                page = new Page(pagination.getPageIndex(), pagination.getLimit());
            }
            return sysModelMapper.selectPage(page, queryWrapper);
        }
        return new Page<SysModel>();
    }

    /**
     * @Author 陈迎博
     * @Title 分页查询用户已拥有系统模块
     * @Description 分页查询用户已拥有系统模块
     * @Date 2021/1/16
     */
    public List<SysModelVO> selectListHav(String userId) {

        List<SysModelVO> resultList = null;
        if(StringUtils.isNotBlank(userId)){

            LambdaQueryWrapper<SysModel> queryWrapper = new LambdaQueryWrapper<SysModel>()
                    .orderByAsc(SysModel::getSort)
                    .orderByDesc(SysModel::getCreateDateTime);

            //查询用户已拥有的系统模块
            List<UserSysModel> userSysModelList = userSysModelService.selectBySysModelId(userId);
            if(!CollectionUtils.isEmpty(userSysModelList)){

                List<String> sysModelIds = userSysModelList.stream().map(UserSysModel::getSysModelId).collect(Collectors.toList());
                queryWrapper.in(SysModel::getId, sysModelIds);
                List<SysModel> sysModelList = sysModelMapper.selectList(queryWrapper);
                if(!CollectionUtils.isEmpty(sysModelList)){

                    resultList = new ArrayList<SysModelVO>();
                    List<SysModel> parentList = new ArrayList<SysModel>();

                    //遍历系统模块列表，组装返回结果
                    Map<String, List<SysModel>> tempMap = new HashMap<String, List<SysModel>>();
                    for(SysModel sysModel : sysModelList){

                        String key = sysModel.getParentId();
                        if(StringUtils.isBlank(key)){
                            if(!tempMap.containsKey(sysModel.getId())){
                                parentList.add(sysModel);
                                tempMap.put(sysModel.getId(), new ArrayList<SysModel>());
                            }
                        }
                    }

                    //添加子栏目
                    for(SysModel sysModel : sysModelList){
                        String key = sysModel.getParentId();
                        if(StringUtils.isNotBlank(key)){
                            if(tempMap.containsKey(key)){
                                List<SysModel> list = tempMap.get(key);
                                list.add(sysModel);
                                tempMap.put(key, list);
                            }
                        }
                    }

                    //组装返回结果
                    for(SysModel sysModel : parentList){
                        if(tempMap.containsKey(sysModel.getId())){
                            SysModelVO sysModelVO = new SysModelVO();
                            BeanUtils.copyProperties(sysModel, sysModelVO);
                            sysModelVO.setChildren(tempMap.get(sysModel.getId()));
                            resultList.add(sysModelVO);
                        }
                    }
                }
            }
        }
        return resultList;
    }

    /**
     * @Author 陈迎博
     * @Title 分页查询用户未拥有系统模块
     * @Description 分页查询用户未拥有系统模块
     * @Date 2021/1/16
     */
    public IPage<SysModel> selectPageHavNo(String userId, Pagination pagination) {

        if(StringUtils.isNotBlank(userId)){

            LambdaQueryWrapper<SysModel> queryWrapper = new LambdaQueryWrapper<SysModel>()
                    .orderByAsc(SysModel::getSort)
                    .orderByDesc(SysModel::getCreateDateTime);

            //查询用户已拥有的系统模块
            List<UserSysModel> userSysModelList = userSysModelService.selectBySysModelId(userId);
            if(!CollectionUtils.isEmpty(userSysModelList)){

                List<String> sysModelIds = userSysModelList.stream().map(UserSysModel::getSysModelId).collect(Collectors.toList());
                queryWrapper.notIn(SysModel::getId, sysModelIds);
            }
            Page page = null;
            if(null != pagination){
                page = new Page(pagination.getPageIndex(), pagination.getLimit());
            }
            return sysModelMapper.selectPage(page, queryWrapper);
        }
        return new Page<SysModel>();
    }

    /**
     * @Author 陈迎博
     * @Title 分页查询
     * @Description 分页查询
     * @Date 2021/1/16
     */
    public IPage<SysModel> selectPage(SysModelSearchVO record, Pagination pagination) {

        LambdaQueryWrapper<SysModel> queryWrapper = commonGetQueryWrapper(record);
        Page page = null;
        if(null != pagination){
            page = new Page(pagination.getPageIndex(), pagination.getLimit());
        }
        return sysModelMapper.selectPage(page, queryWrapper);
    }

    /**
     * @Author 陈迎博
     * @Title 分页查询
     * @Description 分页查询
     * @Date 2021/1/16
     */
    public IPage<SysModel> selectParentPage(SysModelSearchVO record, Pagination pagination) {

        LambdaQueryWrapper<SysModel> queryWrapper = commonGetQueryWrapper(record);

        //url为空则是父栏目
        queryWrapper.and(wrapper -> wrapper.isNull(SysModel::getUrl).or().eq(SysModel::getUrl,""));
        Page page = null;
        if(null != pagination){
            page = new Page(pagination.getPageIndex(), pagination.getLimit());
        }
        return sysModelMapper.selectPage(page, queryWrapper);
    }

    public List<SysModel> selectListByUserId(String userId){

        //查询用户系统模块信息
        List<UserSysModel> userSysModelList = userSysModelService.selectBySysModelId(userId);
        if(!CollectionUtils.isEmpty(userSysModelList)){

            List<String> sysModelIds = userSysModelList.stream().map(UserSysModel::getSysModelId).collect(Collectors.toList());
            //查询系统模块信息并返回结果
            List<SysModel> sysModelList = selectListByIds(sysModelIds);
            return sysModelList;
        }
        return null;
    }

    public List<SysModel> selectListByIds(List<String> idList){
        List<SysModel> sysModelList = sysModelMapper.selectList(new LambdaQueryWrapper<SysModel>().in(SysModel::getId, idList));
        return sysModelList;
    }

    private LambdaQueryWrapper commonGetQueryWrapper(SysModelSearchVO sysModel){

        LambdaQueryWrapper<SysModel> queryWrapper = new LambdaQueryWrapper<SysModel>()
                .orderByAsc(SysModel::getSort)
                .orderByDesc(SysModel::getCreateDateTime);
        if(null != sysModel){
            queryWrapper.like(StringUtils.isNotBlank(sysModel.getUrl()), SysModel::getUrl, sysModel.getUrl())
                    .like(StringUtils.isNotBlank(sysModel.getTitle()), SysModel::getTitle, sysModel.getTitle())
                    .like(StringUtils.isNotBlank(sysModel.getNavbar()), SysModel::getNavbar, sysModel.getNavbar())
                    .like(StringUtils.isNotBlank(sysModel.getRemarks()), SysModel::getRemarks, sysModel.getRemarks());
            SFunction<SysModel, LocalDateTime> function = SysModel::getCreateDateTime;
            wrapperUtil.appendDateTime(queryWrapper, function, sysModel.getDateTime());
        }
        return queryWrapper;
    }
}
