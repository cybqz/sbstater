package com.cyb.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author 陈迎博
 * @Title 自定义事务回滚注解
 * @Description 自定义事务回滚注解
 * @Date  2021/2/18
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface TransRollbackAnnotation {
  String value() default "";
}
