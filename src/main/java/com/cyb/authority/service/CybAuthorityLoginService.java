package com.cyb.authority.service;

import com.cyb.authority.utils.EncryptionDecrypt;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

/**
 * 登陆服务
 */
public class CybAuthorityLoginService {

    private static final Logger logger = LoggerFactory.getLogger(CybAuthorityLoginService.class);

    public boolean doLogin(String userName, String password){

        if(!StringUtils.isEmpty(userName) || !StringUtils.isEmpty(password)){
            Subject subject = SecurityUtils.getSubject();
            UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(userName, EncryptionDecrypt.encryptionMD5(password));
            try {
                logger.info("开始登陆.......");
                subject.login(usernamePasswordToken);
                Object object = subject.getPrincipal();
                subject.getSession(true).setAttribute("SESSION_NAME", object);
            } catch (Exception e) {
                return false;
            }
            return true;
        }
        return false;
    }
}
