package com.cyb.authority.dao;

import com.cyb.authority.domain.UserRole;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface UserRoleMapper {

    int insert(UserRole record);

    int insertSelective(UserRole record);

    int deleteByPrimaryKey(String id);

    int updateByPrimaryKeySelective(UserRole record);

    int updateByPrimaryKey(UserRole record);

    UserRole selectByPrimaryKey(String id);

    List<UserRole> selectByUserId(String userId);
}