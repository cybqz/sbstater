package com.cyb.chat.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cyb.common.R.R;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint("/teamChatServer/{userId}")
@Component
public class CybTeamChatWSServer {

    static Log log= LogFactory.getLog(CybTeamChatWSServer.class);
    /**静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。*/
    private static int onlineCount = 0;
    /**concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。*/
    private static ConcurrentHashMap<String, CybTeamChatWSServer> USER_WEB_SOCKET_MAP = new ConcurrentHashMap<String, CybTeamChatWSServer>();
    private static ConcurrentHashMap<String, String> USER_INFO_MAP = new ConcurrentHashMap<String, String>();
    private static ConcurrentHashMap<String, List<String>> TEAM_INFO_MAP = new ConcurrentHashMap<String, List<String>>();
    private static ConcurrentHashMap<String, String> USER_TEAM_INFO_MAP = new ConcurrentHashMap<String, String>();
    /**与某个客户端的连接会话，需要通过它来给客户端发送数据*/
    private Session session;
    /**接收userId*/
    private String userId="";

    /**
     * 连接建立成功调用的方法*/
    @OnOpen
    public void onOpen(Session session,@PathParam("userId") String userId) {

        try {
            if(StringUtils.isEmpty(userId) || !USER_TEAM_INFO_MAP.containsKey(userId)){
                log.info("连接失败，用户不存在");
            }else{
                String key = USER_TEAM_INFO_MAP.get(userId) + "_" + userId;
                this.session = session;
                this.userId=userId;
                if(USER_WEB_SOCKET_MAP.containsKey(key)){
                    USER_WEB_SOCKET_MAP.remove(key);
                    USER_WEB_SOCKET_MAP.put(key,this);
                    //加入set中
                }else{
                    USER_WEB_SOCKET_MAP.put(key,this);
                    //加入set中
                    addOnlineCount();
                    //在线数加1
                }

                if(!USER_INFO_MAP.containsKey(userId)){

                }

                log.info("用户连接:"+userId+",当前在线人数为:" + getOnlineCount());
                sendMessage("连接成功");
            }
        } catch (IOException e) {
            log.error("用户:"+userId+",网络异常!!!!!!");
        }

    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        if(USER_WEB_SOCKET_MAP.containsKey(userId)){
           USER_WEB_SOCKET_MAP.remove(userId);
            subOnlineCount();
        }
        log.info("用户退出:"+userId+",当前在线人数为:" + getOnlineCount());
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息*/
    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("recived " + userId +" msg:\t" + message);
        if(!StringUtils.isEmpty(message)){
            try {
                //解析发送的报文
                JSONObject messageObject = JSON.parseObject(message);
                if(!messageObject.containsKey("from-team-id") || !messageObject.containsKey("from-id")){
                    log.info("匿标志消息: " + message);
                }else{

                    String from_id = messageObject.getString("from-id");
                    if(StringUtils.isNotEmpty(from_id)){

                        String from_team_id = messageObject.getString("from-team-id");
                        if(StringUtils.isNotEmpty(from_team_id) && TEAM_INFO_MAP.containsKey(from_team_id)){

                            //发送消息到群内每个用户
                            for(String userId : TEAM_INFO_MAP.get(from_team_id)){

                                if (userId.equals(from_id)){
                                    continue;
                                }
                                String tempKey = from_team_id + "_" + userId;
                                if(USER_WEB_SOCKET_MAP.containsKey(tempKey)){
                                    //追加发送人(防止串改)
                                    messageObject.put("from-id",this.userId);
                                    if(USER_INFO_MAP.containsKey(from_id)){
                                        messageObject.put("from-name",USER_INFO_MAP.get(from_id));
                                    }else{
                                        messageObject.put("from-name","none");
                                    }
                                    USER_WEB_SOCKET_MAP.get(tempKey).sendMessage(messageObject.toJSONString());
                                }else{
                                    log.error("请求的userId:"+from_id+"不在该服务器上");
                                }
                            }
                        }else{
                            log.info("匿群消息: " + message);
                        }
                    }else{
                        log.info("匿名消息: " + message);
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    /**
     *
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.error("用户错误:"+this.userId+",原因:"+error.getMessage());
        error.printStackTrace();
    }
    /**
     * 实现服务器主动推送
     */
    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }


    /**
     * 发送自定义消息
     * */
    public void sendInfo(String message,@PathParam("userId") String userId) throws IOException {
        log.info("发送消息到:"+userId+"，报文:"+message);
        if(!StringUtils.isEmpty(userId)&& USER_WEB_SOCKET_MAP.containsKey(userId)){
            USER_WEB_SOCKET_MAP.get(userId).sendMessage(message);
        }else{
            log.error("用户"+userId+",不在线！");
        }
    }

    public R register(String team, List<String> userIdList, List<String> userNameList){

        try {
            int index = 0;
            for(String userId : userIdList){
                USER_TEAM_INFO_MAP.put(userId, team);
                USER_INFO_MAP.put(userId, userNameList.get(index));
                ++index;
            }

            if(TEAM_INFO_MAP.containsKey(team)){
                TEAM_INFO_MAP.remove(team);
            }
            TEAM_INFO_MAP.put(team, userIdList);
            return R.success("success");
        }catch (Exception e){
            return R.fail("exception:\t" + e.getMessage());
        }
    }

    public synchronized int getOnlineCount() {
        return onlineCount;
    }

    public synchronized void addOnlineCount() {
        CybTeamChatWSServer.onlineCount++;
    }

    public synchronized void subOnlineCount() {
        CybTeamChatWSServer.onlineCount--;
    }
}
