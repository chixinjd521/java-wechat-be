package com.dadiyang.wx.dto;

import java.util.Arrays;

/**
 * @author dadiyang
 */
@SuppressWarnings("AlibabaLowerCamelCaseVariableNaming")
public class WxMessage {
//    {"event":"login","params":["-1","为.com"],"post_type":"event","time":0}
    private PostType post_type;
    private EventType event;
    private String id;
    private MsgType type;
    private MsgClass clazz;
    private FormatType format;
    private String sender_id;
    private String receiver_id;
    private String group_id;
    private String group;
    private String[] params;
    private String receiver_account;
    private String receiver_uid;
    private String sender_name;
    private String content;
    private SenderCategory sender_category;
    private String receiver_markname;
    private String receiver_name;
    private String app_id;
    private String app_url;
    private String receiver;
    private String app_title;
    private String app_name;
    private String sender_markname;
    private String sender;
    private String sender_account;
    private String app_desc;
    private int time;
    private String sender_uid;
    private String card_avatar;
    private String card_id;
    private String card_account;
    private String card_city;
    private String card_province;
    private String card_name;
    private String card_sex;
    private String media_id;
    private String media_path;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public PostType getPost_type() {
        return post_type;
    }

    public void setPost_type(PostType post_type) {
        this.post_type = post_type;
    }

    public MsgType getType() {
        return type;
    }

    public void setType(MsgType type) {
        this.type = type;
    }

    public MsgClass getClazz() {
        return clazz;
    }

    public EventType getEvent() {
        return event;
    }

    public void setEvent(EventType event) {
        this.event = event;
    }

    /**
     * 接口返回的字段是class,但是java中class是关键字。
     * 所以只能通过setClass方法给clazz赋值
     */
    public void setClass(MsgClass clazz) {
        this.clazz = clazz;
    }

    public FormatType getFormat() {
        return format;
    }

    public void setFormat(FormatType format) {
        this.format = format;
    }

    public String getSender_id() {
        return sender_id;
    }

    public void setSender_id(String sender_id) {
        this.sender_id = sender_id;
    }

    public String getReceiver_id() {
        return receiver_id;
    }

    public void setReceiver_id(String receiver_id) {
        this.receiver_id = receiver_id;
    }

    public String getGroup_id() {
        return group_id;
    }

    public void setGroup_id(String group_id) {
        this.group_id = group_id;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getReceiver_account() {
        return receiver_account;
    }

    public void setReceiver_account(String receiver_account) {
        this.receiver_account = receiver_account;
    }

    public String getReceiver_uid() {
        return receiver_uid;
    }

    public void setReceiver_uid(String receiver_uid) {
        this.receiver_uid = receiver_uid;
    }

    public String getSender_name() {
        return sender_name;
    }

    public void setSender_name(String sender_name) {
        this.sender_name = sender_name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public SenderCategory getSender_category() {
        return sender_category;
    }

    public void setSender_category(String sender_category) {
        this.sender_category = SenderCategory.forName(sender_category);
    }

    public String getReceiver_markname() {
        return receiver_markname;
    }

    public void setReceiver_markname(String receiver_markname) {
        this.receiver_markname = receiver_markname;
    }

    public String getReceiver_name() {
        return receiver_name;
    }

    public void setReceiver_name(String receiver_name) {
        this.receiver_name = receiver_name;
    }

    public String getApp_id() {
        return app_id;
    }

    public void setApp_id(String app_id) {
        this.app_id = app_id;
    }

    public String getApp_url() {
        return app_url;
    }

    public void setApp_url(String app_url) {
        this.app_url = app_url;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getApp_title() {
        return app_title;
    }

    public void setApp_title(String app_title) {
        this.app_title = app_title;
    }

    public String getApp_name() {
        return app_name;
    }

    public void setApp_name(String app_name) {
        this.app_name = app_name;
    }

    public String getSender_markname() {
        return sender_markname;
    }

    public void setSender_markname(String sender_markname) {
        this.sender_markname = sender_markname;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getSender_account() {
        return sender_account;
    }

    public void setSender_account(String sender_account) {
        this.sender_account = sender_account;
    }

    public String getApp_desc() {
        return app_desc;
    }

    public void setApp_desc(String app_desc) {
        this.app_desc = app_desc;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getSender_uid() {
        return sender_uid;
    }

    public void setSender_uid(String sender_uid) {
        this.sender_uid = sender_uid;
    }

    public String getCard_avatar() {
        return card_avatar;
    }

    public void setCard_avatar(String card_avatar) {
        this.card_avatar = card_avatar;
    }

    public String getCard_id() {
        return card_id;
    }

    public void setCard_id(String card_id) {
        this.card_id = card_id;
    }

    public String getCard_account() {
        return card_account;
    }

    public void setCard_account(String card_account) {
        this.card_account = card_account;
    }

    public String getCard_city() {
        return card_city;
    }

    public void setCard_city(String card_city) {
        this.card_city = card_city;
    }

    public String getCard_province() {
        return card_province;
    }

    public void setCard_province(String card_province) {
        this.card_province = card_province;
    }

    public String getCard_name() {
        return card_name;
    }

    public void setCard_name(String card_name) {
        this.card_name = card_name;
    }

    public String getCard_sex() {
        return card_sex;
    }

    public void setCard_sex(String card_sex) {
        this.card_sex = card_sex;
    }

    public String[] getParams() {
        return params;
    }

    public void setParams(String[] params) {
        this.params = params;
    }

    public String getMedia_id() {
        return media_id;
    }

    public void setMedia_id(String media_id) {
        this.media_id = media_id;
    }

    public String getMedia_path() {
        return media_path;
    }

    public void setMedia_path(String media_path) {
        this.media_path = media_path;
    }

    @Override
    public String toString() {
        return "WxMessage{" +
                "post_type=" + post_type +
                ", event=" + event +
                ", id='" + id + '\'' +
                ", type=" + type +
                ", clazz=" + clazz +
                ", format=" + format +
                ", sender_id='" + sender_id + '\'' +
                ", receiver_id='" + receiver_id + '\'' +
                ", group_id='" + group_id + '\'' +
                ", group='" + group + '\'' +
                ", params=" + Arrays.toString(params) +
                ", receiver_account='" + receiver_account + '\'' +
                ", receiver_uid='" + receiver_uid + '\'' +
                ", sender_name='" + sender_name + '\'' +
                ", content='" + content + '\'' +
                ", sender_category=" + sender_category +
                ", receiver_markname='" + receiver_markname + '\'' +
                ", receiver_name='" + receiver_name + '\'' +
                ", app_id='" + app_id + '\'' +
                ", app_url='" + app_url + '\'' +
                ", receiver='" + receiver + '\'' +
                ", app_title='" + app_title + '\'' +
                ", app_name='" + app_name + '\'' +
                ", sender_markname='" + sender_markname + '\'' +
                ", sender='" + sender + '\'' +
                ", sender_account='" + sender_account + '\'' +
                ", app_desc='" + app_desc + '\'' +
                ", time=" + time +
                ", sender_uid='" + sender_uid + '\'' +
                ", card_avatar='" + card_avatar + '\'' +
                ", card_id='" + card_id + '\'' +
                ", card_account='" + card_account + '\'' +
                ", card_city='" + card_city + '\'' +
                ", card_province='" + card_province + '\'' +
                ", card_name='" + card_name + '\'' +
                ", card_sex='" + card_sex + '\'' +
                '}';
    }

    /**
     * 接收消息
     * 发送消息
     * 其他事件
     */
    public enum PostType {
        // 接收消息
        receive_message,
        // 发送消息
        send_message,
        // 事件
        event
    }

    public enum EventType {
        // 登录，停止，状态变化，     收到二维码，   好友请求
        login, stop, state_change, input_qrcode, friend_request,
        // 建群，   新好友，     新群员，           退出群，     失去好友，    失去群成员
        new_group, new_friend, new_group_member, lose_group, lose_friend, lose_group_member,
        // 群属性变化，          群组成员属性变化，              好友属性变化
        group_property_change, group_member_property_change, friend_property_change,
        // 用户属性变化，       更新用户信息， 更新好友信息， 更新群信息
        user_property_change, update_user, update_friend, update_group
    }

    /**
     * 消息类型细分:
     * 好友消息
     * 群消息
     * 群提示消息
     */
    public enum MsgType {
        // 好友消息，     群消息，       群提醒
        friend_message, group_message, group_notice
    }

    /**
     * 表明是发送消息还是接收消息
     */
    public enum MsgClass {
        // 发送， 接收
        send, recv
    }

    /**
     * 发送人分类
     */
    public enum SenderCategory {
        // 好友,        公众号
        friend("好友"), official_accounts("公众号"), system_account("系统账号");
        private String name;

        SenderCategory(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public static SenderCategory forName(String name) {
            for (SenderCategory category : SenderCategory.values()) {
                if (category.getName().equals(name)) {
                    return category;
                }
            }
            return null;
        }
    }

    public enum FormatType {
        // 文本消息\媒体（图片、视频、语音）\应用分享\撤回消息\名片分享\转账消息
        text, media, app, revoke, card, payment
    }

    public enum State {
        // 初始化->加载->等待扫描二维码->确认登录->更新数据->运行->停止
        init, loading, scaning, confirming, updating, running, stop
    }
}
