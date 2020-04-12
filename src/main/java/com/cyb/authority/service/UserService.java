package com.cyb.authority.service;

import com.cyb.authority.domain.User;

/**
 * 用户 Services
 */
public interface UserService {

    int deleteByPrimaryKey(String id);

    int insert(User record, String basePath);

    User selectByPrimaryKey(String id);

    int updateByPrimaryKey(User record);

    User selectByUserName(String username);
}