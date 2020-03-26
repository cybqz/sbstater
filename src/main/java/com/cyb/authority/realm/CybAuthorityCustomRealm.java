package com.cyb.authority.realm;

import com.cyb.authority.domain.CybAuthorityUser;
import com.cyb.authority.service.CybAuthorityUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.session.mgt.ServletContainerSessionManager;
import org.apache.shiro.web.session.mgt.WebSessionKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.List;

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
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) {
        try{
            logger.info("\t\t\t======用户登陆认证======");
            String name = authenticationToken.getPrincipal().toString();
            String password = new String((char[])authenticationToken.getCredentials());
            CybAuthorityUser user = cybAuthorityUserService.selectByName(name);
            if(user != null) {
                DefaultWebSecurityManager  securityManager = (DefaultWebSecurityManager ) SecurityUtils.getSecurityManager();
                DefaultWebSessionManager sessionManager = (DefaultWebSessionManager) securityManager.getSessionManager();
                Collection<Session> sessions = sessionManager.getSessionDAO().getActiveSessions();//获取当前已登录的用户session列表
                System.out.println("Collection<Session>:\t" + sessions.size());
                for(Session session:sessions){
                    //清除该用户以前登录时保存的session
                    if(user.getName().equals(String.valueOf(session.getAttribute("SESSION_NAME")))) {
                        logger.info("\t\t\t         bdbd         ");
                        logger.info("\t\t\t         bdbd         ");
                        logger.info("\t\t\t         bdbd         ");
                        logger.info("\t\t\t"+user.getName()+":不明登陆");
                        //sessionManager.getSessionDAO().delete(session); 此段代码删除已登陆用户
                        return null;
                    }
                }
                logger.info("doGetAuthenticationInfo\t"+name+"\t"+password+"\t"+user.getPassword());
                AuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(user, user.getPassword(), "CustomRealm");
                return authenticationInfo;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
