package com.cyb.forum.config;

import com.cyb.forum.service.ForumMessageService;
import com.cyb.forum.service.impl.ForumMessageServiceImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

@ConditionalOnClass(ForumMessageService.class)
public class CybForumMessageServiceAutoConfig {
	
	@Bean
	@ConditionalOnMissingBean(ForumMessageService.class)
	public ForumMessageService forumMessageService() {
		ForumMessageService forumMessageService = new ForumMessageServiceImpl();
		System.out.println("com.cyb.forum.service.ForumMessageService init success!");
		return forumMessageService;
	}
}
