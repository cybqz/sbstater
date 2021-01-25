package com.cyb.authority.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyb.authority.dao.UserRoleMapper;
import com.cyb.authority.domain.UserRole;
import com.cyb.common.pagination.Pagination;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;

/**
 * @Author 陈迎博
 * @Description 用户角色服务层
 * @Date 2021/1/21
 */
@Slf4j
@Service
public class UserRoleService extends ServiceImpl<UserRoleMapper, UserRole> {

	@Resource
	private UserRoleMapper userRoleMapper;

	public int deleteById(String id) {
		return userRoleMapper.deleteById(id);
	}

	public int insert(UserRole record) {
		return userRoleMapper.insert(record);
	}

	public UserRole selectById(String id) {
		return userRoleMapper.selectById(id);
	}

	public int selectCount(UserRole userRole){
		return userRoleMapper.selectCount(new QueryWrapper<UserRole>().lambda()
				.eq(StringUtils.isNotBlank(userRole.getRoleId()), UserRole::getRoleId, userRole.getRoleId())
				.eq(StringUtils.isNotBlank(userRole.getUserId()), UserRole::getUserId, userRole.getUserId())
		);
	}

	public UserRole selectOne(UserRole userRole){
		return userRoleMapper.selectOne(new QueryWrapper<UserRole>().lambda()
				.eq(StringUtils.isNotBlank(userRole.getRoleId()), UserRole::getRoleId, userRole.getRoleId())
				.eq(StringUtils.isNotBlank(userRole.getUserId()), UserRole::getUserId, userRole.getUserId())
		);
	}

	/**
	 * @Author 陈迎博
	 * @Title 分页查询
	 * @Description 分页查询
	 * @Date 2021/1/16
	 */
	public IPage<UserRole> selectPage(UserRole record, Pagination pagination) {

		LambdaQueryWrapper<UserRole> queryWrapper = queryWrapper = new LambdaQueryWrapper<UserRole>();
		queryWrapper.orderByDesc(UserRole::getCreateDateTime);
		if(null != record){
			queryWrapper = queryWrapper.like(StringUtils.isNotBlank(record.getUserId()), UserRole::getUserId, record.getUserId());
		}

		Page page = null;
		if(null != pagination){
			page = new Page(pagination.getPageIndex(), pagination.getLimit());
		}
		return userRoleMapper.selectPage(page, queryWrapper);
	}

	/**
	 * @Author 陈迎博
	 * @Title 查询用户已拥有角色
	 * @Description 查询用户已拥有角色
	 * @Date 2021/1/16
	 */
	public List<UserRole> selectByUserId(String userId) {
		if(StringUtils.isNotBlank(userId)){
			LambdaQueryWrapper<UserRole> queryWrapper = queryWrapper = new LambdaQueryWrapper<UserRole>()
					.orderByDesc(UserRole::getCreateDateTime)
					.eq(UserRole::getUserId, userId);
			return userRoleMapper.selectList(queryWrapper);
		}else{
			return null;
		}
	}
}