package com.cyb.authority.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyb.authority.dao.SysModelMapper;
import com.cyb.authority.domain.SysModel;
import com.cyb.authority.domain.UserSysModel;
import com.cyb.common.pagination.Pagination;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
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
    private SysModelMapper sysModelMapper;

    @Resource
    private UserSysModelService userSysModelService;

    public int deleteById(String id) {
        return sysModelMapper.deleteById(id);
    }

    public int insert(SysModel record) {
        LocalDateTime now = LocalDateTime.now();
        record.setCreateDateTime(now);
        record.setUpdateDateTime(now);
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

    public int selectCount(SysModel sysModel){
        return sysModelMapper.selectCount(new LambdaQueryWrapper<SysModel>()
                .eq(StringUtils.isNotBlank(sysModel.getUrl()), SysModel::getUrl, sysModel.getUrl())
                .eq(StringUtils.isNotBlank(sysModel.getTitle()), SysModel::getTitle, sysModel.getTitle())
        );
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
     * @Title 分页查询用户未拥有系统模块
     * @Description 分页查询用户未拥有系统模块
     * @Date 2021/1/16
     */
    public IPage<SysModel> selectPageHavNo(String userId, Pagination pagination) {

        if(StringUtils.isNotBlank(userId)){

            LambdaQueryWrapper<SysModel> queryWrapper = new LambdaQueryWrapper<SysModel>()
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
    public IPage<SysModel> selectPage(SysModel record, Pagination pagination) {

        LambdaQueryWrapper<SysModel> queryWrapper = queryWrapper = new LambdaQueryWrapper<SysModel>();
        queryWrapper.orderByDesc(SysModel::getCreateDateTime);
        if(null != record){
            queryWrapper = queryWrapper.like(StringUtils.isNotBlank(record.getUrl()), SysModel::getUrl, record.getUrl());
            queryWrapper = queryWrapper.like(StringUtils.isNotBlank(record.getTitle()), SysModel::getTitle, record.getTitle());
        }

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
}
