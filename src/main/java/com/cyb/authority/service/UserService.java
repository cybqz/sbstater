package com.cyb.authority.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyb.authority.constant.SexEnum;
import com.cyb.authority.dao.UserMapper;
import com.cyb.authority.domain.User;
import com.cyb.authority.utils.EncryptionDecrypt;
import com.cyb.common.tips.Tips;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

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

	public int deleteById(String id) {
		return userMapper.deleteById(id);
	}

	public int insert(User record, String basePath) {
		String password = EncryptionDecrypt.encryptionMD5(record.getPassword());
		record.setPassword(password);
		SexEnum sex = record.getSex();
		String image = basePath + "/headportrait/";
		if(null == sex || sex.compareTo(SexEnum.WOMAN) == 0) {
			image += "gril.png";
		}else {
			image += "boy.png";
		}
		record.setImage(image);

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

	public User selectByUserName(String userName) {
		return userMapper.selectOne(new QueryWrapper<User>().lambda().eq(User::getUserName, userName));
	}
}
