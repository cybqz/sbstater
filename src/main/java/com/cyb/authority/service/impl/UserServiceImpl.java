package com.cyb.authority.service.impl;

import com.cyb.authority.dao.UserMapper;
import com.cyb.authority.domain.User;
import com.cyb.authority.service.UserService;
import com.cyb.authority.utils.EncryptionDecrypt;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service(value="userSerivce")
public class UserServiceImpl implements UserService {
	
	@Resource
	private UserMapper userMapper;

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
	public User selectByUserName(String username) {
		return userMapper.selectByUserName(username);
	}

	@Override
	public List<User> selectBySelective(User record){
		return userMapper.selectBySelective(record);
	}
}
