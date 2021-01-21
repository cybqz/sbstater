package com.cyb.authority.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyb.authority.domain.Permission;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author 陈迎博
 * @Description 权限映射类
 * @Date 2021/1/21
 */
@Mapper
public interface PermissionMapper extends BaseMapper<Permission> {
}