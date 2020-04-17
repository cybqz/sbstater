package com.cyb.forum.dao;

import com.cyb.common.pagenation.Pagenation;
import com.cyb.forum.domain.ForumPraise;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ForumPraiseMapper {

    int insert(ForumPraise record);

    int insertSelective(ForumPraise record);

    int deleteByPrimaryKey(String id);

    int deleteByForumMessageId(String messageId);

    ForumPraise selectOne(@Param("userId") String userId, @Param("messageId") String messageId);

    ForumPraise selectByPrimaryKey(String id);

    List<ForumPraise> selectSelective(ForumPraise forumPraise, Pagenation pagenation);
}