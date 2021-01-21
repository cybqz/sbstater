package com.cyb.authority.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyb.authority.dao.UserRoleMapper;
import com.cyb.authority.domain.UserRole;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;

/**
 * @Author 陈迎博
 * @Description 用户角色服务层
 * @Date 2021/1/21
 */
@Slf4j
@Service
public class UserRoleService extends ServiceImpl<UserRoleMapper, UserRole> {

	@Resource
	private UserRoleMapper userRoleMapper;

	public int deleteById(String id) {
		return userRoleMapper.deleteById(id);
	}

	public int insert(UserRole record) {
		return userRoleMapper.insert(record);
	}

	public UserRole selectById(String id) {
		return userRoleMapper.selectById(id);
	}

	public List<UserRole> selectByUserId(String userId) {
		return userRoleMapper.selectList(new QueryWrapper<UserRole>().lambda().eq(UserRole::getUserId, userId));
	}
}