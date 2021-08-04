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
@Constraint(validatedBy = {DoubleRangeValidator.class})
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
@Retention(RUNTIME)
public @interface DoubleRange {

    String message() default "com.cyb.common.validation.constraints.DoubleRange valid fail";

    double min() default 0.00;

    double max() default 9999999.99;

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
