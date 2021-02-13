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
import javax.validation.constraints.NotNull;
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
    @NotBlank(message = "ID不能为空", groups = {UpdateValid.class})
    private String id;

    @TableField(value = "parent_id")
    private String parentId;

    @TableField(value = "title")
    @NotBlank(message = "标题不能为空", groups = {AddValid.class, UpdateValid.class})
    private String title;

    @TableField(value = "navbar")
    @NotBlank(message = "导航栏名称不能为空", groups = {AddValid.class, UpdateValid.class})
    private String navbar;

    @TableField(value = "icon_class")
    @NotBlank(message = "图标类名称不能为空", groups = {AddValid.class, UpdateValid.class})
    private String iconClass;

    @TableField(value = "url")
    private String url;

    @TableField(value = "sort")
    @NotNull(message = "排序不能为空", groups = {AddValid.class, UpdateValid.class})
    private Integer sort;

    @TableField(value = "remarks")
    private String remarks;

    @TableField(value = "create_date_time")
    private LocalDateTime createDateTime;

    @TableField(value = "update_date_time")
    private LocalDateTime updateDateTime;
}