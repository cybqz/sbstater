package com.cyb.authority.service.impl;

import com.cyb.authority.domain.CybAuthorityUser;
import com.cyb.authority.exception.UndefinedException;
import com.cyb.authority.service.CybAuthorityUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Set;

/**
 * 用户查询接口
 */
public class CybAuthorityUserServiceImpl implements CybAuthorityUserService {

    @Override
    public Set<String> queryRolesByName(String userName){
        try {
            throw new UndefinedException();
        } catch (UndefinedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Set<String> queryPermissionByName(String userName){
        try {
            throw new UndefinedException();
        } catch (UndefinedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<CybAuthorityUser> selectByName(String name){
        try {
            throw new UndefinedException();
        } catch (UndefinedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
