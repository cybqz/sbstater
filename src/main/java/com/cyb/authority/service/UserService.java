package com.cyb.authority.service;

import com.cyb.authority.domain.User;
import com.cyb.common.tips.Tips;

import java.util.List;

/**
 * 用户 Services
 */
public interface UserService {

    int deleteByPrimaryKey(String id);

    int insert(User record, String basePath);

    User selectByPrimaryKey(String id);

    int updateByPrimaryKey(User record);

    Tips updatePasswordById(String userId, String password, String oldPassword, boolean againLogin);

    User selectByUserName(String userName);

    List<User> selectBySelective(User record);
}