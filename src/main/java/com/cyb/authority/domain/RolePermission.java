package com.cyb.authority.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author 陈迎博
 * @Description 角色-权限模型
 * @Date 2021/1/21
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "role_permission")
public class RolePermission {

    @TableId(value = "id")
    private String id;

    @TableField(value = "role_id")
    private String roleId;

    @TableField(value = "permission_id")
    private String permissionId;

    @TableField(value = "remarks")
    private String remarks;
}