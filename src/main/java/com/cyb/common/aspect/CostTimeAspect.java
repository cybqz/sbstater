package com.cyb.common.aspect;

import com.cyb.common.annotation.CostTimeAnnotation;
import com.cyb.common.result.R;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @Author 陈迎博
 * @Title 自定义耗时打印注解
 * @Description 自定义耗时打印注解
 * @Date  2021/2/18
 */
@Slf4j
@Aspect
@Component
public class CostTimeAspect {

  @Pointcut("@annotation(com.cyb.common.annotation.CostTimeAnnotation)")
  private void pointcut() {}

  @Around(value = "pointcut() && @annotation(annotation)")
  public Object around(ProceedingJoinPoint point, CostTimeAnnotation annotation) {

    String title = annotation.title();
    try {
      Long start = System.currentTimeMillis();
      Object result = point.proceed();
      log.info("{}耗时: {}", title, (System.currentTimeMillis() - start));
      return result;
    }catch (Exception e){
      log.error(title, e);
    } catch (Throwable throwable) {
      log.error(title, throwable);
    }
    return R.fail(title + "失败");
  }
}

