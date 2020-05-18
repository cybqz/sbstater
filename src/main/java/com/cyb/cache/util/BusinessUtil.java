package com.cyb.cache.util;

public class BusinessUtil {

    /**
     * 根据参数值计算key
     * @param args
     * @return
     */
    public static String getKeyByArgs(Object[] args){

        String key = "";
        if(null != args && args.length > 0){
            for(Object object : args){
                key += object.toString();
            }
        }
        return key;
    }
}
