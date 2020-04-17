package com.cyb.forum.service.impl;

import com.cyb.common.pagenation.Pagenation;
import com.cyb.forum.dao.ForumDiscussMapper;
import com.cyb.forum.domain.ForumDiscuss;
import com.cyb.forum.service.ForumDiscussService;
import javax.annotation.Resource;
import java.util.List;

public class ForumDiscussServiceImpl implements ForumDiscussService {

    @Resource
    private ForumDiscussMapper forumDiscussMapper;

    @Override
    public int insert(ForumDiscuss record) {

        return forumDiscussMapper.insert(record);
    }

    @Override
    public int insertSelective(ForumDiscuss record) {

        return forumDiscussMapper.insertSelective(record);
    }

    @Override
    public int deleteByPrimaryKey(String id) {
        return forumDiscussMapper.deleteByPrimaryKey(id);
    }

    @Override
    public ForumDiscuss selectByPrimaryKey(String id) {
        return forumDiscussMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<ForumDiscuss> selectSelective(ForumDiscuss record, Pagenation pagenation) {
        if(null == record){
            record = new ForumDiscuss();
        }
        if(null == pagenation){
            pagenation = new Pagenation(1, 10);
        }
        return forumDiscussMapper.selectSelective(record, pagenation);
    }
}
