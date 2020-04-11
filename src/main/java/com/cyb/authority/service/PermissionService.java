package com.cyb.authority.service;

import com.cyb.authority.domain.Permission;

/**
 * 权限 Services
 */
public interface PermissionService {

    int deleteByPrimaryKey(String id);

    int insert(Permission record);

    int insertSelective(Permission record);

    Permission selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Permission record);

    int updateByPrimaryKey(Permission record);
}