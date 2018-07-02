package com.dadiyang.wx.listeners;

import com.dadiyang.wx.ws.WsServer;
import org.apache.log4j.Logger;
import org.java_websocket.WebSocketImpl;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * 项目启动和结束的监听器
 * 用于在项目启动后执行一些操作
 *
 * @author huangxuyang
 * @date 2018/4/6
 */
@Service
public class ContextListener {
    private static final Logger logger = Logger.getLogger(ContextListener.class);
    private static final int WS_PORT = 8787;
    private WsServer wsServer;

    /**
     * 在web启动时执行
     */

    @PostConstruct
    public void applicationStart() {
        startWs();
    }

    /**
     * 启动 websocket
     */
    private void startWs() {
        WebSocketImpl.DEBUG = false;
        wsServer = new WsServer(WS_PORT);
        wsServer.start();
        logger.info("WS启动，监听端口：" + WS_PORT);
    }

    /**
     * 在web结束时执行
     */
    @PreDestroy
    public void applicationEnd() {
        try {
            wsServer.stop();
            logger.info("WS停止");
        } catch (Exception e) {
            logger.error("停止WsServer发生异常", e);
        }
    }
}