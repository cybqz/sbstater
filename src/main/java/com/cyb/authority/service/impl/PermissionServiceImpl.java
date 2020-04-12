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
		return permissionMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(Permission record) {
		return permissionMapper.insert(record);
	}

	@Override
	public int insertSelective(Permission record) {
		return permissionMapper.insertSelective(record);
	}

	@Override
	public Permission selectByPrimaryKey(String id) {
		return permissionMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(Permission record) {
		return permissionMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(Permission record) {
		return permissionMapper.updateByPrimaryKey(record);
	}
}