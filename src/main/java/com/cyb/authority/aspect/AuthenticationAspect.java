package com.cyb.authority.aspect;

import com.cyb.authority.annotation.Authentication;
import com.cyb.authority.domain.Permission;
import com.cyb.authority.domain.Role;
import com.cyb.authority.domain.RolePermission;
import com.cyb.authority.domain.User;
import com.cyb.authority.service.PermissionService;
import com.cyb.authority.service.RolePermissionService;
import com.cyb.authority.service.RoleService;
import com.cyb.authority.service.UserService;
import com.cyb.authority.validate.UserValidate;
import com.cyb.common.tips.Tips;
import lombok.extern.slf4j.Slf4j;
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
import java.util.Set;
import java.util.stream.Collectors;

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
    private PermissionService permissionService;

    @Resource
    private RolePermissionService rolePermissionService;

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

        //用户角色、权限校验
        boolean isPassRole = validRoleAndPermission(authentication, userId);

        //返回校验结果
        if (isPassRole) {
            log.info("authentication pass, cost time: {}", System.currentTimeMillis() - beginTime.get());
            return joinPoint.proceed();
        } else {
            beginTime.set(System.currentTimeMillis());
            log.info("authentication reject, cost time: {}", System.currentTimeMillis() - beginTime.get());
            return new Tips(authentication.name() + "，权限验证未通过", authentication.isShowTips(), false);
        }
    }

    /**
     * @Author 陈迎博
     * @Description 用户角色、权限校验
     * @Date 2021/1/30
     */
    private boolean validRoleAndPermission(Authentication authentication, String userId){

        boolean isPass = false;
        String[] roleNames = authentication.roleNames();
        String[] permissionNames = authentication.permissionNames();

        //校验角色
        if(null != roleNames && roleNames.length > 0){

            List<String> roleIdList = new ArrayList<>();
            List<String> roleNameList = new ArrayList<>();
            //查询用户角色
            List<Role> roles = roleService.selectListByUserId(userId);
            if(!CollectionUtils.isEmpty(roles)){

                for (Role role : roles) {

                    roleIdList.add(role.getId());
                    roleNameList.add(role.getName());
                }

                //校验角色
                log.info("authentication user={}, roles={}", userId, roleNameList);
                for (String role : roleNames) {
                    if (roleNameList.contains(role)) {
                        isPass = true;
                    }
                }

                //校验权限
                if(isPass && null != permissionNames && permissionNames.length > 0){
                    List<RolePermission> rolePermissionList = rolePermissionService.selectListByRoleIds(roleIdList);
                    if(!CollectionUtils.isEmpty(rolePermissionList)){

                        List<String> permissionIdList = new ArrayList<String>(rolePermissionList.size());
                        for(RolePermission rp : rolePermissionList){
                            permissionIdList.add(rp.getPermissionId());
                        }

                        List<Permission> permissionList = permissionService.queryListByIds(permissionIdList);
                        Set<String> permissionNameSet = permissionList.stream().map(Permission::getName).collect(Collectors.toSet());

                        log.info("authentication user={}, roles={}", userId, permissionNameSet);
                        for(String permission : permissionNames){
                            if(permissionNameSet.contains(permission)){
                                isPass = true;
                            }
                        }
                    }
                }
            }
        }
        return isPass;
    }
}
