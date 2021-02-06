package com.cyb.authority.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @Author 陈迎博
 * @Description 角色模型
 * @Date 2021/1/21
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "sys_model")
public class SysModel {

    @TableId(value = "id")
    private String id;

    @TableField(value = "parent_id")
    private String parentId;

    @TableField(value = "title")
    private String title;

    @TableField(value = "navbar")
    private String navbar;

    @TableField(value = "url")
    private String url;

    @TableField(value = "sort")
    private Integer sort;

    @TableField(value = "remarks")
    private String remarks;

    @TableField(value = "create_date_time")
    private LocalDateTime createDateTime;

    @TableField(value = "update_date_time")
    private LocalDateTime updateDateTime;
}