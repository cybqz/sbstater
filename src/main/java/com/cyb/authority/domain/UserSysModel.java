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
 * @Description 用户模块模型
 * @Date 2021/1/21
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "user_sys_model")
public class UserSysModel {

    @TableId(value = "id")
    private String id;

    @TableField(value = "user_id")
    private String userId;

    @TableField(value = "sys_model_id")
    private String sysModelId;

    @TableField(value = "remarks")
    private String remarks;

    @TableField(value = "create_date_time")
    private LocalDateTime createDateTime;

    @TableField(value = "update_date_time")
    private LocalDateTime updateDateTime;
}