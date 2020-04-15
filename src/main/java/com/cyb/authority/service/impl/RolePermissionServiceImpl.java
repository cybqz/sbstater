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
		return rolePermissionMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(RolePermission record) {
		return rolePermissionMapper.insert(record);
	}

	@Override
	public int insertSelective(RolePermission record) {
		return rolePermissionMapper.insertSelective(record);
	}

	@Override
	public RolePermission selectByPrimaryKey(String id) {
		return rolePermissionMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateBySelective(RolePermission record) {
		return rolePermissionMapper.updateBySelective(record);
	}

	@Override
	public int updateByPrimaryKey(RolePermission record) {
		return rolePermissionMapper.updateByPrimaryKey(record);
	}

	@Override
	public List<RolePermission> selectByRoleId(String roleId) {
		return rolePermissionMapper.selectByRoleId(roleId);
	}
}