package com.cyb.authority.config;

import com.cyb.authority.filter.CORSAuthenticationFilter;
import com.cyb.authority.properties.ShiroConfigurationProperties;
import com.cyb.authority.realm.CybAuthorityCustomRealm;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
@ComponentScan({"com.cyb.authority.properties"})
public class ShiroConfig {

    private static final Logger logger = LoggerFactory.getLogger(ShiroConfig.class);

    @Autowired
    private ShiroConfigurationProperties shiroConfigurationProperties;

    @Bean
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactory = new ShiroFilterFactoryBean();
        shiroFilterFactory.setSecurityManager(securityManager);
        if(null != shiroConfigurationProperties.getLoginUrl()){
            shiroFilterFactory.setLoginUrl(shiroConfigurationProperties.getLoginUrl());
        }

        if(null != shiroConfigurationProperties.getUnauthorizedUrl()){
            shiroFilterFactory.setUnauthorizedUrl(shiroConfigurationProperties.getUnauthorizedUrl());
        }

        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
        // <!-- authc:所有url都必须认证通过才可以访问; anon:所有url都都可以匿名访问-->
        //filterChainDefinitionMap.put("/webjars/**", "anon");

        //主要这行代码必须放在所有权限设置的最后，不然会导致所有 url 都被拦截 剩余的都需要认证
        //filterChainDefinitionMap.put("/**", "authc");
        shiroFilterFactory.setFilterChainDefinitionMap(filterChainDefinitionMap);

        //自定义拦截器
        Map<String, Filter> customFilterMap = new LinkedHashMap<>();
        customFilterMap.put("corsAuthenticationFilter", new CORSAuthenticationFilter());
        shiroFilterFactory.setFilters(customFilterMap);
        return shiroFilterFactory;
    }

    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher(){

        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        //加密方式
        hashedCredentialsMatcher.setHashAlgorithmName("md5");
        //加密次数
        //hashedCredentialsMatcher.setHashIterations(1);
        //存储散列后的密码是否为16进制
        //hashedCredentialsMatcher.isStoredCredentialsHexEncoded();
        return hashedCredentialsMatcher;
    }

    //加入注解的使用，不加入这个注解不生效
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager defaultSecurityManager = new DefaultWebSecurityManager();
        defaultSecurityManager.setRealm(customRealm());
        defaultSecurityManager.setSessionManager(sessionManager());
        return defaultSecurityManager;
    }

    @Bean
    public CybAuthorityCustomRealm customRealm() {
        CybAuthorityCustomRealm customRealm = new CybAuthorityCustomRealm();
        return customRealm;
    }

    /**
     * shiro缓存管理器
     * 在securityManager 中注册缓存管理器，之后就不会每次都会去查询数据库了，
     * 相关的权限和角色会保存在缓存中，但需要注意一点，更新了权限等操作之后，需要及时的清理缓存
     */
    @Bean
    public EhCacheManager ehCacheManager() {
        EhCacheManager cacheManager = new EhCacheManager();
        cacheManager.setCacheManagerConfigFile("classpath:config/ehcache.xml");
        return cacheManager;
    }

    @Bean
    public SessionManager sessionManager(){
        CybShiroSessionManager shiroSessionManager = new CybShiroSessionManager();
        //这里可以不设置。Shiro有默认的session管理。如果缓存为Redis则需改用Redis的管理
        //shiroSessionManager.setSessionDAO(new EnterpriseCacheSessionDAO());
        return shiroSessionManager;
    }
}
