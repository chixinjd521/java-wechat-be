package com.dadiyang.wx.ws;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

/**
 * @author dadiyang
 * @date 2018/7/14
 */
@Service
public class MsgHandler extends AbstractWebSocketHandler {
    private static final Logger logger = Logger.getLogger(MsgHandler.class);

    private final MsgPusher msgPusher;

    @Autowired
    public MsgHandler(MsgPusher msgPusher) {
        super();
        this.msgPusher = msgPusher;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        Object username = session.getAttributes().get("loginUser");
        if (username != null && username.toString().length() > 0) {
            logger.info("用户接入 websocket: " + username);
            msgPusher.addUserSession(username.toString(), session);
        }
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        super.handleTextMessage(session, message);
        logger.debug("收到ws消息：" + message.getPayload());
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        super.handleTransportError(session, exception);
    }


}
