package com.cyb.authority.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @Author 陈迎博
 * @Description 权限模型
 * @Date 2021/1/21
 */
@Data
@AllArgsConstructor
@TableName(value = "permission")
public class Permission {

    @TableId(value = "id")
    private String id;

    @TableField(value = "name")
    private String name;

    @TableField(value = "remarks")
    private String remarks;
}