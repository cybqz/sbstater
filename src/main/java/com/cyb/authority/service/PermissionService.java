package com.cyb.authority.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyb.authority.dao.PermissionMapper;
import com.cyb.authority.domain.Permission;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

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

	public List<Permission> queryListByIds(List<String> idList) {

    	return permissionMapper.selectList(new QueryWrapper<Permission>().lambda().in(Permission::getId, idList));
	}
}