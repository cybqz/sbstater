package com.cyb.authority.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyb.authority.dao.RoleMapper;
import com.cyb.authority.domain.Permission;
import com.cyb.authority.domain.Role;
import com.cyb.authority.domain.UserRole;
import com.cyb.authority.vo.PermissionSearchVO;
import com.cyb.authority.vo.RoleSearchVO;
import com.cyb.common.pagination.Pagination;
import com.cyb.common.utils.WrapperUtil;
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
 * @Description 角色服务层
 * @Date 2021/1/21
 */
@Slf4j
@Service
public class RoleService extends ServiceImpl<RoleMapper, Role> {

    @Resource
    private RoleMapper roleMapper;

    @Resource
    private WrapperUtil wrapperUtil;

    @Resource
    private UserRoleService userRoleService;

    @Resource
    private RolePermissionService rolePermissionService;

    public int deleteById(String id) {
        rolePermissionService.deleteByRoleId(id);
        return roleMapper.deleteById(id);
    }

    public int insert(Role record) {
        record.setCreateDateTime(LocalDateTime.now());
        return roleMapper.insert(record);
    }

    public Role selectById(String id) {
        return roleMapper.selectById(id);
    }

    public Role selectByName(String name){
        if(StringUtils.isBlank(name)){
            return null;
        }else {
            return roleMapper.selectOne(new LambdaQueryWrapper<Role>().eq(Role::getName, name));
        }
    }

    public int selectCount(RoleSearchVO role){
        return roleMapper.selectCount(commonGetQueryWrapper(role));
    }

    /**
     * @Author 陈迎博
     * @Title 查询用户未拥有角色总数
     * @Description
     * @Date 2021/1/24
     */
    public int selectCountHavNo(String userId){
        if(StringUtils.isNotBlank(userId)){
            //查询用户已拥有的角色
            List<UserRole> userRoleList = userRoleService.selectByUserId(userId);
            if(!CollectionUtils.isEmpty(userRoleList)){

                List<String> roleIds = userRoleList.stream().map(UserRole::getRoleId).collect(Collectors.toList());
                return roleMapper.selectCount(new LambdaQueryWrapper<Role>().notIn(Role::getId, roleIds));
            }else{
                return roleMapper.selectCount(null);
            }
        }
        return 0;
    }

    /**
     * @Author 陈迎博
     * @Title 分页查询用户已拥有角色
     * @Description 分页查询用户已拥有角色
     * @Date 2021/1/16
     */
    public IPage<Role> selectPageHav(String userId, Pagination pagination) {

        if(StringUtils.isNotBlank(userId)){

            LambdaQueryWrapper<Role> queryWrapper = new LambdaQueryWrapper<Role>()
                    .orderByDesc(Role::getCreateDateTime);

            //查询用户已拥有的角色
            List<UserRole> userRoleList = userRoleService.selectByUserId(userId);
            if(!CollectionUtils.isEmpty(userRoleList)){

                List<String> roleIds = userRoleList.stream().map(UserRole::getRoleId).collect(Collectors.toList());
                queryWrapper.in(Role::getId, roleIds);
            }
            Page page = null;
            if(null != pagination){
                page = new Page(pagination.getPageIndex(), pagination.getLimit());
            }
            return roleMapper.selectPage(page, queryWrapper);
        }
        return new Page<Role>();
    }

    /**
     * @Author 陈迎博
     * @Title 分页查询用户未拥有角色
     * @Description 分页查询用户未拥有角色
     * @Date 2021/1/16
     */
    public IPage<Role> selectPageHavNo(String userId, Pagination pagination) {

        if(StringUtils.isNotBlank(userId)){

            LambdaQueryWrapper<Role> queryWrapper = new LambdaQueryWrapper<Role>()
                    .orderByDesc(Role::getCreateDateTime);

            //查询用户已拥有的角色
            List<UserRole> userRoleList = userRoleService.selectByUserId(userId);
            if(!CollectionUtils.isEmpty(userRoleList)){

                List<String> roleIds = userRoleList.stream().map(UserRole::getRoleId).collect(Collectors.toList());
                queryWrapper.notIn(Role::getId, roleIds);
            }
            Page page = null;
            if(null != pagination){
                page = new Page(pagination.getPageIndex(), pagination.getLimit());
            }
            return roleMapper.selectPage(page, queryWrapper);
        }
        return new Page<Role>();
    }

    /**
     * @Author 陈迎博
     * @Title 分页查询
     * @Description 分页查询
     * @Date 2021/1/16
     */
    public IPage<Role> selectPage(RoleSearchVO record, Pagination pagination) {

        LambdaQueryWrapper<Role> queryWrapper = commonGetQueryWrapper(record);
        Page page = null;
        if(null != pagination){
            page = new Page(pagination.getPageIndex(), pagination.getLimit());
        }
        return roleMapper.selectPage(page, queryWrapper);
    }

    public List<Role> selectListByUserId(String userId){

        //查询用户角色信息
        List<UserRole> userRoleList = userRoleService.selectByUserId(userId);
        if(!CollectionUtils.isEmpty(userRoleList)){

            List<String> roleIds = userRoleList.stream().map(UserRole::getRoleId).collect(Collectors.toList());
            //查询角色信息并返回结果
            List<Role> roleList = selectListByIds(roleIds);
            return roleList;
        }

        return null;
    }

    public List<Role> selectListByIds(List<String> idList){
        List<Role> roleList = roleMapper.selectList(new LambdaQueryWrapper<Role>().in(Role::getId, idList));
        return roleList;
    }

    private LambdaQueryWrapper commonGetQueryWrapper(RoleSearchVO role){

        LambdaQueryWrapper<Role> queryWrapper = new LambdaQueryWrapper<Role>()
                .orderByDesc(Role::getCreateDateTime);
        if(null != role){
            queryWrapper.like(StringUtils.isNotBlank(role.getName()), Role::getName, role.getName())
                    .like(StringUtils.isNotBlank(role.getRemarks()), Role::getRemarks, role.getRemarks());
            SFunction<Role, LocalDateTime> function = Role::getCreateDateTime;
            wrapperUtil.appendDateTime(queryWrapper, function, role.getDateTime());
        }

        return queryWrapper;
    }
}
