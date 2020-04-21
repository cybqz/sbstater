package com.cyb.authority.service;

import com.cyb.authority.domain.User;
import com.cyb.common.tips.Tips;

/**
 * 登陆接口
 */
public interface LoginService {


	/**
	 * 登入
	 * @param user
	 * @return
	 */
	public Tips login(User user);

	/**
	 * 管理员登陆
	 * @param user
	 * @param roleName
	 * @return
	 */
	Tips adminLogin(User user, String roleName);

	/**
	 * 登出
	 * @return
	 */
	public Tips logout();
}