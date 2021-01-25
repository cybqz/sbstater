package com.cyb.authority.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyb.authority.dao.RoleMapper;
import com.cyb.authority.domain.Role;
import com.cyb.authority.domain.UserRole;
import com.cyb.common.pagination.Pagination;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

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
    private UserRoleService userRoleService;

    public int deleteById(String id) {
        return roleMapper.deleteById(id);
    }

    public int insert(Role record) {
        return roleMapper.insert(record);
    }

    public Role selectById(String id) {
        return roleMapper.selectById(id);
    }

    public Role selectByName(String name){
        if(StringUtils.isBlank(name)){
            return null;
        }else {
            return roleMapper.selectOne(new QueryWrapper<Role>().lambda().eq(Role::getName, name));
        }
    }

    public int selectCount(Role role){
        return roleMapper.selectCount(new QueryWrapper<Role>().lambda()
                .eq(StringUtils.isNotBlank(role.getName()), Role::getName, role.getName())
        );
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

                List<String> roleIds = new ArrayList<String>(userRoleList.size());
                for(UserRole ur : userRoleList){
                    roleIds.add(ur.getRoleId());
                }
                return roleMapper.selectCount(new QueryWrapper<Role>().lambda().notIn(Role::getId, roleIds));
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

                List<String> roleIds = new ArrayList<String>(userRoleList.size());
                for(UserRole ur : userRoleList){
                    roleIds.add(ur.getRoleId());
                }
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

                List<String> roleIds = new ArrayList<String>(userRoleList.size());
                for(UserRole ur : userRoleList){
                    roleIds.add(ur.getRoleId());
                }

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
    public IPage<Role> selectPage(Role record, Pagination pagination) {

        LambdaQueryWrapper<Role> queryWrapper = queryWrapper = new LambdaQueryWrapper<Role>();
        queryWrapper.orderByDesc(Role::getCreateDateTime);
        if(null != record){
            queryWrapper = queryWrapper.like(StringUtils.isNotBlank(record.getName()), Role::getName, record.getName());
        }

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

            List<String> idList = new ArrayList<String>(userRoleList.size());
            for(UserRole ur : userRoleList){
                idList.add(ur.getId());
            }

            //查询角色信息并返回结果
            List<Role> roleList = selectListByIds(idList);
            return roleList;
        }

        return null;
    }

    public List<Role> selectListByIds(List<String> idList){
        List<Role> roleList = roleMapper.selectList(new QueryWrapper<Role>().lambda().in(Role::getId, idList));
        return roleList;
    }
}
