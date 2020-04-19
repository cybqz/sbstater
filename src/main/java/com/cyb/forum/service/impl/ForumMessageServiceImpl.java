package com.cyb.forum.service.impl;

import com.cyb.common.pagination.Pagination;
import com.cyb.forum.dao.ForumDiscussMapper;
import com.cyb.forum.dao.ForumMessageMapper;
import com.cyb.forum.dao.ForumPraiseMapper;
import com.cyb.forum.domain.ForumMessage;
import com.cyb.forum.service.ForumMessageService;

import javax.annotation.Resource;
import java.util.List;

public class ForumMessageServiceImpl implements ForumMessageService {

    @Resource
    private ForumMessageMapper messageMapper;
    @Resource
    private ForumPraiseMapper praiseMapper;
    @Resource
    private ForumDiscussMapper discussMapper;

    @Override
    public int insert(ForumMessage forumMessage) {
        return messageMapper.insert(forumMessage);
    }

    @Override
    public int insertSelective(ForumMessage forumMessage) {

        return messageMapper.insertSelective(forumMessage);
    }

    @Override
    public int deleteByPrimaryKey(String id) {

        praiseMapper.deleteByForumMessageId(id);
        discussMapper.deleteByForumMessageId(id);
        return messageMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int countByForumMessage(ForumMessage forumMessage) {
        return messageMapper.countByForumMessage(forumMessage);
    }

    @Override
    public ForumMessage selectByPrimaryKey(String id) {
        return messageMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<ForumMessage> selectSelective(ForumMessage forumMessage, Pagination pagination) {

        if(null == forumMessage){
            forumMessage = new ForumMessage();
        }
        if(null == pagination){
            pagination = new Pagination(1, 10);
        }
        return messageMapper.selectSelective(forumMessage, pagination);
    }
}
