package com.cyb.authority.service;

import com.cyb.authority.domain.UserRole;
import java.util.List;

/**
 * 用户-角色 Services
 */
public interface UserRoleService {

    int deleteByPrimaryKey(String id);

    int insert(UserRole record);

    int insertSelective(UserRole record);

    UserRole selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(UserRole record);

    int updateByPrimaryKey(UserRole record);

    List<UserRole> selectByUserId(String id);
}