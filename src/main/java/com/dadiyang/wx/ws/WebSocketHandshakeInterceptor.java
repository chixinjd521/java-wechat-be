package com.dadiyang.wx.ws;

import com.dadiyang.wx.service.SystemUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

/**
 * @author dadiyang
 * @date 2018/7/14
 */
@Service
public class WebSocketHandshakeInterceptor implements HandshakeInterceptor {
    private SystemUserService systemUserService;

    @Autowired
    public WebSocketHandshakeInterceptor(SystemUserService systemUserService) {
        this.systemUserService = systemUserService;
    }

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        if (request instanceof ServletServerHttpRequest) {
            attributes.put("loginUser", systemUserService.auth(((ServletServerHttpRequest) request).getServletRequest()));
        }
        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {
    }
}