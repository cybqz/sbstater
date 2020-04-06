package com.cyb.chat.task;

import com.cyb.chat.server.CybTeamChatWSServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ChatTask {

    @Autowired
    private CybTeamChatWSServer cybTeamChatWSServer;

    @Scheduled(cron="0/5 * * * * ? ") //每5秒执行一次
    public void testA(){

        try {
            cybTeamChatWSServer.sendInfo("messageA", "5");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Scheduled(cron="0/7 * * * * ? ") //每5秒执行一次
    public void testB(){

        try {
            cybTeamChatWSServer.sendInfo("messageB", "7");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Scheduled(cron="0/9 * * * * ? ") //每5秒执行一次
    public void testC(){

        try {
            cybTeamChatWSServer.sendInfo("messageC", "9");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Scheduled(cron="0/11 * * * * ? ") //每5秒执行一次
    public void testD(){

        try {
            cybTeamChatWSServer.sendInfo("messageD", "11");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Scheduled(cron="0/13 * * * * ? ") //每5秒执行一次
    public void testE(){

        try {
            cybTeamChatWSServer.sendInfo("messageE", "13");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Scheduled(cron="0/15 * * * * ? ") //每5秒执行一次
    public void testF(){

        try {
            cybTeamChatWSServer.sendInfo("messageF", "15");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
