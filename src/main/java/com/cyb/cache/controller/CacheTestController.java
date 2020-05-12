package com.cyb.cache.controller;

import com.cyb.common.result.R;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller()
@RequestMapping(value="/cacheTest")
public class CacheTestController {


    @PostMapping("/push")
    public R<String> push(String message, String toUserId){

        return R.success("success");
    }
}
