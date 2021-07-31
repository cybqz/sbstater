package com.cyb.common.result;

import com.cyb.common.tips.Tips;
import lombok.Data;
import javax.servlet.http.HttpServletResponse;

/**
 * 公共返回结果封装
 * @author CYB
 */
@Data
public class R<Entity> {

    private int code;

    private String msg;

    private Entity data;

    private boolean success = false;

    private R(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private R(int code, Entity data, String msg) {
        this.code = code;
        this.data = data;
        this.msg = msg;
    }

    public static R success(String msg){
        R r = new R(HttpServletResponse.SC_OK, msg);
        r.setSuccess(true);
        return r;
    }

    public static R success(String msg, Object data){
        R r = new R(HttpServletResponse.SC_OK, data, msg);
        r.setSuccess(true);
        return r;
    }

    public static R fail(String msg){
        R r = new R(HttpServletResponse.SC_BAD_REQUEST, msg);
        return r;
    }

    public static R fail(String msg, Object data){
        R r = new R(HttpServletResponse.SC_BAD_REQUEST, data, msg);
        return r;
    }

    public Tips toTips(){
        return new Tips(this.msg, this.success, this.data);
    }

    public boolean isSuccess(){
        return this.success;
    }
}
