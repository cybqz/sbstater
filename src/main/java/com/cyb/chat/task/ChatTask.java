package com.cyb.chat.task;

import com.cyb.chat.server.CybWebSocketServer;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ChatTask {

    @Scheduled(cron="0/5 * * * * ? ") //每5秒执行一次
    public void testA(){

        try {
            CybWebSocketServer.sendInfo("messageA", "5");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Scheduled(cron="0/7 * * * * ? ") //每5秒执行一次
    public void testB(){

        try {
            CybWebSocketServer.sendInfo("messageB", "7");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Scheduled(cron="0/9 * * * * ? ") //每5秒执行一次
    public void testC(){

        try {
            CybWebSocketServer.sendInfo("messageC", "9");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Scheduled(cron="0/11 * * * * ? ") //每5秒执行一次
    public void testD(){

        try {
            CybWebSocketServer.sendInfo("messageD", "11");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Scheduled(cron="0/13 * * * * ? ") //每5秒执行一次
    public void testE(){

        try {
            CybWebSocketServer.sendInfo("messageE", "13");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Scheduled(cron="0/15 * * * * ? ") //每5秒执行一次
    public void testF(){

        try {
            CybWebSocketServer.sendInfo("messageF", "15");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
