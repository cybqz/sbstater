package com.cyb.authority.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author 陈迎博
 * @Description 权限注解
 * @Date 2021/1/30
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Authentication {

    //是否提示
    boolean isShowTips() default true;

    //名称
    String name() default "";

    //角色
    String[] roleNames() default {};

    //权限
    String[] permissionNames() default {};

    //模块
    String[] modelNames() default {};
}