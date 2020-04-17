package com.cyb.forum.dao;

import com.cyb.common.pagenation.Pagenation;
import com.cyb.forum.domain.ForumMessage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ForumMessageMapper {

    int insert(ForumMessage record);

    int insertSelective(ForumMessage record);

    int deleteByPrimaryKey(String id);

    int updatePraiseByPrimaryKey(ForumMessage forumMessage);

    int countByForumMessage(ForumMessage record);

    ForumMessage selectByPrimaryKey(String id);

    List<ForumMessage> selectSelective(@Param("forumMessage") ForumMessage forumMessage, Pagenation pagenation);

}