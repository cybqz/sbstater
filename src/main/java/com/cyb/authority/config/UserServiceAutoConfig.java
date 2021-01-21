package com.cyb.authority.config;

import com.cyb.authority.service.UserService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

@ConditionalOnClass(UserService.class)
public class UserServiceAutoConfig {

    @Bean
    @ConditionalOnMissingBean(UserService.class)
    public UserService userService(){
        UserService userService = new UserService();
        System.out.println(UserService.class + " init success!");
        return userService;
    }
}
