package com.cyb.authority.service;

import com.alibaba.fastjson.JSONObject;
import com.cyb.authority.utils.EncryptionDecrypt;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.Serializable;

/**
 * 登陆服务
 */
public class CybAuthorityLoginService {

    private static final Logger logger = LoggerFactory.getLogger(CybAuthorityLoginService.class);

    /**
     * 登陆方法
     * @param userName
     * @param password
     * @return jSONObject {success: boolean, }
     */
    public JSONObject doLogin(String userName, String password){

        JSONObject result = new JSONObject();
        result.put("success", false);
        if(!StringUtils.isEmpty(userName) || !StringUtils.isEmpty(password)){
            Subject subject = SecurityUtils.getSubject();
            UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(userName, EncryptionDecrypt.encryptionMD5(password));
            try {
                subject.login(usernamePasswordToken);
                Object object = subject.getPrincipal();
                subject.getSession(true).setAttribute("SESSION_NAME", object);

                //token信息
                subject = SecurityUtils.getSubject();
                Serializable authToken = subject.getSession().getId();

                result.put("success", true);
                result.put("authToken", authToken);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return result;
    }
}
