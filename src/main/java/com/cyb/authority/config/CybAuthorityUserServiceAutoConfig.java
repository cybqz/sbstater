package com.cyb.authority.config;

import com.cyb.authority.service.CybAuthorityUserService;
import com.cyb.authority.service.impl.CybAuthorityUserServiceImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

@ConditionalOnClass(CybAuthorityUserService.class)
public class CybAuthorityUserServiceAutoConfig {

    @Bean
    @ConditionalOnMissingBean(CybAuthorityUserService.class)
    public CybAuthorityUserService cybAuthorityUserService(){
        CybAuthorityUserService cybAuthorityUserService = new CybAuthorityUserServiceImpl();
        System.out.println(CybAuthorityUserService.class + " init success!");
        return cybAuthorityUserService;
    }
}
