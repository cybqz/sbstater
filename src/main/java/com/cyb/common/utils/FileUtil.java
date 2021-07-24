package com.cyb.common.utils;

import com.cyb.common.tips.Tips;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import java.io.File;

/**
 * @author CYB
 */
@Slf4j
public class FileUtil {

    /**
     * 创建目录
     * @param path
     * @return
     */
    public static Tips createPath(String path){
        if(StringUtils.isNotBlank(path)){
            File file = new File(path);
            if(!file.isDirectory()){
                file.mkdirs();
                return Tips.fail(path, "创建目录成功！");
            }
            return Tips.fail(path, "创建目录失败，路径已存在！");
        }
        return Tips.fail(null, "创建目录失败，路径是空！");
    }
}
