package com.cyb.authority.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyb.authority.dao.PermissionMapper;
import com.cyb.authority.domain.Permission;
import com.cyb.authority.domain.RolePermission;
import com.cyb.authority.vo.PermissionSearchVO;
import com.cyb.common.pagination.Pagination;
import com.cyb.common.utils.WrapperUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author 陈迎博
 * @Description 权限服务层
 * @Date 2021/1/21
 */
@Slf4j
@Service(value="permissionService")
public class PermissionService extends ServiceImpl<PermissionMapper, Permission> {

	@Resource
	private WrapperUtil wrapperUtil;

	@Resource
	private PermissionMapper permissionMapper;

	@Resource
	private RolePermissionService rolePermissionService;

    public int deleteById(String id) {
		return permissionMapper.deleteById(id);
	}

	public int insert(Permission record) {
		record.setCreateDateTime(LocalDateTime.now());
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

	public int selectCount(PermissionSearchVO permission){
    	return permissionMapper.selectCount(commonGetQueryWrapper(permission));
	}

	/**
	 * @Author 陈迎博
	 * @Title 分页查询
	 * @Description 分页查询
	 * @Date 2021/1/16
	 */
	public IPage<Permission> selectPage(PermissionSearchVO record, Pagination pagination) {

		LambdaQueryWrapper<Permission> queryWrapper = commonGetQueryWrapper(record);
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
				List<String> permissionIds = rolePermissionList.stream().map(RolePermission::getPermissionId).collect(Collectors.toList());
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

				List<String> permissionIds = rolePermissionList.stream().map(RolePermission::getPermissionId).collect(Collectors.toList());
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

				List<String> permissionIds = rolePermissionList.stream().map(RolePermission::getPermissionId).collect(Collectors.toList());
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

	private LambdaQueryWrapper commonGetQueryWrapper(PermissionSearchVO permission){

		LambdaQueryWrapper<Permission> queryWrapper = new LambdaQueryWrapper<Permission>()
				.orderByDesc(Permission::getCreateDateTime);
		if(null != permission){
			queryWrapper.like(StringUtils.isNotBlank(permission.getName()), Permission::getName, permission.getName())
						.like(StringUtils.isNotBlank(permission.getRemarks()), Permission::getRemarks, permission.getRemarks());
			SFunction<Permission, LocalDateTime> function = Permission::getCreateDateTime;
			wrapperUtil.appendDateTime(queryWrapper, function, permission.getDateTime());
		}

		return queryWrapper;
	}
}