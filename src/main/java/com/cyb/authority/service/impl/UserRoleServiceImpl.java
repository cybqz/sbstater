package com.cyb.authority.service.impl;

import com.cyb.authority.dao.UserRoleMapper;
import com.cyb.authority.domain.UserRole;
import com.cyb.authority.service.UserRoleService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;

@Service(value="userRoleService")
public class UserRoleServiceImpl implements UserRoleService {

	@Resource
	private UserRoleMapper userRoleMapper;

	@Override
	public int deleteByPrimaryKey(String id) {
		return userRoleMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(UserRole record) {
		return userRoleMapper.insert(record);
	}

	@Override
	public int insertSelective(UserRole record) {
		return userRoleMapper.insertSelective(record);
	}

	@Override
	public UserRole selectByPrimaryKey(String id) {
		return userRoleMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(UserRole record) {
		return userRoleMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(UserRole record) {
		return userRoleMapper.updateByPrimaryKey(record);
	}

	@Override
	public List<UserRole> selectByUserId(String id) {
		return userRoleMapper.selectByUserId(id);
	}
}