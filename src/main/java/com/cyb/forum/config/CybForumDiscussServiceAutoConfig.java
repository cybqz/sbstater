package com.cyb.forum.config;

import com.cyb.forum.service.ForumDiscussService;
import com.cyb.forum.service.impl.ForumDiscussServiceImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

@ConditionalOnClass(ForumDiscussService.class)
public class CybForumDiscussServiceAutoConfig {
	
	@Bean
	@ConditionalOnMissingBean(ForumDiscussService.class)
	public ForumDiscussService forumDiscussService() {
		ForumDiscussService forumDiscussService = new ForumDiscussServiceImpl();
		System.out.println("com.cyb.forum.service.ForumDiscussService init success!");
		return forumDiscussService;
	}
}
