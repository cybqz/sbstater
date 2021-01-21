package com.cyb.authority.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyb.authority.dao.UserMapper;
import com.cyb.authority.domain.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @Author 陈迎博
 * @Description 用户服务层
 * @Date 2021/1/21
 */
@Slf4j
@Service
public class CybAuthorityUserService extends ServiceImpl<UserMapper, User> {

    @Resource
    private UserService userService;

    @Resource
    private RoleService roleService;

    @Resource
    private PermissionService permissionService;

    @Resource
    private RolePermissionService rolePermissionService;

    public Set<String> queryPermissionByUserName(String userName){

        //查询用户信息
        User user = userService.selectByUserName(userName);
        if(null != user) {

            //查询角色信息并返回结果
            List<Role> roleList = roleService.selectListByUserId(user.getId());
            if (!CollectionUtils.isEmpty(roleList)) {

                List<String> roleIds = new ArrayList<String>();
                for(Role role :roleList){
                    roleIds.add(role.getId());
                }

                //查询角色权限信息
                List<RolePermission> rolePermissionList = rolePermissionService.selectListByRoleIds(roleIds);
                if (!CollectionUtils.isEmpty(rolePermissionList)) {

                    List<String> idList = new ArrayList<String>();
                    for(RolePermission rp : rolePermissionList){
                        idList.add(rp.getPermissionId());
                    }

                    //查询权限信息并返回结果
                    List<Permission> permissionList = permissionService.queryListByIds(idList);
                    if (!CollectionUtils.isEmpty(permissionList)) {

                        Set<String> resultSet = new HashSet<String>();
                        for(Permission permission : permissionList){
                            resultSet.add(permission.getName());
                        }
                    }
                }
            }
        }
        return null;
    }

    /**
     * @Author 陈迎博
     * @Title 查询用户信息
     * @Description 更具用户姓名查询用户信息
     * @Date 2021/1/21
     */
    public CybAuthorityUser selectByUserName(String userName){
        log.info("start selectByName:\t" + userName);
        List<CybAuthorityUser> resultList = new ArrayList<CybAuthorityUser>();
        User user = userService.selectByUserName(userName);
        if(null != user){
            CybAuthorityUser cybAuthorityUser = new CybAuthorityUser();
            cybAuthorityUser.setName(user.getUserName());
            cybAuthorityUser.setPassword(user.getPassword());
            return cybAuthorityUser;
        }
        return null;
    }

    /**
     * @Author 陈迎博
     * @Title 根据用户名查询用户角色
     * @Description
     * @Date 2021/1/21
     */
    public Set<String> queryRolesByUserName(String userName) {

        //查询用户信息
        User user = userService.selectByUserName(userName);
        if(null != user){

            //查询角色信息并返回结果
            List<Role> roleList = roleService.selectListByUserId(user.getId());
            if(!CollectionUtils.isEmpty(roleList)){

                Set<String> resultSet = new HashSet<String>();
                for(Role role : roleList){
                    resultSet.add(role.getName());
                }
                return resultSet;
            }
        }
        return null;
    }
}
