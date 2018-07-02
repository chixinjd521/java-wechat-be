package com.dadiyang.wx.vo;

import com.dadiyang.wx.dto.WxMessage;

/**
 * 微信上报消息
 *
 * @author huangxuyang
 * @date 2018/4/7
 */
public class WxPostMsgVo {
    private String client;
    private String msgJson;
    private String authKey;
    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getMsgJson() {
        return msgJson;
    }

    public void setMsgJson(String msgJson) {
        this.msgJson = msgJson;
    }

    public String getAuthKey() {
        return authKey;
    }

    public void setAuthKey(String authKey) {
        this.authKey = authKey;
    }
}
