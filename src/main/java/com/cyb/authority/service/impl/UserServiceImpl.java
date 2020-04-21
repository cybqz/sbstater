package com.cyb.authority.service.impl;

import com.cyb.authority.dao.UserMapper;
import com.cyb.authority.domain.User;
import com.cyb.authority.service.LoginService;
import com.cyb.authority.service.UserService;
import com.cyb.authority.utils.EncryptionDecrypt;
import com.cyb.common.tips.Tips;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service(value="userSerivce")
public class UserServiceImpl implements UserService {
	
	@Resource
	private UserMapper userMapper;

	@Autowired
	private LoginService loginService;

	@Override
	public int deleteByPrimaryKey(String id) {
		return userMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(User record, String basePath) {
		String password = EncryptionDecrypt.encryptionMD5(record.getPassword());
		record.setPassword(password);
		Integer sex = record.getSex();
		String image = basePath + "/headportrait/";
		if(sex == 0) {
			image += "gril.png";
		}else {
			image += "boy.png";
		}
		record.setImage(image);
		userMapper.insert(record);
		return 1;
	}

	@Override
	public User selectByPrimaryKey(String id) {
		return userMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKey(User record) {
		return userMapper.updateByPrimaryKey(record);
	}

	@Override
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
			User user = userMapper.selectByPrimaryKey(userId);
			if(null == user){
				tips.setMsg("用户不存在");
			}else if(!user.getPassword().equals(encOldPassword)){
				tips.setMsg("旧密码不正确");
			}else if(encPassword.equals(encOldPassword)){
				tips.setMsg("新密码不能和旧密码相同");
			}else{
				user.setPassword(encPassword);
				int count = userMapper.updateByPrimaryKey(user);
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

	@Override
	public User selectByUserName(String userName) {
		return userMapper.selectByUserName(userName);
	}

	@Override
	public List<User> selectBySelective(User record){
		return userMapper.selectBySelective(record);
	}
}
