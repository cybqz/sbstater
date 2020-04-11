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
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insert(UserRole record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insertSelective(UserRole record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public UserRole selectByPrimaryKey(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int updateByPrimaryKeySelective(UserRole record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateByPrimaryKey(UserRole record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<UserRole> selectByUserId(String id) {
		return null;
	}
}