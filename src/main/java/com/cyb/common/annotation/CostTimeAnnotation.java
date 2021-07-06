package com.cyb.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author 陈迎博
 * @Title 自定义耗时打印注解
 * @Description 自定义耗时打印注解
 * @Date  2021/6/05
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CostTimeAnnotation {
  String title() default "";
}
