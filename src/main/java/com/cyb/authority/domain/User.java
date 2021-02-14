package com.cyb.authority.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyb.authority.constant.SexEnum;
import com.cyb.common.validation.constraints.Phone;
import com.cyb.common.validation.group.AddValid;
import com.cyb.common.validation.group.UpdateValid;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Author 陈迎博
 * @Description 用户模型
 * @Date 2021/1/21
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "user")
public class User implements Serializable {

    @TableId(value = "id")
    @NotBlank(message = "ID不能为空", groups = {UpdateValid.class})
    private String id;

    @TableField(value = "name")
    @NotBlank(message = "姓名不能为空", groups = {AddValid.class, UpdateValid.class})
    private String name;

    @TableField(value = "user_name")
    @NotBlank(message = "用户名不能为空", groups = {AddValid.class, UpdateValid.class})
    private String userName;

    @TableField(value = "password")
    @NotBlank(message = "密码不能为空", groups = {AddValid.class, UpdateValid.class})
    private String password;

    @TableField(value = "phone")
    @Phone(groups = {AddValid.class, UpdateValid.class})
    private String phone;

    @TableField(value = "email")
    @Email(message = "邮箱地址不合法", regexp = "^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$", groups = {AddValid.class, UpdateValid.class})
    private String email;

    @TableField(value = "sex")
    @NotNull(message = "性别校验不合法")
    private SexEnum sex;

    @TableField(value = "address")
    @NotBlank(message = "地址不能为空", groups = {AddValid.class, UpdateValid.class})
    private String address;

    @TableField(value = "image")
    private String image;

    @TableField(value = "introduce")
    private String introduce;

    @TableField(value = "create_date_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createDateTime;

    @TableField(value = "update_date_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updateDateTime;
}