package com.cyb.authority.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyb.authority.domain.User;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import java.util.Set;

/**
 * @Author 陈迎博
 * @Description 用户映射类
 * @Date 2021/1/21
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}