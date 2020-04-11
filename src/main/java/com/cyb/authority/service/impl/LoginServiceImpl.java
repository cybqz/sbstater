package com.cyb.authority.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.cyb.authority.dao.RoleMapper;
import com.cyb.authority.dao.UserRoleMapper;
import com.cyb.authority.domain.Role;
import com.cyb.authority.domain.User;
import com.cyb.authority.domain.UserRole;
import com.cyb.authority.service.LoginService;
import com.cyb.authority.service.UserService;
import com.cyb.authority.utils.EncryptionDecrypt;
import com.cyb.common.tips.Tips;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

@Service(value="loginService")
public class LoginServiceImpl implements LoginService {

	@Resource
	private UserService userServices;

	@Resource
	private RoleMapper roleMapper;

	@Resource
	private UserRoleMapper userRoleMapper;

	@Override
	public Tips login(User user) {
		Tips tips = new Tips("登陆失败", false);
		JSONObject result = commonLogin(user);
		if(null != result){
			tips = new Tips("登录成功！", true, result);
		}
		return tips;
	}

	@Override
	public Tips adminLogin(User user, String roleName){
		Tips tips = new Tips("登陆失败", false);

		if(StringUtils.isEmpty(user.getUserName())){
			tips.setMsg("用户名不能为空");
		}else{
			User userTemp = userServices.selectByUserName(user.getUserName());
			if(null != userTemp){
				List<UserRole> userRoleList = userRoleMapper.selectByUserId(userTemp.getId());
				if(null != userRoleList && !userRoleList.isEmpty()){
					for(UserRole userRole : userRoleList){

						Role role = roleMapper.selectByPrimaryKey(userRole.getRoleId());
						if(null != role && role.getName().equals(roleName)){

							JSONObject result = commonLogin(user);
							if(null != result){
								tips = new Tips("登录成功！", true, result);
								return tips;
							}
						}
					}
				}
			}
		}
		return tips;
	}

	@Override
	public Tips logout(User user) {
		Tips tips = new Tips("没有登陆，退出失败", false);
		Subject subject = SecurityUtils.getSubject();
		if(subject.isAuthenticated()) {
			subject.logout();
			tips = new Tips("退出成功！", true);
		}
		return tips;
	}

	private JSONObject commonLogin(User user){
		JSONObject loginResult = doLogin(user.getUserName(), user.getPassword());
		if(null != loginResult && loginResult.containsKey("authToken")){
			JSONObject result = new JSONObject();
			User resultUser = userServices.selectByUserName(user.getUserName());
			result.put("userId", resultUser.getId());
			result.put("userName", user.getUserName());
			result.put("authToken", loginResult.getString("authToken"));
			return result;
		}
		return null;
	}

	private JSONObject doLogin(String userName, String password){

		JSONObject result = new JSONObject();
		result.put("success", false);
		if(!org.springframework.util.StringUtils.isEmpty(userName) || !org.springframework.util.StringUtils.isEmpty(password)){
			Subject subject = SecurityUtils.getSubject();
			UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(userName, EncryptionDecrypt.encryptionMD5(password));
			try {
				subject.login(usernamePasswordToken);
				Object object = subject.getPrincipal();
				subject.getSession(true).setAttribute("SESSION_NAME", object);

				//token信息
				subject = SecurityUtils.getSubject();
				Serializable authToken = subject.getSession().getId();

				result.put("success", true);
				result.put("authToken", authToken);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		return result;
	}
}
