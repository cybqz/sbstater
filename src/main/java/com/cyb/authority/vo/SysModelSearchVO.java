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
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * @Author 陈迎博
 * @Description 系统模块搜索VO
 * @Date 2021/1/21
 */
@Data
public class SysModelSearchVO {

    private String title;

    private String navbar;

    private String url;

    private String remarks;

    private String dateTime;
}