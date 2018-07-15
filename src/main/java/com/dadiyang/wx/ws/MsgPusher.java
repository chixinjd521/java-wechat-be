package com.dadiyang.wx.ws;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author huangxuyang-sz
 * @date 2018/07/14
 */
@Service
public class MsgPusher {
    private static final Logger logger = Logger.getLogger(MsgPusher.class);
    private Map<String, Set<WebSocketSession>> userSessions = new ConcurrentHashMap<>(64);

    /**
     * 发送信息给指定用户
     *
     * @param username 用户名
     * @param message  消息体
     * @return 成功或失败，只要用户有一个客户端推送成功就算成功
     */
    public boolean sendMsg(String username, String message) {
        if (StringUtils.isBlank(username)
                || userSessions.get(username) == null
                || StringUtils.isBlank(message)) {
            return false;
        }
        boolean rs = false;
        Set<WebSocketSession> currentUserSessions = userSessions.get(username);
        for (WebSocketSession session : currentUserSessions) {
            if (!session.isOpen()) {
                // 移除已关闭的链接
                currentUserSessions.remove(session);
                if (currentUserSessions.size() <= 0) {
                    userSessions.remove(username);
                }
                continue;
            }
            try {
                session.sendMessage(new TextMessage(message));
                // 只要有一个成功就算成功
                rs = true;
            } catch (IOException e) {
                logger.error("给用户推送 websocket 消息时发生异常: username=" + username + ", message=" + message);
            }
        }
        return rs;
    }

    /**
     * 广播信息
     *
     * @param message 消息体
     * @return 发送成功的用户名列表
     */
    public List<String> sendMessageToAllUsers(String message) {
        if (StringUtils.isBlank(message)) {
            return Collections.emptyList();
        }
        List<String> sucUser = new LinkedList<>();
        Set<String> usernames = userSessions.keySet();
        for (String username : usernames) {
            if (sendMsg(username, message)) {
                sucUser.add(username);
            }
        }
        return sucUser;
    }

    void addUserSession(String username, WebSocketSession webSocketSession) {
        userSessions.putIfAbsent(username, new CopyOnWriteArraySet<>());
        userSessions.get(username).add(webSocketSession);
    }
}
