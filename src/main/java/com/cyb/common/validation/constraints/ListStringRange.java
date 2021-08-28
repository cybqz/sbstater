package com.cyb.common.validation.constraints;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @Author 陈迎博
 * @Description Double校验注解
 * @Date 2021/8/01
 */
@Constraint(validatedBy = {ListStringRangeValidator.class})
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
@Retention(RUNTIME)
public @interface ListStringRange {

    String message() default "com.cyb.common.validation.constraints.ListStringRange valid fail";

    int minSize() default 0;

    int maxSize() default Integer.MAX_VALUE;

    boolean repeat() default false;

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
