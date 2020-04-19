package com.cyb.forum.dao;

import com.cyb.common.pagination.Pagination;
import com.cyb.forum.domain.ForumDiscuss;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 评论
 */
@Mapper
public interface ForumDiscussMapper {

    int insert(ForumDiscuss forumDiscuss);

    int insertSelective(ForumDiscuss forumDiscuss);

    int deleteByPrimaryKey(String id);

    int deleteByForumMessageId(String messageId);

    ForumDiscuss selectByPrimaryKey(String id);

    List<ForumDiscuss> selectSelective(ForumDiscuss forumDiscuss, Pagination pagination);
}