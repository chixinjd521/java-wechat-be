package com.dadiyang.wx.ws;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.java_websocket.WebSocket;

import java.util.*;

/**
 * websocket连接池
 * 来源：https://www.cnblogs.com/roy-blog/p/7211761.html
 *
 * @author huangxuyang
 * @date 2018/4/6
 */
public class WsPool {
    private static final Logger logger = Logger.getLogger(WsPool.class);
    private static final Map<WebSocket, String> wsUserMap = new HashMap<WebSocket, String>();

    /**
     * 通过websocket连接获取其对应的用户
     *
     * @param conn
     * @return
     */
    public static String getUserByWs(WebSocket conn) {
        return wsUserMap.get(conn);
    }

    /**
     * 根据userName获取WebSocket,这是一个list,此处取第一个
     * 因为有可能多个websocket对应一个userName（但一般是只有一个，因为在close方法中，我们将失效的websocket连接去除了）
     *
     * @param username
     */
    public static Set<WebSocket> getWsByUser(String username) {
        Set<WebSocket> ws = new HashSet<>();
        Set<WebSocket> keySet = wsUserMap.keySet();
        for (WebSocket conn : keySet) {
            String cuser = wsUserMap.get(conn);
            if (cuser.equals(username)) {
                ws.add(conn);
            }
        }

        return ws;
    }

    /**
     * 向连接池中添加连接
     */
    public static void addUser(String userName, WebSocket conn) {
        wsUserMap.put(conn, userName);
    }

    /**
     * 获取所有连接池中的用户，因为set是不允许重复的，所以可以得到无重复的user数组
     */
    public static Collection<String> getOnlineUser() {
        Collection<String> setUser = wsUserMap.values();
        List<String> setUsers = new ArrayList<>(setUser);
        return setUsers;
    }

    /**
     * 移除连接池中的连接
     */
    public static boolean removeUser(WebSocket conn) {
        if (wsUserMap.containsKey(conn)) {
            wsUserMap.remove(conn);
            return true;
        } else {
            return false;
        }
    }

    /**
     * 向特定的用户发送数据
     *
     * @param message 消息内容
     */
    public static void sendMessageToUser(WebSocket conn, String message) {
        if (null != conn && null != wsUserMap.get(conn)) {
            conn.send(message);
        }
    }

    /**
     * 向特定的用户发送数据
     *
     * @param message 消息内容
     */
    public static void sendMessageToUser(String username, String message) {
        if (StringUtils.isNotBlank(username)) {
            Set<WebSocket> conns = getWsByUser(username);
            logger.info(username + ": 有" + conns.size() + "个客户端在线，发送ws消息：" + message);
            if (!conns.isEmpty()) {
                for (WebSocket conn : conns) {
                    conn.send(message);
                }
            }
        }
    }

    /**
     * 向所有的用户发送消息
     *
     * @param message 消息内容
     */
    public static void sendMessageToAll(String message) {
        Set<WebSocket> keySet = wsUserMap.keySet();
        synchronized (keySet) {
            for (WebSocket conn : keySet) {
                String user = wsUserMap.get(conn);
                if (user != null) {
                    conn.send(message);
                }
            }
        }
    }

}