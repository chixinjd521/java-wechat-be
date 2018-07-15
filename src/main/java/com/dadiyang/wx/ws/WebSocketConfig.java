package com.dadiyang.wx.ws;

import com.dadiyang.wx.config.AppConfig;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * @author huangxuyang-sz
 * @date 2018/07/14
 */
@Component
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
    private static final Logger logger = Logger.getLogger(WebSocketConfig.class);
    private final MsgHandler msgHandler;
    private final WebSocketHandshakeInterceptor webSocketHandshakeInterceptor;
    private final String[] allowOrigins;

    @Autowired
    public WebSocketConfig(MsgHandler msgHandler, WebSocketHandshakeInterceptor webSocketHandshakeInterceptor, AppConfig appConfig) {
        this.msgHandler = msgHandler;
        this.webSocketHandshakeInterceptor = webSocketHandshakeInterceptor;
        String origins = appConfig.getAllowOrigins();
        allowOrigins = StringUtils.isBlank(origins) ? new String[]{} : origins.split(",");
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        logger.debug("注册websocket服务");
        registry.addHandler(msgHandler, "/ws").setAllowedOrigins(allowOrigins).addInterceptors(webSocketHandshakeInterceptor);
        logger.debug("注册websocket服务完成");
    }
}