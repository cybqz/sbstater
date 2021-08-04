package com.cyb.common.validation.constraints;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @Author 陈迎博
 * @Description Double校验注解约束类
 * @Date 2021/8/01
 */
public class DoubleRangeValidator implements ConstraintValidator<DoubleRange, Double> {

    private double min;
    private double max;
    private String message;
    private Class<?>[] groups;

    @Override
    public void initialize(DoubleRange constraintAnnotation) {
        this.min = constraintAnnotation.min();
        this.max = constraintAnnotation.max();
        this.groups = constraintAnnotation.groups();
        this.message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(Double value, ConstraintValidatorContext context) {
        if(null != value){
            if(value.compareTo(min) >= 0 && value.compareTo(max) <= 0){
                return true;
            }
        }
        return false;
    }
}
