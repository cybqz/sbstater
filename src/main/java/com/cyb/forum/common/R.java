package com.cyb.forum.common;

import lombok.Data;

/**
 * 公共返回结果封装
 */
@Data
public class R {

    private String msg;

    private boolean result = false;

    private R(String msg) {
        this.msg = msg;
    }

    public static R success(String msg){
        R r = new R(msg);
        r.setResult(true);
        return r;
    }

    public static R fail(String msg){
        R r = new R(msg);
        return r;
    }

    public boolean isSuccess(){
        return this.result;
    }
}
