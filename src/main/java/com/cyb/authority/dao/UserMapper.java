package com.cyb.authority.dao;

import com.cyb.authority.domain.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Set;

@Mapper
public interface UserMapper {

    int deleteByPrimaryKey(String id);

    void insert(User record);

    int updateByPrimaryKey(User record);

	Set<String> queryRolesByName(String userName);

	Set<String> queryPermissionByName(String userName);

    User selectByUserName(String userName);

    User selectByPrimaryKey(String id);

    List<User> selectBySelective(User record);
}