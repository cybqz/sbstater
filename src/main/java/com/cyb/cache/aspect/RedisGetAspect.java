package com.cyb.cache.aspect;

import com.cyb.cache.util.BusinessUtil;
import com.cyb.cache.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class RedisGetAspect {

    @Autowired
    private RedisUtil redisUtil;

    @Pointcut("@annotation(com.cyb.cache.annotation.RedisGet)")
    public void redisGetPointcut() {}

    @Before("redisGetPointcut()")
    public void redisGetBeforePointcut(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        String key = BusinessUtil.getKeyByArgs(args);
        if(StringUtils.isEmpty(key)){
            throw new RuntimeException("Key can not be empty");
        }
        if(redisUtil.hasKey(key)){
            redisUtil.del(key);
        }
    }

    @Around("redisGetPointcut()")
    public Object redisGetAround(ProceedingJoinPoint joinPoint) throws Throwable {

        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        String[] params = methodSignature.getParameterNames();
        Object[] args = joinPoint.getArgs();
        return joinPoint.proceed();
    }

    @AfterReturning(value = "redisGetPointcut()", argNames = "joinPoint,returnVal", returning = "returnVal")
    public void redisGetAfterReturning(JoinPoint joinPoint, Object returnVal) {

        Object[] args = joinPoint.getArgs();
        String key = BusinessUtil.getKeyByArgs(args);
        if(StringUtils.isEmpty(key)){
            throw new RuntimeException("Key can not be empty");
        }
        if(null == returnVal){
            throw new RuntimeException("Return val can not be empty");
        }
        if(!redisUtil.hasKey(key)){
            redisUtil.set(key, returnVal);
        }
    }
}
