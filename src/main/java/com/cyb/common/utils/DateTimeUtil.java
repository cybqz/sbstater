package com.cyb.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @Author 陈迎博
 * @Description 时间工具辅助类
 * @Date 2021/2/14
 */
@Slf4j
public class DateTimeUtil {

    private static final String PATTERN = "yyyy-MM-dd HH:mm:ss";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(PATTERN);

    /**
     * @Author 陈迎博
     * @Title 格式化时间
     * @Description
     * @Date 2021/2/14
     */
    public static LocalDateTime getByStr(String dateTime, String pattern){
        try {
            if(StringUtils.isBlank(dateTime)){
                return null;
            }
            DateTimeFormatter formatter = null;
            if(StringUtils.isNotBlank(pattern)){
                formatter = DateTimeFormatter.ofPattern(pattern);
            }else {
                formatter = FORMATTER;
            }
            return LocalDateTime.parse(dateTime, formatter);
        }catch (Exception e){
            log.error("时间格式化异常");
            return null;
        }
    }
}
