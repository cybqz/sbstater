package com.cyb.common.aspect;

import com.cyb.common.annotation.TransRollbackAnnotation;
import com.cyb.common.result.R;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

/**
 * @Author 陈迎博
 * @Title 自定义事务拦截处理切面类
 * @Description 自定义事务拦截处理切面类
 * @Date  2021/2/18
 */
@Slf4j
@Aspect
@Component
public class TransactionAspect {

  @Pointcut("@annotation(com.cyb.common.annotation.TransRollbackAnnotation)")
  private void pointcut() {}

  @Around(value = "pointcut() && @annotation(annotation)")
  public Object around(ProceedingJoinPoint point, TransRollbackAnnotation annotation) {
    try {
      Object result = point.proceed();
      return result;
    }catch (Exception e){
      //强制手动事务回滚
      TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
      log.error(annotation.value(), e);
    } catch (Throwable throwable) {
      TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
      log.error(annotation.value(), throwable);
    }
    return R.fail(annotation.value());
  }
}

