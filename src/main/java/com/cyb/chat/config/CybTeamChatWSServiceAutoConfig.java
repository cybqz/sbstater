package com.cyb.chat.config;

import com.cyb.chat.server.CybTeamChatWSServer;
import com.cyb.chat.service.CybTeamChatWSService;
import com.cyb.chat.service.CybTeamChatWSServiceImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

@ConditionalOnClass(CybTeamChatWSService.class)
public class CybTeamChatWSServiceAutoConfig {
	
	@Bean
	@ConditionalOnMissingBean(CybTeamChatWSService.class)
	public CybTeamChatWSService cybTeamChatWSService() {
		CybTeamChatWSService cybTeamChatWSService = new CybTeamChatWSServiceImpl(new CybTeamChatWSServer());
		System.out.println("com.cyb.chat.service.CybTeamChatWSService init success!");
		return cybTeamChatWSService;
	}
}
