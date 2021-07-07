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
            path = path.replace("[/]","\\");
            File file = new File(path);
            if(file.isDirectory()){
                if(!file.exists()){
                    try {
                        file.createNewFile();
                    }catch (Exception e){
                        log.info("创建目录异常，{}！", e.getMessage());
                        return Tips.fail(null, "创建目录异常！");
                    }
                }
                return Tips.fail(null, "创建目录成功！");
            }
            return Tips.fail(null, "创建目录失败，路径不是目录！");
        }
        return Tips.fail(null, "创建目录失败，路径是空！");
    }
}
