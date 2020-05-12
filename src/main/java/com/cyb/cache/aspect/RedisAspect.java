package com.cyb.cache.aspect;

import com.cyb.cache.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class RedisAspect {

    @Autowired
    private RedisUtil redisUtil;

    @Pointcut("@annotation(com.cyb.cache.annotation.RedisPut)")
    public void redisPutPointcut() {}

    /**
     * 进入方法前实现的逻辑
     * @param joinPoint
     */
    @Before("redisPutPointcut()")
    public void beforePointcut(JoinPoint joinPoint) {
    }

    @Around("redisPutPointcut()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        String[] params = methodSignature.getParameterNames();// 获取参数名称
        Object[] args = joinPoint.getArgs();// 获取参数值

        if(null != params && params.length > 0){
            Object[] data = new Object[params.length];
            int index = 0;
            for(String param : params){
                data[index] = new Object[]{param, args[index]};
                ++index;
            }
            return joinPoint.proceed(data);
        }
        return joinPoint.proceed();
    }

    /**
     * 在切入点return内容之后切入内容（可以用来对处理返回值做一些加工处理）
     * @param joinPoint
     */
    @AfterReturning("redisPutPointcut()")
    public void doAfterReturning(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        if(null != args && args.length > 0){

            //计算redis key
            String key = "";
            for(Object object : args){

                Object[] data = (Object[]) object;
                key += data[0].toString();
            }

            if(!redisUtil.hasKey(key)){
                Object value = redisUtil.get(key);
            }
        }
    }
}
