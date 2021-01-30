package com.cyb.authority.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyb.authority.constant.SexEnum;
import com.cyb.authority.dao.UserMapper;
import com.cyb.authority.domain.User;
import com.cyb.authority.utils.EncryptionDecrypt;
import com.cyb.common.pagination.Pagination;
import com.cyb.common.tips.Tips;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * @Author 陈迎博
 * @Description 用户服务层
 * @Date 2021/1/21
 */
@Slf4j
@Service
public class UserService extends ServiceImpl<UserMapper, User> {
	
	@Resource
	private UserMapper userMapper;

	@Resource
	private LoginService loginService;

	@Resource
	private UserRoleService userRoleService;

	public int deleteById(String id) {
		int count = userMapper.deleteById(id);
		if(count > 0){
			userRoleService.deleteByUserId(id);
		}
		return count;
	}

	public int insert(User record, String basePath) {
		String password = EncryptionDecrypt.encryptionMD5(record.getPassword());
		record.setPassword(password);
		SexEnum sex = record.getSex();
		String image = basePath + "/headportrait/";
		if(null == sex || sex.compareTo(SexEnum.WOMAN) == 0) {
			image += "girl.png";
		}else {
			image += "boy.png";
		}
		record.setImage(image);
		record.setCreateDateTime(LocalDateTime.now());
		return userMapper.insert(record);
	}

	public User selectById(String id) {
		return userMapper.selectById(id);
	}

	public Tips updatePasswordById(String userId, String password, String oldPassword, boolean againLogin){
		Tips tips = new Tips("更新密码失败", true, false);
		if(StringUtils.isEmpty(userId)){
			tips.setMsg("用户不存在");
		}else if(StringUtils.isEmpty(oldPassword)){
			tips.setMsg("旧密码不能为空");
		}else if(StringUtils.isEmpty(password)){
			tips.setMsg("新密码不能为空");
		}else{
			String encOldPassword = EncryptionDecrypt.encryptionMD5(oldPassword);
			String encPassword = EncryptionDecrypt.encryptionMD5(password);
			User user = userMapper.selectById(userId);
			if(null == user){
				tips.setMsg("用户不存在");
			}else if(!user.getPassword().equals(encOldPassword)){
				tips.setMsg("旧密码不正确");
			}else if(encPassword.equals(encOldPassword)){
				tips.setMsg("新密码不能和旧密码相同");
			}else{
				user.setPassword(encPassword);
				user.setCreateDateTime(LocalDateTime.now());
				int count = userMapper.updateById(user);
				if(count > 0){
					if(againLogin){
						loginService.logout();
					}
					tips = new Tips("更新密码成功", true, true);
				}
			}
		}
		return tips;
	}

	/**
	 * @Author 陈迎博
	 * @Title 根据用户姓名查询用户信息
	 * @Description
	 * @Date 2021/1/22
	 */
	public User selectByUserName(String userName) {
		return userMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getUserName, userName));
	}

	/**
	 * @Author 陈迎博
	 * @Title 分页查询
	 * @Description 分页查询
	 * @Date 2021/1/16
	 */
	public IPage<User> selectPage(User record, Pagination pagination) {

		LambdaQueryWrapper<User> queryWrapper = queryWrapper = new LambdaQueryWrapper<User>();
		queryWrapper.orderByDesc(User::getCreateDateTime);
		if(null != record){
			queryWrapper = queryWrapper.like(StringUtils.isNotBlank(record.getUserName()), User::getUserName, record.getUserName());
		}

		Page page = null;
		if(null != pagination){
			page = new Page(pagination.getPageIndex(), pagination.getLimit());
		}
		return userMapper.selectPage(page, queryWrapper);
	}

	/**
	 * @Author 陈迎博
	 * @Title 根据条件查询用户总数
	 * @Description
	 * @Date 2021/1/22
	 */
	public int selectCount(User user){

		return userMapper.selectCount(new LambdaQueryWrapper<User>()
				.like(StringUtils.isNotBlank(user.getName()), User::getName, user.getName())
				.like(StringUtils.isNotBlank(user.getEmail()), User::getEmail, user.getEmail())
				.like(StringUtils.isNotBlank(user.getPhone()), User::getPhone, user.getPhone())
				.like(StringUtils.isNotBlank(user.getUserName()), User::getUserName, user.getUserName())
		);
	}
}
