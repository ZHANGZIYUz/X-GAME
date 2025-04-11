package com.example.backend.controller;


import com.example.backend.common.ErrorCode;
import com.example.backend.config.GetHttpSessionConfig;
import com.example.backend.exception.MyException;
import com.example.backend.model.domain.ChatRecord;
import com.example.backend.model.domain.User;
import com.example.backend.service.ChatRecordService;
import com.example.backend.utils.SpringContextHolder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.example.backend.contant.UserContant.USER_LOGIN_STATE;

@ServerEndpoint(value = "/user/chat", configurator = GetHttpSessionConfig.class)
@Component
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
//@CrossOrigin(origins = "http://120.27.243.219", allowCredentials = "true")
public class ChatEndpoint {

    private ChatRecordService chatRecordService;

    // 获取ChatRecordService实例
    private ChatRecordService getChatRecordService() {
        if (chatRecordService == null) {
            chatRecordService = SpringContextHolder.getBean(ChatRecordService.class);
        }
        return chatRecordService;
    }

    /**
     * 用来存放在线用户和对应的session
     */
    private static final Map<String, Session> onlineUsers = new ConcurrentHashMap<>();

    private HttpSession httpSession;

    /**
     * 建立websocket连接后,调用该方法
     * 该方法将用户名跟websocket的session存放到一个map集合中，这个集合里面放着所有上线的用户
     *
     * @param session
     */
    @OnOpen
    public void onOpen(Session session, EndpointConfig config) {
        this.httpSession = (HttpSession) config.getUserProperties().get(HttpSession.class.getName());
        User currentUser = (User) this.httpSession.getAttribute(USER_LOGIN_STATE);
        String username = currentUser.getUsername();
        onlineUsers.put(username, session);
    }

    /**
     * 浏览器发送消息到服务端，该方法被调用
     * 该方法给所有在线的用户广播消息
     *
     * @param message
     */
    @OnMessage
    public void onMessage(String message) {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = null;
        try {
            jsonNode = mapper.readTree(message);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        String receiveUser = jsonNode.get("username").asText();
        String content = jsonNode.get("content").asText();
        User currentUser = (User) this.httpSession.getAttribute(USER_LOGIN_STATE);
        if (currentUser != null) {
            String username = currentUser.getUsername();
            String formattedMessage = username + ": " + content;

            // 将聊天消息存放到数据库中
            ChatRecord chatRecord = new ChatRecord();
            chatRecord.setRecord(content);
            chatRecord.setSendUser(username);
            chatRecord.setReceiveUser(receiveUser);
            boolean save = getChatRecordService().save(chatRecord);
            if (!save) {
                throw new MyException(ErrorCode.PARAMS_ERROR, "聊天记录存放数据库错误");
            }

            // 向接受用户发送消息
            Session receiveSession = onlineUsers.get(receiveUser);
            if (receiveSession != null && receiveSession.isOpen()) {
                try {
                    receiveSession.getBasicRemote().sendText(formattedMessage);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 断开websocket连接时,调用该方法
     * 该方法将该用户从map集合中去除
     *
     * @param session
     */
    @OnClose
    public void onClose(Session session) {
        User currentUser = (User) this.httpSession.getAttribute(USER_LOGIN_STATE);
        String username = currentUser.getUsername();
        onlineUsers.remove(username);
    }

    /**
     * 有异常时调用
     *
     * @param session
     * @param throwable
     */
    @OnError
    public void onError(Session session, Throwable throwable) {
        throwable.printStackTrace();
    }
}
