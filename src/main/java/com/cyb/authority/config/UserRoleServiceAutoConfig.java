package com.cyb.authority.config;

import com.cyb.authority.service.UserRoleService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

@ConditionalOnClass(UserRoleService.class)
public class UserRoleServiceAutoConfig {

    @Bean
    @ConditionalOnMissingBean(UserRoleService.class)
    public UserRoleService userRoleService(){
        UserRoleService userRoleService = new UserRoleService();
        System.out.println(UserRoleService.class + " init success!");
        return userRoleService;
    }
}
