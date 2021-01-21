package com.cyb.authority.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyb.authority.dao.RolePermissionMapper;
import com.cyb.authority.domain.RolePermission;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;

/**
 * @Author 陈迎博
 * @Description 角色权限服务层
 * @Date 2021/1/21
 */
@Slf4j
@Service
public class RolePermissionService extends ServiceImpl<RolePermissionMapper, RolePermission> {

	@Resource
	private RolePermissionMapper rolePermissionMapper;

    public int deleteById(String id) {
		return rolePermissionMapper.deleteById(id);
	}

	public int insert(RolePermission record) {
		return rolePermissionMapper.insert(record);
	}

	public RolePermission selectById(String id) {
		return rolePermissionMapper.selectById(id);
	}

	public List<RolePermission> selectListByRoleIds(List<String> roleIds) {
		return rolePermissionMapper.selectList(new QueryWrapper<RolePermission>().lambda().in(RolePermission::getRoleId, roleIds));
	}
}