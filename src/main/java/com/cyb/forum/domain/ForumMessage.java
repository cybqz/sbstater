package com.cyb.forum.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 论坛-消息DO
 */
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

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createDateTime;
}