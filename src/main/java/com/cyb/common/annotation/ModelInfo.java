package com.cyb.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author 陈迎博
 * @Description 模块信息注解类
 * @Date 2021/2/6
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ModelInfo {

    //标题
    String title();

    //导航栏名称
    String navbar() default "";

    //页面前缀
    String prefix() default "";

    //排序
    int sort() default 0;
}
