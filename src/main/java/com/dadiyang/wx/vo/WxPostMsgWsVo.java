package com.dadiyang.wx.vo;

import java.util.Date;

/**
 * 微信上报消息通过Websocket传递给前端使用的Vo
 *
 * @author huangxuyang
 * @date 2018/4/7
 */
public class WxPostMsgWsVo {
    private String id;
    private String from;
    private String fromName;
    private String to;
    private String toName;
    /**
     * friend_message, group_message, group_notice
     */
    private String type;
    /**
     *  send, recv
     */
    private String clazz;
    private String format;
    private String content;
    private Date time;
    private String group;
    private String groupId;
    private String postType;
    private String eventType;
    private String[] params;

    public WxPostMsgWsVo() {
    }

    public WxPostMsgWsVo(String from, String to, String content) {
        this.from = from;
        this.to = to;
        this.content = content;
    }

    public WxPostMsgWsVo(String content) {
        this.content = content;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public String getFromName() {
        return fromName;
    }

    public void setFromName(String fromName) {
        this.fromName = fromName;
    }

    public String getToName() {
        return toName;
    }

    public void setToName(String toName) {
        this.toName = toName;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getPostType() {
        return postType;
    }

    public void setPostType(String postType) {
        this.postType = postType;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String[] getParams() {
        return params;
    }

    public void setParams(String[] params) {
        this.params = params;
    }
}
