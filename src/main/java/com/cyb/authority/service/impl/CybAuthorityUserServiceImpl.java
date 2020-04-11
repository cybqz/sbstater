package com.cyb.authority.service.impl;

import com.cyb.authority.dao.UserMapper;
import com.cyb.authority.domain.CybAuthorityUser;
import com.cyb.authority.domain.User;
import com.cyb.authority.exception.UndefinedException;
import com.cyb.authority.service.CybAuthorityUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 用户查询接口
 */
public class CybAuthorityUserServiceImpl implements CybAuthorityUserService {

    private static final Logger logger = LoggerFactory.getLogger(CybAuthorityUserService.class);

    @Resource
    private UserMapper userMapper;

    @Override
    public Set<String> queryRolesByName(String userName){
        return userMapper.queryRolesByName(userName);
    }

    @Override
    public Set<String> queryPermissionByName(String userName){
        return userMapper.queryPermissionByName(userName);
    }

    @Override
    public CybAuthorityUser selectByName(String userName){
        logger.info("start selectByName:\t" + userName);
        List<CybAuthorityUser> resultList = new ArrayList<CybAuthorityUser>();
        User user = userMapper.selectByUserName(userName);
        if(null != user){
            CybAuthorityUser cybAuthorityUser = new CybAuthorityUser();
            cybAuthorityUser.setName(user.getUserName());
            cybAuthorityUser.setPassword(user.getPassword());
            return cybAuthorityUser;
        }
        return null;
    }
}
