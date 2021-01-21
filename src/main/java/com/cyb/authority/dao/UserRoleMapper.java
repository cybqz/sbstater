package com.cyb.authority.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyb.authority.domain.UserRole;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

/**
 * @Author 陈迎博
 * @Description 用户角色映射类
 * @Date 2021/1/21
 */
@Mapper
public interface UserRoleMapper extends BaseMapper<UserRole> {
}