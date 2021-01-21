package com.cyb.authority.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Getter;
import lombok.Setter;

/**
 * @Author 陈迎博
 * @Description 权限校验桥接对象
 * @Date 2021/1/21
 */
@Getter
@Setter
public class CybAuthorityUser {

    private String id;

    private String name;

    private String password;
}
