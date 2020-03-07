package com.cyb.forum.service.impl;

import com.cyb.forum.dao.ForumMessageMapper;
import com.cyb.forum.domain.ForumMessage;
import com.cyb.forum.service.ForumMessageService;

import javax.annotation.Resource;
import java.util.List;

public class ForumMessageServiceImpl implements ForumMessageService {

    @Resource
    private ForumMessageMapper forumMessageMapper;

    @Override
    public int insert(ForumMessage forumMessage) {
        return forumMessageMapper.insert(forumMessage);
    }

    @Override
    public int insertSelective(ForumMessage forumMessage) {

        return forumMessageMapper.insertSelective(forumMessage);
    }

    @Override
    public int deleteByPrimaryKey(String id) {
        return forumMessageMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int countByForumMessage(ForumMessage forumMessage) {
        return forumMessageMapper.countByForumMessage(forumMessage);
    }

    @Override
    public ForumMessage selectByPrimaryKey(String id) {
        return forumMessageMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<ForumMessage> selectSelective(ForumMessage forumMessage,int pageIndex, int pageSize) {
        return forumMessageMapper.selectSelective(forumMessage, pageIndex, pageSize);
    }
}
