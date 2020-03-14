package com.cyb.chat.controller;

import com.cyb.chat.server.CybWebSocketServer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("chatTest")
public class ChatTestController {

    @RequestMapping("/push/{toUserId}")
    public ResponseEntity<String> pushToWeb(String message, @PathVariable String toUserId){
        try {
            CybWebSocketServer.sendInfo(message,toUserId);
            return ResponseEntity.ok("MSG SEND SUCCESS");
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResponseEntity.ok("MSG SEND FAIL");
    }
}
