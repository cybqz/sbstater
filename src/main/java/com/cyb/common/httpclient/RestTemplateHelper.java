package com.systek.hnppl.nogp.supervision.biz.util;

import com.alibaba.fastjson.JSONObject;
import com.cyb.common.result.R;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Http请求辅助类
 *
 * @author chenyingbo
 */
public class RestTemplateHelper {

    /**
     * POST方式请求数据
     *
     * @param msg
     * @param url
     * @return
     */
    public static R<JSONObject> sendMsgPost(String msg, String url){

        JSONObject jsonObject = new JSONObject();
        if(StringUtils.isEmpty(msg)){
            jsonObject.put("msg", "msg is null");
            return R.fail("fail", jsonObject);
        }
        try {
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
            map.add("msg", msg);

            //发送请求
            HttpEntity<MultiValueMap<String, String>> formEntity = new HttpEntity<>(map, headers);
            ResponseEntity<String> response = restTemplate.postForEntity( url, formEntity , String.class );

            //解析返回结果

            return R.success("ResultCode.FAILURE.getCode()", response.getBody());
        }catch (Exception e){
            jsonObject = new JSONObject();
            jsonObject.put("Exception", e.getMessage());
            return R.fail("fail", jsonObject);
        }
    }
}
