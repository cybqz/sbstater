package com.cyb.authority.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyb.authority.dao.UserSysModelMapper;
import com.cyb.authority.domain.UserSysModel;
import com.cyb.common.pagination.Pagination;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;

/**
 * @Author 陈迎博
 * @Description 用户系统模块服务层
 * @Date 2021/1/21
 */
@Slf4j
@Service
public class UserSysModelService extends ServiceImpl<UserSysModelMapper, UserSysModel> {

	@Resource
	private UserSysModelMapper userSysModelMapper;

	public int deleteById(String id) {
		return userSysModelMapper.deleteById(id);
	}

	/**
	 * @Author 陈迎博
	 * @Description 根据用户编号删除
	 * @Date 2021/1/30
	 */
	public int deleteByUserId(String userId) {
		if(StringUtils.isNotBlank(userId)){
			return userSysModelMapper.delete(new LambdaQueryWrapper<UserSysModel>().eq(UserSysModel::getId, userId));
		}
		return 0;
	}

	public int insert(UserSysModel record) {
		return userSysModelMapper.insert(record);
	}

	public UserSysModel selectById(String id) {
		return userSysModelMapper.selectById(id);
	}

	public int selectCount(UserSysModel sysModel){
		return userSysModelMapper.selectCount(new LambdaQueryWrapper<UserSysModel>()
				.eq(StringUtils.isNotBlank(sysModel.getSysModelId()), UserSysModel::getSysModelId, sysModel.getSysModelId())
				.eq(StringUtils.isNotBlank(sysModel.getUserId()), UserSysModel::getUserId, sysModel.getUserId())
		);
	}

	public UserSysModel selectOne(UserSysModel sysModel){
		return userSysModelMapper.selectOne(new LambdaQueryWrapper<UserSysModel>()
				.eq(StringUtils.isNotBlank(sysModel.getSysModelId()), UserSysModel::getSysModelId, sysModel.getSysModelId())
				.eq(StringUtils.isNotBlank(sysModel.getUserId()), UserSysModel::getUserId, sysModel.getUserId())
		);
	}

	/**
	 * @Author 陈迎博
	 * @Title 分页查询
	 * @Description 分页查询
	 * @Date 2021/1/16
	 */
	public IPage<UserSysModel> selectPage(UserSysModel record, Pagination pagination) {

		LambdaQueryWrapper<UserSysModel> queryWrapper = queryWrapper = new LambdaQueryWrapper<UserSysModel>();
		queryWrapper.orderByDesc(UserSysModel::getCreateDateTime);
		if(null != record){
			queryWrapper = queryWrapper.like(StringUtils.isNotBlank(record.getUserId()), UserSysModel::getUserId, record.getUserId());
		}

		Page page = null;
		if(null != pagination){
			page = new Page(pagination.getPageIndex(), pagination.getLimit());
		}
		return userSysModelMapper.selectPage(page, queryWrapper);
	}

	/**
	 * @Author 陈迎博
	 * @Title 查询用户已拥有系统模块
	 * @Description 查询用户已拥有系统模块
	 * @Date 2021/1/16
	 */
	public List<UserSysModel> selectBySysModelId(String userId) {
		if(StringUtils.isNotBlank(userId)){
			LambdaQueryWrapper<UserSysModel> queryWrapper = queryWrapper = new LambdaQueryWrapper<UserSysModel>()
					.orderByDesc(UserSysModel::getCreateDateTime)
					.eq(UserSysModel::getUserId, userId);
			return userSysModelMapper.selectList(queryWrapper);
		}else{
			return null;
		}
	}
}