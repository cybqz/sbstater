package com.cyb.forum.service;

import com.cyb.common.pagenation.Pagenation;
import com.cyb.forum.domain.ForumMessage;

import java.util.List;

public interface ForumMessageService {

    int insert(ForumMessage forumMessage);

    int insertSelective(ForumMessage forumMessage);

    int deleteByPrimaryKey(String id);

    int countByForumMessage(ForumMessage forumMessage);

    ForumMessage selectByPrimaryKey(String id);

    /**
     *
     * @param forumMessage
     * @param pagenation
     * @return
     */
    List<ForumMessage> selectSelective(ForumMessage forumMessage, Pagenation pagenation);
}
