package com.cyb.authority.config;

import com.cyb.authority.service.RoleService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

@ConditionalOnClass(RoleService.class)
public class RoleServiceAutoConfig {

    @Bean
    @ConditionalOnMissingBean(RoleService.class)
    public RoleService roleService(){
        RoleService roleService = new RoleService();
        System.out.println(RoleService.class + " init success!");
        return roleService;
    }
}
