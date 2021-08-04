package com.cyb.common.validation.constraints;

import org.springframework.util.CollectionUtils;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import com.cyb.common.validation.enums.ListRangeTypeEnum;
/**
 * @Author 陈迎博
 * @Description 手机号校验注解约束类
 * @Date 2021/2/13
 */
public class ListIntegerRangeValidator implements ConstraintValidator<ListIntegerRange, List<Integer>> {

    private Double min;
    private Double max;
    private int size;
    private boolean repeat;
    private String message;
    private Class<?>[] groups;

    @Override
    public void initialize(ListIntegerRange constraintAnnotation) {
        this.min = Double.parseDouble(constraintAnnotation.min());
        this.max = Double.parseDouble(constraintAnnotation.max());
        this.size = constraintAnnotation.size();
        this.repeat = constraintAnnotation.repeat();
        this.groups = constraintAnnotation.groups();
        this.message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(List<Integer> list, ConstraintValidatorContext context) {
        if(CollectionUtils.isEmpty(list) || list.size() != this.size){
            return true;
        }

        //重复校验
        Set<Integer> set = list.stream().collect(Collectors.toSet());
        if(!this.repeat && list.size()!= set.size()){
            return false;
        }

        //校验值范围
        for(Object obj : list){
            Double d = Double.parseDouble(obj.toString());
            if(d.compareTo(min) < 0 || d.compareTo(max) > 0){
                return false;
            }
        }
        return true;
    }
}
