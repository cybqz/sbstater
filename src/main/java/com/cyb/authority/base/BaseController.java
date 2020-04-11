package com.cyb.authority.base;

import com.cyb.authority.domain.User;
import com.cyb.authority.service.UserService;
import com.cyb.authority.validate.UserValidate;
import com.cyb.common.tips.Tips;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 基础控制类
 */
public class BaseController {

    @Autowired
    private UserValidate userValidate;

    @Autowired
    private UserService userService;

    public User currentLoginedUser;

    protected boolean isLogined = false;

    protected Tips tips = null;

    /**
     * 验证用户是否登陆
     */
    public void validLogined(){

        tips = new Tips("success", true, true);
        User user = userValidate.isLoginAuthenticated();
        isLogined = null != user;
        if(!isLogined){
            tips = new Tips("请先登陆", true, false);
        }else{
            currentLoginedUser = userService.selectByUserName(user.getUserName());
        }
    }

    /**
     * 验证用户是否登陆
     */
    public void validateAll(String role, String permission){

        tips = new Tips("success", true, true);
        User user = userValidate.validateAll(tips, role, permission);
        if(tips.isValidate()){
            isLogined = null != user;
            if(!isLogined){
                tips = new Tips("请先登陆", true, false);
            }else{
                currentLoginedUser = userService.selectByUserName(user.getUserName());
            }
        }
    }
}
