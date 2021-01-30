package com.cyb.authority.aspect;

import com.cyb.authority.annotation.Authentication;
import com.cyb.authority.domain.Role;
import com.cyb.authority.domain.User;
import com.cyb.authority.service.RoleService;
import com.cyb.authority.service.UserService;
import com.cyb.authority.validate.UserValidate;
import com.cyb.common.tips.Tips;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.connector.RequestFacade;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author 陈迎博
 * @Description 权限校验切面
 * @Date 2021/1/30
 */
@Slf4j
@Aspect
@Component
@Order(-10)
public class AuthenticationAspect {

    ThreadLocal<Long> beginTime = new ThreadLocal<Long>();

    @Resource
    private RoleService roleService;

    @Resource
    private UserService userService;

    @Resource
    private UserValidate userValidate;

    @Pointcut("@annotation(authentication)")
    public void AuthenticationService(Authentication authentication) {
    }

    @Around("AuthenticationService(authentication)")
    public Object doAround(ProceedingJoinPoint joinPoint, Authentication authentication) throws Throwable{
        beginTime.set(System.currentTimeMillis());
        String userId = null;

        //获取当前登录用户信息
        User user = userValidate.isLoginAuthenticated();
        if (null == user) {
            return new Tips("权限验证未通过，用户未登录", true, false);
        } else {
            user = userService.selectByUserName(user.getUserName());
            if(null != user && StringUtils.isNotBlank(user.getId())){
                userId = user.getId();
            }else{
                return new Tips("权限验证未通过，用户不存在", true, false);
            }
        }

        //用户角色校验
        boolean isPassRole = validRole(authentication.roleNames(), userId);

        //返回校验结果
        if (isPassRole) {
            log.info("authentication pass, cost time: {}", System.currentTimeMillis() - beginTime.get());
            return joinPoint.proceed();
        } else {
            beginTime.set(System.currentTimeMillis());
            log.info("authentication reject, cost time: {}", System.currentTimeMillis() - beginTime.get());
            return new Tips(authentication.name() + "，权限验证未通过", true, false);
        }
    }

    /**
     * @Author 陈迎博
     * @Description 用户角色校验
     * @Date 2021/1/30
     */
    private boolean validRole(String[] roleNames, String userId){

        boolean isPass = false;
        if(null != roleNames && roleNames.length > 0){

            List<String> roleList = new ArrayList<>();
            //查询用户角色
            List<Role> roles = roleService.selectListByUserId(userId);
            for (Role role : roles) {
                roleList.add(role.getName());
            }

            //校验角色
            log.info("authentication user={}, roles={}", userId, roleList);
            for (String role : roleNames) {
                if (roleList.contains(role)) {
                    isPass = true;
                }
            }
        }
        return isPass;
    }
}
