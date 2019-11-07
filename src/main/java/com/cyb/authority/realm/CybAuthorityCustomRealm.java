package com.cyb.authority.realm;

import com.cyb.authority.domain.CybAuthorityUser;
import com.cyb.authority.service.CybAuthorityUserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

/**
 * @author chenyingbo
 */
public class CybAuthorityCustomRealm extends AuthorizingRealm {

	private static final Logger logger = LoggerFactory.getLogger(CybAuthorityCustomRealm.class);

    @Autowired
    private CybAuthorityUserService cybAuthorityUserService;
    
    /**
     * 用户授权认证
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        logger.info("======用户授权认证======");
        CybAuthorityUser user = (CybAuthorityUser) principalCollection.getPrimaryPrincipal();
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.setRoles(cybAuthorityUserService.queryRolesByName(user.getName()));
        simpleAuthorizationInfo.setStringPermissions(cybAuthorityUserService.queryPermissionByName(user.getName()));
        return simpleAuthorizationInfo;
    }
    /**
     * 用户登陆认证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        logger.info("======用户登陆认证======");
        String name = authenticationToken.getPrincipal().toString();
		List<CybAuthorityUser> users = cybAuthorityUserService.selectByName(name);
		if(users != null && users.size() > 0) {
            CybAuthorityUser user = users.get(0);
			AuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(user, user.getPassword(), "CustomRealm");
	        return authenticationInfo;
		}
        return null;
    }
}
