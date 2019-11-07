package com.cyb.authority.config;

import com.cyb.authority.service.CybAuthorityLoginService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

@ConditionalOnClass(CybAuthorityLoginService.class)
public class CybAuthorityLoginServiceAutoConfig {
	
	@Bean
	@ConditionalOnMissingBean(CybAuthorityLoginService.class)
	public CybAuthorityLoginService loginService() {
		CybAuthorityLoginService loginService = new CybAuthorityLoginService();
		System.out.println("cyb.authority.cybAuthorcityLoginService init success!");
		return loginService;
	}
}
