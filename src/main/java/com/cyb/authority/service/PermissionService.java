package com.cyb.authority.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyb.authority.dao.PermissionMapper;
import com.cyb.authority.domain.Permission;
import com.cyb.authority.domain.RolePermission;
import com.cyb.common.pagination.Pagination;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author 陈迎博
 * @Description 权限服务层
 * @Date 2021/1/21
 */
@Slf4j
@Service(value="permissionService")
public class PermissionService extends ServiceImpl<PermissionMapper, Permission> {

	@Resource
	private PermissionMapper permissionMapper;

	@Resource
	private RolePermissionService rolePermissionService;

    public int deleteById(String id) {
		return permissionMapper.deleteById(id);
	}

	public int insert(Permission record) {
		return permissionMapper.insert(record);
	}

	public Permission selectById(String id) {
		return permissionMapper.selectById(id);
	}

	public Permission selectByName(String name){
    	if(StringUtils.isBlank(name)){
    		return null;
		}else {
    		return permissionMapper.selectOne(new LambdaQueryWrapper<Permission>().eq(Permission::getName, name));
		}
	}

	public int selectCount(Permission permission){
    	return permissionMapper.selectCount(new LambdaQueryWrapper<Permission>()
				.eq(StringUtils.isNotBlank(permission.getName()), Permission::getName, permission.getName())
		);
	}

	/**
	 * @Author 陈迎博
	 * @Title 分页查询
	 * @Description 分页查询
	 * @Date 2021/1/16
	 */
	public IPage<Permission> selectPage(Permission record, Pagination pagination) {

		LambdaQueryWrapper<Permission> queryWrapper = queryWrapper = new LambdaQueryWrapper<Permission>();
		queryWrapper.orderByDesc(Permission::getCreateDateTime);
		if(null != record){
			queryWrapper = queryWrapper.like(StringUtils.isNotBlank(record.getName()), Permission::getName, record.getName());
		}

		Page page = null;
		if(null != pagination){
			page = new Page(pagination.getPageIndex(), pagination.getLimit());
		}
		return permissionMapper.selectPage(page, queryWrapper);
	}

	/**
	 * @Author 陈迎博
	 * @Title 查询角色未拥有权限总数
	 * @Description
	 * @Date 2021/1/24
	 */
	public int selectCountHavNo(String roleId){
		if(StringUtils.isNotBlank(roleId)){
			//查询角色已拥有的权限
			List<RolePermission> rolePermissionList = rolePermissionService.selectByRoleId(roleId);
			if(!CollectionUtils.isEmpty(rolePermissionList)){

				List<String> permissionIds = new ArrayList<String>(rolePermissionList.size());
				for(RolePermission rp : rolePermissionList){
					permissionIds.add(rp.getPermissionId());
				}
				return permissionMapper.selectCount(new LambdaQueryWrapper<Permission>().notIn(Permission::getId, permissionIds));
			}else{
				return permissionMapper.selectCount(null);
			}
		}
		return 0;
	}

	/**
	 * @Author 陈迎博
	 * @Title 分页查询角色已拥有权限
	 * @Description 分页查询角色已拥有权限
	 * @Date 2021/1/16
	 */
	public IPage<Permission> selectPageHav(String roleId, Pagination pagination) {

		if(StringUtils.isNotBlank(roleId)){

			LambdaQueryWrapper<Permission> queryWrapper = new LambdaQueryWrapper<Permission>()
					.orderByDesc(Permission::getCreateDateTime);

			//查询角色已拥有的权限
			List<RolePermission> rolePermissionList = rolePermissionService.selectByRoleId(roleId);
			if(!CollectionUtils.isEmpty(rolePermissionList)){

				List<String> permissionIds = new ArrayList<String>(rolePermissionList.size());
				for(RolePermission rp : rolePermissionList){
					permissionIds.add(rp.getPermissionId());
				}
				queryWrapper.in(Permission::getId, permissionIds);
			}
			Page page = null;
			if(null != pagination){
				page = new Page(pagination.getPageIndex(), pagination.getLimit());
			}
			return permissionMapper.selectPage(page, queryWrapper);
		}
		return new Page<Permission>();
	}

	/**
	 * @Author 陈迎博
	 * @Title 分页查询角色未拥有权限
	 * @Description 分页查询角色未拥有权限
	 * @Date 2021/1/16
	 */
	public IPage<Permission> selectPageHavNo(String roleId, Pagination pagination) {

		if(StringUtils.isNotBlank(roleId)){

			LambdaQueryWrapper<Permission> queryWrapper = new LambdaQueryWrapper<Permission>()
					.orderByDesc(Permission::getCreateDateTime);

			//查询角色已拥有的权限
			List<RolePermission> rolePermissionList = rolePermissionService.selectByRoleId(roleId);
			if(!CollectionUtils.isEmpty(rolePermissionList)){

				List<String> permissionIds = new ArrayList<String>(rolePermissionList.size());
				for(RolePermission rp : rolePermissionList){
					permissionIds.add(rp.getPermissionId());
				}

				queryWrapper.notIn(Permission::getId, permissionIds);
			}
			Page page = null;
			if(null != pagination){
				page = new Page(pagination.getPageIndex(), pagination.getLimit());
			}
			return permissionMapper.selectPage(page, queryWrapper);
		}
		return new Page<Permission>();
	}

	public List<Permission> queryListByIds(List<String> idList) {

    	return permissionMapper.selectList(new LambdaQueryWrapper<Permission>().in(Permission::getId, idList));
	}
}