package com.cyb.authority.config;

import com.cyb.authority.service.RolePermissionService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

@ConditionalOnClass(RolePermissionService.class)
public class RolePermissionServiceAutoConfig {

    @Bean
    @ConditionalOnMissingBean(RolePermissionService.class)
    public RolePermissionService rolePermissionService(){
        RolePermissionService rolePermissionService = new RolePermissionService();
        System.out.println(RolePermissionService.class + " init success!");
        return rolePermissionService;
    }
}
