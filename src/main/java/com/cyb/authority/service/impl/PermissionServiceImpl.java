package com.cyb.authority.service.impl;

import com.cyb.authority.dao.PermissionMapper;
import com.cyb.authority.domain.Permission;
import com.cyb.authority.service.PermissionService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

@Service(value="permissionService")
public class PermissionServiceImpl implements PermissionService {

	@Resource
	private PermissionMapper permissionMapper;

	@Override
    public int deleteByPrimaryKey(String id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insert(Permission record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insertSelective(Permission record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Permission selectByPrimaryKey(String id) {

		return permissionMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(Permission record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateByPrimaryKey(Permission record) {
		// TODO Auto-generated method stub
		return 0;
	}
}