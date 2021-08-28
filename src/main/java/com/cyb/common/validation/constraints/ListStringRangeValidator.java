package com.cyb.common.validation.constraints;

import org.springframework.util.CollectionUtils;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Author 陈迎博
 * @Description 手机号校验注解约束类
 * @Date 2021/2/13
 */
public class ListStringRangeValidator implements ConstraintValidator<ListStringRange, List<String>> {

    private int minSize;
    private int maxSize;
    private boolean repeat;
    private String message;
    private Class<?>[] groups;

    @Override
    public void initialize(ListStringRange constraintAnnotation) {
        this.minSize = constraintAnnotation.minSize();
        this.maxSize = constraintAnnotation.maxSize();
        this.repeat = constraintAnnotation.repeat();
        this.groups = constraintAnnotation.groups();
        this.message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(List<String> list, ConstraintValidatorContext context) {
        if(this.minSize > 0 && CollectionUtils.isEmpty(list)){
            return false;
        }

        //重复校验
        if(this.repeat){
            Set<String> set = list.stream().collect(Collectors.toSet());
            if(list.size()!= set.size()){
                return false;
            }
        }
        return true;
    }
}
