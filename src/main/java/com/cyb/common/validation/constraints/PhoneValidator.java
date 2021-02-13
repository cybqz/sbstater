package com.cyb.common.validation.constraints;

import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author 陈迎博
 * @Description 手机号校验注解约束类
 * @Date 2021/2/13
 */
public class PhoneValidator implements ConstraintValidator<Phone, String> {


    private String regexp;
    private String message;
    private Class<?>[] groups;

    @Override
    public void initialize(Phone constraintAnnotation) {
        this.groups = constraintAnnotation.groups();
        this.regexp = constraintAnnotation.regexp();
        this.message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        if(StringUtils.isNotBlank(regexp) || StringUtils.isBlank(value)){
            Pattern pattern = Pattern.compile(regexp);
            Matcher matcher = pattern.matcher(value);
            return matcher.find();
        }
        return false;
    }
}
