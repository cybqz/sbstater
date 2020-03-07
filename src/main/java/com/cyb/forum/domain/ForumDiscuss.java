package com.cyb.forum.domain;

import lombok.Data;

import java.util.Date;

@Data
public class ForumDiscuss {
    private String id;

    private String userId;

    private String messageId;

    private String content;

    private String img;

    private Date createDateTime;

}