package com.cyb.cache.controller;

import com.cyb.cache.annotation.RedisRemove;
import com.cyb.common.result.R;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value="/cacheTest")
public class CacheTestController {

    @RedisRemove(cacheNames = "test")
    @GetMapping("/remove")
    @ResponseBody
    public R<Map> remove(String name){
        return R.success("success");
    }

    @GetMapping("/get")
    @ResponseBody
    public R<Map> get(String name){
        Map<String, String> map = new HashMap<>();
        map.put("name", name);
        System.out.println("excute push success");
        return R.success("success", map);
    }
}
