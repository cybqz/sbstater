package com.cyb.authority.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyb.authority.dao.RolePermissionMapper;
import com.cyb.authority.domain.RolePermission;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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

	public int deleteByRoleId(String roleId) {

    	if(StringUtils.isNotBlank(roleId)){
    		return rolePermissionMapper.delete(new LambdaQueryWrapper<RolePermission>().eq(RolePermission::getRoleId, roleId));
		}
		return 0;
	}

	public int insert(RolePermission record) {
		return rolePermissionMapper.insert(record);
	}

	public RolePermission selectById(String id) {
		return rolePermissionMapper.selectById(id);
	}

	public List<RolePermission> selectListByRoleIds(List<String> roleIds) {
		return rolePermissionMapper.selectList(new LambdaQueryWrapper<RolePermission>().in(RolePermission::getRoleId, roleIds));
	}

	public int selectCount(RolePermission rolePermission){
		return rolePermissionMapper.selectCount(new LambdaQueryWrapper<RolePermission>()
				.eq(StringUtils.isNotBlank(rolePermission.getRoleId()), RolePermission::getRoleId, rolePermission.getRoleId())
				.eq(StringUtils.isNotBlank(rolePermission.getPermissionId()), RolePermission::getPermissionId, rolePermission.getPermissionId())
		);
	}

	public RolePermission selectOne(RolePermission rolePermission){
		return rolePermissionMapper.selectOne(new LambdaQueryWrapper<RolePermission>()
				.eq(StringUtils.isNotBlank(rolePermission.getRoleId()), RolePermission::getRoleId, rolePermission.getRoleId())
				.eq(StringUtils.isNotBlank(rolePermission.getPermissionId()), RolePermission::getPermissionId, rolePermission.getPermissionId())
		);
	}

	public List<RolePermission> selectByPermissionId(String permissionId) {
    	return rolePermissionMapper.selectList(new LambdaQueryWrapper<RolePermission>()
				.eq(RolePermission::getPermissionId, permissionId)
		);
	}

	public List<RolePermission> selectByRoleId(String roleId) {
    	return rolePermissionMapper.selectList(new LambdaQueryWrapper<RolePermission>()
				.eq(RolePermission::getRoleId, roleId)
		);
	}
}