package com.cyb.authority.config;

import com.cyb.authority.service.PermissionService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

/**
 * @Author 陈迎博
 * @Description 权限服务层自动配置
 * @Date 2021/1/21
 */
@ConditionalOnClass(PermissionService.class)
public class PermissionServiceAutoConfig {

    @Bean
    @ConditionalOnMissingBean(PermissionService.class)
    public PermissionService permissionService(){
        PermissionService permissionService = new PermissionService();
        System.out.println(PermissionService.class + " init success!");
        return permissionService;
    }
}
