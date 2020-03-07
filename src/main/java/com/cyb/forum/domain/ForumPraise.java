package com.cyb.forum.domain;

import lombok.Data;

import java.util.Date;

@Data
public class ForumPraise {
    private String id;

    private String userId;

    private String messageId;

    private Date createDateTime;
}