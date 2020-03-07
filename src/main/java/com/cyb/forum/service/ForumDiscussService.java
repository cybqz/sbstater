package com.cyb.forum.service;

import com.cyb.forum.domain.ForumDiscuss;

import java.util.List;

/**
 * 评论
 */
public interface ForumDiscussService {

    int insert(ForumDiscuss record);

    int insertSelective(ForumDiscuss record);

    int deleteByPrimaryKey(String id);

    ForumDiscuss selectByPrimaryKey(String id);

    List<ForumDiscuss> selectSelective(ForumDiscuss record);
}
