package com.cyb.forum.config;

import com.cyb.forum.service.ForumPraiseService;
import com.cyb.forum.service.impl.ForumPraiseServiceImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

@ConditionalOnClass(ForumPraiseService.class)
public class CybForumPraiseServiceAutoConfig {
	
	@Bean
	@ConditionalOnMissingBean(ForumPraiseService.class)
	public ForumPraiseService forumPraiseService() {
		ForumPraiseService forumPraiseService = new ForumPraiseServiceImpl();
		System.out.println("com.cyb.forum.service.ForumPraiseService init success!");
		return forumPraiseService;
	}
}
