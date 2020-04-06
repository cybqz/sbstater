package com.cyb.chat.controller;

import com.cyb.chat.server.CybTeamChatWSServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller()
@RequestMapping(value="/chatTest")
public class ChatTestController {

    @Autowired
    private CybTeamChatWSServer cybTeamChatWSServer;

    @PostMapping("/push")
    public ResponseEntity<String> push(String message, String toUserId){
        try {
            cybTeamChatWSServer.sendInfo(message,toUserId);
            return ResponseEntity.ok("MSG SEND SUCCESS");
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResponseEntity.ok("MSG SEND FAIL");
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(
            @RequestParam(value = "team")String team,
            @RequestParam(value = "userIdList")List<String> userIdList,
            @RequestParam(value = "userNameList")List<String> userNameList){
        try {
            cybTeamChatWSServer.register(team, userIdList, userNameList);
            return ResponseEntity.ok("Register Team SUCCESS");
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResponseEntity.ok("Register Team FAIL");
    }
}
