package com.cyb.authority.service;

import com.cyb.authority.domain.CybAuthorityUser;
import java.util.List;
import java.util.Set;

/**
 * 用户查询接口
 */
public interface CybAuthorityUserService {

    public Set<String> queryRolesByName(String userName);

    public Set<String> queryPermissionByName(String userName);

    public List<CybAuthorityUser> selectByName(String name);
}
