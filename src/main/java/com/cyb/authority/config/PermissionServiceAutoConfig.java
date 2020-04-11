package com.cyb.authority.config;

import com.cyb.authority.service.PermissionService;
import com.cyb.authority.service.impl.PermissionServiceImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

@ConditionalOnClass(PermissionService.class)
public class PermissionServiceAutoConfig {

    @Bean
    @ConditionalOnMissingBean(PermissionService.class)
    public PermissionService permissionService(){
        PermissionService permissionService = new PermissionServiceImpl();
        System.out.println(PermissionService.class + " init success!");
        return permissionService;
    }
}
