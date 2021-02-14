package com.cyb.common.utils;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

/**
 * @Author 陈迎博
 * @Description Wrapper辅助类
 * @Date 2021/2/14
 */
@Component
public class WrapperUtil {

    /**
     * @Author 陈迎博
     * @Title 拼接时间查询条件
     * @Description
     * @Date 2021/2/14
     */
    public void appendDateTime(LambdaQueryWrapper wrapper, SFunction column, String dateTime){
        if(StringUtils.isNotBlank(dateTime) && dateTime.indexOf(" - ") > 0){

            String[] s = dateTime.split(" - ");
            LocalDateTime start = DateTimeUtil.getByStr(s[0], null);
            LocalDateTime end = DateTimeUtil.getByStr(s[1], null);
            appendDateTime(wrapper, start, end, column);
        }
    }

    /**
     * @Author 陈迎博
     * @Title 拼接时间查询条件
     * @Description
     * @Date 2021/2/14
     */
    public void appendDateTime(LambdaQueryWrapper wrapper, LocalDateTime start, LocalDateTime end, SFunction column){
        if(null != start && null != end){
            wrapper.between(column, start, end);
        }else if(null != start){
            wrapper.le(column, start);
        }else if(null != end){
            wrapper.ge(column, end);
        }
    }
}
