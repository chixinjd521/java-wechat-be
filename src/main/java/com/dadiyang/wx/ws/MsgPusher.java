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
     * @param msg      消息体
     * @return 成功或失败，只要用户有一个客户端推送成功就算成功
     */
    public boolean sendMsg(String username, String msg) {
        if (StringUtils.isBlank(msg)
                || StringUtils.isBlank(username)
                || userSessions.get(username) == null) {
            logger.debug("用户不在线或内容为空, username=" + username + ", msg=" + msg);
            return false;
        }
        logger.debug("发送消息给: " + username + ", 内容:" + msg);
        boolean rs = false;
        Set<WebSocketSession> currentUserSessions = userSessions.get(username);
        for (WebSocketSession session : currentUserSessions) {
            if (!session.isOpen()) {
                // 移除已关闭的链接
                currentUserSessions.remove(session);
                if (currentUserSessions.size() <= 0) {
                    logger.debug("用户连接已全部关闭，移除: username=" + username);
                    userSessions.remove(username);
                }
                continue;
            }
            try {
                session.sendMessage(new TextMessage(msg));
                // 只要有一个成功就算成功
                rs = true;
                logger.debug("消息推送成功username=" + username + ", msg:" + msg + ", sessionId=" + session.getId());
            } catch (IOException e) {
                logger.error("给用户推送 websocket 消息时发生异常: username=" + username + ", msg=" + msg);
            }
        }
        return rs;
    }

    /**
     * 广播信息
     *
     * @param msg 消息体
     * @return 发送成功的用户名列表
     */
    public List<String> sendMessageToAllUsers(String msg) {
        if (StringUtils.isBlank(msg)
                || userSessions.isEmpty()) {
            logger.debug("消息为空或者没有用户在线");
            return Collections.emptyList();
        }
        logger.debug("将内容推送给所有用户开始, msg=" + msg);
        List<String> sucUser = new LinkedList<>();
        Set<String> usernames = userSessions.keySet();
        for (String username : usernames) {
            if (sendMsg(username, msg)) {
                sucUser.add(username);
            }
        }
        logger.debug("将内容推送给所有用户结束成功接收者: " + sucUser + ", msg=" + msg);
        return sucUser;
    }

    boolean addUserSession(String username, WebSocketSession webSocketSession) {
        userSessions.putIfAbsent(username, new CopyOnWriteArraySet<>());
        return userSessions.get(username).add(webSocketSession);
    }
}
