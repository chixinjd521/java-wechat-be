package com.dadiyang.wx.ws;

import com.dadiyang.wx.config.AppConfig;
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
    private final MsgHandler msgHandler;
    private final WebSocketHandshakeInterceptor webSocketHandshakeInterceptor;
    private final String[] allowOrigins;

    @Autowired
    public WebSocketConfig(MsgHandler msgHandler, WebSocketHandshakeInterceptor webSocketHandshakeInterceptor, AppConfig appConfig) {
        this.msgHandler = msgHandler;
        this.webSocketHandshakeInterceptor = webSocketHandshakeInterceptor;
        String origins = appConfig.getAllowOrigins();
        allowOrigins = origins == null ? new String[]{} : origins.split(",");
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(msgHandler, "/ws").setAllowedOrigins(allowOrigins).addInterceptors(webSocketHandshakeInterceptor);
    }
}