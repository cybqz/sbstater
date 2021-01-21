package com.cyb.authority.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyb.authority.domain.Role;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author 陈迎博
 * @Description 角色映射类
 * @Date 2021/1/21
 */
@Mapper
public interface RoleMapper  extends BaseMapper<Role> {
}