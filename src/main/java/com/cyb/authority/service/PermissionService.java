package com.cyb.authority.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyb.authority.dao.PermissionMapper;
import com.cyb.authority.domain.Permission;
import com.cyb.common.pagination.Pagination;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
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
    		return permissionMapper.selectOne(new QueryWrapper<Permission>().lambda().eq(Permission::getName, name));
		}
	}

	public int selectCount(Permission permission){
    	return permissionMapper.selectCount(new QueryWrapper<Permission>().lambda()
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

	public List<Permission> queryListByIds(List<String> idList) {

    	return permissionMapper.selectList(new QueryWrapper<Permission>().lambda().in(Permission::getId, idList));
	}
}