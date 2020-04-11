package com.cyb.authority.service;

import com.cyb.authority.domain.RolePermission;

import java.util.List;

/**
 * 角色-权限Services
 */
public interface RolePermissionService {

    int deleteByPrimaryKey(String id);

    int insert(RolePermission record);

    int insertSelective(RolePermission record);

    RolePermission selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(RolePermission record);

    int updateByPrimaryKey(RolePermission record);

    List<RolePermission> selectByRoleId(String roleId);
}