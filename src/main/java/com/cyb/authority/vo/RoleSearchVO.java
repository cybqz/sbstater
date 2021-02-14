package com.cyb.authority.vo;

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
 * @Description 角色搜索VO
 * @Date 2021/1/21
 */
@Data
public class RoleSearchVO {

    private String name;

    private String remarks;

    private String dateTime;
}