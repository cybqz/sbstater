package com.cyb.authority.dao;

import com.cyb.authority.domain.RolePermission;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RolePermissionMapper {

    int deleteByPrimaryKey(String id);

    int insert(RolePermission record);

    int insertSelective(RolePermission record);

    RolePermission selectByPrimaryKey(String id);

    int updateBySelective(RolePermission record);

    int updateByPrimaryKey(RolePermission record);

    List<RolePermission> selectByRoleId(String roleId);
}