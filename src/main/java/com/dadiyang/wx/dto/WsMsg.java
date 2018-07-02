package com.dadiyang.wx.dto;

/**
 * WebSocket统一消息格式
 */
public class WsMsg<T> {
    private int type;
    private T msg;

    public WsMsg(int type) {
        this.type = type;
    }

    public WsMsg(int type, T msg) {
        this.type = type;
        this.msg = msg;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public T getMsg() {
        return msg;
    }

    public void setMsg(T msg) {
        this.msg = msg;
    }

    public enum Type {
        /**
         * 群发
         */
        WX_SEND_MANY(1),
        /**
         * 微信消息上报
         */
        WX_POST_MSG(2),
        /**
         * 微信事件上报
         */
        WX_EVT_POST_MSG(3);
        private int code;

        Type(int code) {
            this.code = code;
        }

        public int getCode() {
            return code;
        }
    }
}
