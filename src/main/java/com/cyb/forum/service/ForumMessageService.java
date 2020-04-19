package com.cyb.forum.service;

import com.cyb.common.pagination.Pagination;
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
     * @param pagination
     * @return
     */
    List<ForumMessage> selectSelective(ForumMessage forumMessage, Pagination pagination);
}
