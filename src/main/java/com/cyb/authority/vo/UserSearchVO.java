package com.cyb.authority.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyb.authority.constant.SexEnum;
import com.cyb.common.validation.constraints.Phone;
import com.cyb.common.validation.group.AddValid;
import com.cyb.common.validation.group.UpdateValid;
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
 * @Description 用户搜索VO
 * @Date 2021/1/21
 */
@Data
public class UserSearchVO implements Serializable {

    private String name;

    private String userName;

    private String phone;

    private String email;

    private String address;

    private String dateTime;
}