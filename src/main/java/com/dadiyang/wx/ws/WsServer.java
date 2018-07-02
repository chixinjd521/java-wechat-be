package com.dadiyang.wx.ws;

import com.dadiyang.wx.util.Crypt;
import com.dadiyang.wx.util.JedisUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;

/**
 * websocket主程序类
 * 来源：https://www.cnblogs.com/roy-blog/p/7211761.html
 *
 * @author huangxuyang
 * @date 2018/4/6
 */
public class WsServer extends WebSocketServer {
    private static final Logger logger = Logger.getLogger(WsServer.class);
    private static final String WX_USERS_KEY = "em_wx_online_users:";
    private static final String PING = "~ping~";
    private static final String PONG = "~pong~";
    private static final int HEART_BEAT_TIMEOUT = 60;

    public WsServer(int port) {
        super(new InetSocketAddress(port));
    }

    public WsServer(InetSocketAddress address) {
        super(address);
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        // ws连接的时候触发的代码，onOpen中我们不做任何操作
        String username = auth(handshake);
        if (StringUtils.isNotBlank(username)) {
            userJoin(conn, username);
            JedisUtil.getInstance().setex(WX_USERS_KEY + username, String.valueOf(System.currentTimeMillis()), HEART_BEAT_TIMEOUT);
        } else {
            conn.send("认证失败");
            conn.close();
            logger.warn("Websocket认证失败：token=" + handshake.getResourceDescriptor() + ", cookie=" + handshake.getFieldValue("cookie") + ", remoteAddr="
                    + conn.getRemoteSocketAddress().getAddress() + ":" + conn.getRemoteSocketAddress().getPort());
        }
    }

    private String auth(ClientHandshake handshake) {
        String cookie = handshake.getFieldValue("cookie");
        logger.info("cookie=" + cookie);
        if (StringUtils.isNotBlank(cookie)) {
            String token = cookie.substring(cookie.indexOf("at=") + 3);
            if (token.contains(";")) {
                token = token.substring(0, token.indexOf(";"));
                token = StringUtils.strip(token, "\"");
                token = StringUtils.strip(token, "%22");
            }
            logger.info("token=" + token);
            return Crypt.auth(token);
        }
        return authByDesc(handshake);
    }


    private String authByDesc(ClientHandshake handshake) {
        String desc = handshake.getResourceDescriptor();
        if (StringUtils.isNotBlank(desc)) {
            String token = desc.replaceFirst("/\\?token=", "");
            return Crypt.auth(token);
        }
        return "";
    }

    /**
     * 断开连接时候触发代码
     */
    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        logger.info(WsPool.getUserByWs(conn) + ":断开WebSocket连接：code=" + code + ", reason=" + reason);
        userLeave(conn);
    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        String username = WsPool.getUserByWs(conn);
        // redis在线心跳
        JedisUtil.getInstance().setex(WX_USERS_KEY + username, String.valueOf(System.currentTimeMillis()), HEART_BEAT_TIMEOUT);
        if (null != message && message.startsWith("offline")) {
            userLeave(conn);
        }
        if (PING.equals(message)) {
            conn.send(PONG);
        }
    }

    @Override
    public void onError(WebSocket conn, Exception ex) {
        logger.warn("Websocket连接错误：" + WsPool.getUserByWs(conn), ex);
        userLeave(conn);
    }

    /**
     * 去除掉失效的websocket链接
     *
     * @param conn
     */
    private void userLeave(WebSocket conn) {
        String username = WsPool.getUserByWs(conn);
        JedisUtil.getInstance().del(WX_USERS_KEY + username);
        WsPool.removeUser(conn);
    }

    /**
     * 将websocket加入用户池
     *
     * @param conn
     * @param username
     */
    private void userJoin(WebSocket conn, String username) {
        WsPool.addUser(username, conn);
        logger.info("用户连入WebSocket：" + username);
    }

}