package com.cyb.forum.service.impl;

import com.cyb.common.pagination.Pagination;
import com.cyb.common.result.R;
import com.cyb.forum.dao.ForumMessageMapper;
import com.cyb.forum.dao.ForumPraiseMapper;
import com.cyb.forum.domain.ForumMessage;
import com.cyb.forum.domain.ForumPraise;
import com.cyb.forum.service.ForumPraiseService;

import javax.annotation.Resource;
import java.util.List;

public class ForumPraiseServiceImpl implements ForumPraiseService {

    @Resource
    private ForumPraiseMapper forumPraiseMapper;

    @Resource
    private ForumMessageMapper forumMessageMapper;

    @Override
    public R insert(ForumPraise record) {
        String userId = record.getUserId();
        String messageId = record.getMessageId();
        ForumPraise forumPraise = forumPraiseMapper.selectOne(userId, messageId);
        if(null == forumPraise){
            int insert = forumPraiseMapper.insert(record);
            if(insert > 0){

                ForumMessage forumMessage = forumMessageMapper.selectByPrimaryKey(messageId);
                forumMessage.setPraiseCount(forumMessage.getPraiseCount() + 1);
                int update = forumMessageMapper.updatePraiseByPrimaryKey(forumMessage);
                return  R.success("点成啦");
            }else{
                return R.fail("没点成");
            }
        }else{
            return R.fail("已经点过赞啦");
        }
    }

    @Override
    public int insertSelective(ForumPraise record) {
        return forumPraiseMapper.insertSelective(record);
    }

    @Override
    public int deleteByPrimaryKey(String id) {
        return forumPraiseMapper.deleteByPrimaryKey(id);
    }

    @Override
    public ForumPraise selectByPrimaryKey(String id) {
        return forumPraiseMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<ForumPraise> selectSelective(ForumPraise record, Pagination pagination) {

        if(null == record){
            record = new ForumPraise();
        }
        if(null == pagination){
            pagination = new Pagination(1, 10);
        }
        return forumPraiseMapper.selectSelective(record, pagination);
    }
}
