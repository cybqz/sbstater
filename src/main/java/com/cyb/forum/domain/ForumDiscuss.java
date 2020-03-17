package com.cyb.forum.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 论坛-评论DO
 */
@Data
public class ForumDiscuss {
    private String id;

    private String userId;

    private String messageId;

    private String content;

    private String img;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createDateTime;

}