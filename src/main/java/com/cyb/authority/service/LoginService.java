package com.cyb.authority.service;

import com.alibaba.fastjson.JSONObject;
import com.cyb.authority.domain.Role;
import com.cyb.authority.domain.User;
import com.cyb.authority.domain.UserRole;
import com.cyb.authority.utils.EncryptionDecrypt;
import com.cyb.common.tips.Tips;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * @Author 陈迎博
 * @Description 登录服务层
 * @Date 2021/1/21
 */
@Service
public class LoginService {

	@Resource
	private UserService userServices;

	@Resource
	private RoleService roleService;

	@Resource
	private UserRoleService userRoleService;

	public Tips login(User user) {
		Tips tips = new Tips("登陆失败", true, false);
		validateParam(user, tips);
		if(tips.isValidate()){
			tips.setValidate(false);
			JSONObject result = commonLogin(user);
			if(null != result && result.containsKey("authToken")){
				tips = new Tips("登录成功！", true, result);
			}else{
				tips = new Tips(result.getString("msg"), true, false);
			}
		}
		return tips;
	}

	public Tips adminLogin(User user, String roleName){

		Tips tips = new Tips("登陆失败", true, false);
		validateParam(user, tips);
		if(tips.isValidate()){
			tips.setValidate(false);
			User userTemp = userServices.selectByUserName(user.getUserName());
			if(null != userTemp){
				List<UserRole> userRoleList = userRoleService.selectByUserId(userTemp.getId());
				if(null != userRoleList && !userRoleList.isEmpty()){
					for(UserRole userRole : userRoleList){

						Role role = roleService.selectById(userRole.getRoleId());
						if(null != role && role.getName().equals(roleName)){

							JSONObject result = commonLogin(user);
							if(null != result && result.containsKey("authToken")){
								tips = new Tips("登录成功！", true, result);
							}else{
								tips.setData(result);
							}
						}
					}
				}
			}
		}
		return tips;
	}

	public Tips logout() {
		Tips tips = new Tips("没有登陆，退出失败", false);
		Subject subject = SecurityUtils.getSubject();
		if(subject.isAuthenticated()) {
			subject.logout();
			tips = new Tips("退出成功！", true);
		}
		return tips;
	}

	public Tips logoutWithToken(String authToken) {
		Tips tips = new Tips("没有登陆，退出失败", false);
		if(StringUtils.isEmpty(authToken)) {
			tips.setMsg("参数不能为空");
		}else {
			Subject subject = SecurityUtils.getSubject();
			SecurityManager securityManager = SecurityUtils.getSecurityManager();
			Serializable serializable = subject.getSession().getId();

			System.out.println(JSONObject.toJSONString(subject));
			System.out.println(JSONObject.toJSONString(serializable));
			System.out.println(JSONObject.toJSONString(securityManager));
			/*if(subject.isAuthenticated()) {
				subject.logout();
				tips = new Tips("退出成功！", true);
			}*/
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
		return loginResult;
	}

	private void validateParam(User user, Tips tips){

		if(StringUtils.isEmpty(user.getUserName())){
			tips.setMsg("用户名不能为空");
		}else if(StringUtils.isEmpty(user.getPassword())){
			tips.setMsg("密码不能为空");
		}else{
			tips.setValidate(true);
		}
	}

	/**
	 * @Author 陈迎博
	 * @Title 处理登录逻辑
	 * @Description 处理登录逻辑
	 * @Date 2021/1/22
	 */
	private JSONObject doLogin(String userName, String password){

		JSONObject result = new JSONObject();
		result.put("success", false);
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
		}catch (UnknownAccountException e){
			result.put("msg", "未找到用户信息");
		}catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
