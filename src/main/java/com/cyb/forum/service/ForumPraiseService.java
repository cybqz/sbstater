package com.cyb.forum.service;

import com.cyb.common.pagination.Pagination;
import com.cyb.common.result.R;
import com.cyb.forum.domain.ForumPraise;

import java.util.List;

public interface ForumPraiseService {

    R insert(ForumPraise record);

    int insertSelective(ForumPraise record);

    int deleteByPrimaryKey(String id);

    ForumPraise selectByPrimaryKey(String id);

    List<ForumPraise> selectSelective(ForumPraise record, Pagination pagination);
}
