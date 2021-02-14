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
 * @Description 权限模型
 * @Date 2021/1/21
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "permission")
public class Permission {

    @TableId(value = "id")
    @NotBlank(message = "权限编号不能为空", groups = {UpdateValid.class})
    private String id;

    @TableField(value = "name")
    @NotBlank(message = "名称不能为空", groups = {AddValid.class, UpdateValid.class})
    private String name;

    @TableField(value = "remarks")
    private String remarks;

    @TableField(value = "create_date_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createDateTime;

    @TableField(value = "update_date_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updateDateTime;
}