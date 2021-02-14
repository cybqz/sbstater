package com.cyb.authority.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyb.common.validation.group.AddValid;
import com.cyb.common.validation.group.UpdateValid;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

/**
 * @Author 陈迎博
 * @Description 用户角色模型
 * @Date 2021/1/21
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "user_role")
public class UserRole {

    @TableId(value = "id")
    @NotBlank(message = "ID不能为空", groups = {UpdateValid.class})
    private String id;

    @TableField(value = "user_id")
    @NotBlank(message = "用户编号不能为空", groups = {AddValid.class, UpdateValid.class})
    private String userId;

    @TableField(value = "role_id")
    @NotBlank(message = "角色编号不能为空", groups = {AddValid.class, UpdateValid.class})
    private String roleId;

    @TableField(value = "remarks")
    private String remarks;

    @TableField(value = "create_date_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createDateTime;

    @TableField(value = "update_date_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updateDateTime;
}