package com.cyb.common.validation;

import com.alibaba.fastjson.JSONObject;
import com.cyb.common.result.R;
import org.hibernate.validator.HibernateValidator;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * 校验辅助类
 */
public class ValidationUtil {

    private static Validator failFastValidator = Validation.byProvider(HibernateValidator.class)
            .configure()
            .failFast(true)
            .buildValidatorFactory().getValidator();

    /**
     * 全部校验
     */
    private static Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
    
    private ValidationUtil() {}

    /**
     * 注解验证参数(快速失败模式)
     *
     * @param entity
     */
    public static <Entity> R<JSONObject> fastValidate(Entity entity) {
        Set<ConstraintViolation<Entity>> constraintViolations = failFastValidator.validate(entity);
        //返回异常result
        if (constraintViolations.size() > 0) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("property", constraintViolations.iterator().next().getPropertyPath().toString());
            return R.fail(constraintViolations.iterator().next().getMessage(), jsonObject);
        }
        return R.success("success");
    }

    /**
     * 注解验证参数(全部校验)
     *
     * @param entity
     */
    public static <Entity> R<List<String>> allValidate(Entity entity) {
        Set<ConstraintViolation<Entity>> constraintViolations = validator.validate(entity);
        //返回异常result
        if (constraintViolations.size() > 0) {
            List<String> errorMessages = new LinkedList<String>();
            Iterator<ConstraintViolation<Entity>> iterator = constraintViolations.iterator();
            while (iterator.hasNext()) {
                ConstraintViolation<Entity> violation = iterator.next();
                errorMessages.add(String.format("%s:%s", violation.getPropertyPath().toString(), violation.getMessage()));
            }
            return R.fail("fail", errorMessages);
        }
        return R.success("success");
    }
}
