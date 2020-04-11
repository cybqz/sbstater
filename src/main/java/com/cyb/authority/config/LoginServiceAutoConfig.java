package com.cyb.authority.config;

import com.cyb.authority.service.LoginService;
import com.cyb.authority.service.impl.LoginServiceImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

@ConditionalOnClass(LoginService.class)
public class LoginServiceAutoConfig {

    @Bean
    @ConditionalOnMissingBean(LoginService.class)
    public LoginService loginService(){
        LoginService loginService = new LoginServiceImpl();
        System.out.println(LoginService.class + " init success!");
        return loginService;
    }
}
