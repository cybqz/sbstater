package com.cyb.forum.domain;

import lombok.Data;

import java.util.Date;

@Data
public class ForumMessage {
    private String id;

    private String userId;

    private String content;

    private String img;

    private Integer praiseCount;

    private Integer discussCount;

    private Integer collectCount;

    private Integer forwardCount;

    private Date createDateTime;
}