package com.cyb.common.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.cyb.common.validation.group.UpdateValid;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

/**
 * 基础Domain类
 * @author CYB
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseDomain {

    @TableId(value = "id")
    @ApiModelProperty(value = "ID")
    @NotBlank(message = "权限编号不能为空", groups = {UpdateValid.class})
    private String id;

    @TableField(value = "create_date_time")
    @ApiModelProperty(value = "createDateTime")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createDateTime = LocalDateTime.now();

    @TableField(value = "update_date_time")
    @ApiModelProperty(value = "updateDateTime")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updateDateTime = LocalDateTime.now();
}
