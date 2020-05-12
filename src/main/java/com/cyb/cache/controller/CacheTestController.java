package com.cyb.cache.controller;

import com.cyb.cache.annotation.RedisPut;
import com.cyb.common.result.R;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value="/cacheTest")
public class CacheTestController {


    @RedisPut(cacheNames = "test")
    @PostMapping("/push")
    @ResponseBody
    public R<Map> push(String name){
        Map<String, String> map = new HashMap<>();
        map.put("name", name);
        System.out.println("excute push success");
        return R.success("success", map);
    }
}
