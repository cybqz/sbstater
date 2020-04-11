package com.cyb.authority.service.impl;

import com.cyb.authority.dao.RolePermissionMapper;
import com.cyb.authority.domain.RolePermission;
import com.cyb.authority.service.RolePermissionService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;

@Service(value="rolePermissionService")
public class RolePermissionServiceImpl implements RolePermissionService {

	@Resource
	private RolePermissionMapper rolePermissionMapper;

	@Override
    public int deleteByPrimaryKey(String id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insert(RolePermission record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insertSelective(RolePermission record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public RolePermission selectByPrimaryKey(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int updateByPrimaryKeySelective(RolePermission record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateByPrimaryKey(RolePermission record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<RolePermission> selectByRoleId(String roleId) {
		return null;
	}
}