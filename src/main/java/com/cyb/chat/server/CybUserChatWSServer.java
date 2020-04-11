package com.cyb.chat.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint("/userChatServer/{userId}")
@Component
public class CybUserChatWSServer {

    static Log log= LogFactory.getLog(CybUserChatWSServer.class);
    /**静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。*/
    private static int onlineCount = 0;
    /**concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。*/
    private static ConcurrentHashMap<String, CybUserChatWSServer> WEB_SOCKET_MAP_USER = new ConcurrentHashMap<String, CybUserChatWSServer>();
    private static ConcurrentHashMap<String, String> USER_INFO_MAP = new ConcurrentHashMap<String, String>();
    /**与某个客户端的连接会话，需要通过它来给客户端发送数据*/
    private Session session;
    /**接收userId*/
    private String userId="";

    /**
     * 连接建立成功调用的方法*/
    @OnOpen
    public void onOpen(Session session,@PathParam("userId") String userId) {

        try {
            if(StringUtils.isNotEmpty(userId)){
                this.session = session;
                this.userId=userId;
                if(WEB_SOCKET_MAP_USER.containsKey(userId)){
                    WEB_SOCKET_MAP_USER.remove(userId);
                    WEB_SOCKET_MAP_USER.put(userId,this);
                    //加入set中
                }else{
                    WEB_SOCKET_MAP_USER.put(userId,this);
                    //加入set中
                    addOnlineCount();
                    //在线数加1
                }

                if(!USER_INFO_MAP.contains(userId)){

                }

                log.info("用户连接:"+userId+",当前在线人数为:" + getOnlineCount());
                sendMessage("连接成功");
            }else{
                sendMessage("连接失败，用户编号不存在");
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
        if(WEB_SOCKET_MAP_USER.containsKey(userId)){
            WEB_SOCKET_MAP_USER.remove(userId);
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
        log.info("用户消息:"+userId+",报文:"+message);
        //可以群发消息
        //消息保存到数据库、redis
        if(!StringUtils.isEmpty(message)){
            log.info("recived msg:\t" + message);
            try {
                //解析发送的报文
                JSONObject messageObject = JSON.parseObject(message);
                String to_id = messageObject.getString("from-id");
                if(StringUtils.isNotEmpty(to_id)){
                    //传送给对应toUserId用户的websocket
                    if(WEB_SOCKET_MAP_USER.containsKey(to_id)){
                        //追加发送人(防止串改)
                        messageObject.put("from-id",this.userId);
                        if(USER_INFO_MAP.contains(to_id)){
                            messageObject.put("from-name",USER_INFO_MAP.get(to_id));
                        }else{
                            messageObject.put("from-name","none");
                        }
                        WEB_SOCKET_MAP_USER.get(to_id).sendMessage(messageObject.toJSONString());
                    }else{
                        log.error("请求的userId:"+to_id+"不在该服务器上");
                        //否则不在这个服务器上，发送到mysql或者redis
                    }
                }else{
                    log.info("匿名消息: " + message);
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
        if(!StringUtils.isEmpty(userId)&& WEB_SOCKET_MAP_USER.containsKey(userId)){
            WEB_SOCKET_MAP_USER.get(userId).sendMessage(message);
        }else{
            log.error("用户"+userId+",不在线！");
        }
    }

    public synchronized int getOnlineCount() {
        return onlineCount;
    }

    public synchronized void addOnlineCount() {
        CybUserChatWSServer.onlineCount++;
    }

    public synchronized void subOnlineCount() {
        CybUserChatWSServer.onlineCount--;
    }
}
