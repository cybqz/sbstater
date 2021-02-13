package com.cyb.authority.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyb.common.validation.group.AddValid;
import com.cyb.common.validation.group.UpdateValid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

/**
 * @Author 陈迎博
 * @Description 角色模型
 * @Date 2021/1/21
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "role")
public class Role {

    @TableId(value = "id")
    @NotBlank(message = "角色编号不能为空", groups = {UpdateValid.class})
    private String id;

    @TableField(value = "name")
    @NotBlank(message = "名称不能为空", groups = {AddValid.class, UpdateValid.class})
    private String name;

    @TableField(value = "remarks")
    private String remarks;

    @TableField(value = "create_date_time")
    private LocalDateTime createDateTime;

    @TableField(value = "update_date_time")
    private LocalDateTime updateDateTime;
}