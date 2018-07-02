package com.dadiyang.wx.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dadiyang.wx.util.Conf;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Service;
import com.dadiyang.wx.dto.ResultBean;
import com.dadiyang.wx.dto.SendManySingleResult;
import com.dadiyang.wx.dto.WsMsg;
import com.dadiyang.wx.service.WxService;
import com.dadiyang.wx.util.JedisUtil;
import com.dadiyang.wx.util.TimeUtils;
import com.dadiyang.wx.vo.FriendGroupVo;
import com.dadiyang.wx.ws.WsPool;

import java.io.IOException;
import java.util.*;

/**
 * 微信相关服务实现类
 *
 * @author huangxuyang
 * @date 2018/4/6
 */
@Service
public class WxServiceImpl implements WxService {
    private static final Logger logger = Logger.getLogger(WxServiceImpl.class);
    private static final String WX_SEND_MANY_SENDERS_KEY = "wx_send_many_senders";
    private static final String WX_SEND_QUEUE_KEY = "wx_send_queue:";
    private static final String WX_SEND_CONTENT_KEY = "wx_send_content:";
    private static final String WX_FRIEND_GROUPS_KEY = "wx_friend_groups:";
    private static final String WX_FRIEND_GROUP_MEMBER_KEY = "wx_friend_group_members:";
    private static final String GROUP_ID_SYMBOL = "@@";
    private static final String OPENWX_SERVER = Conf.getValue("openwxServer");

    /**
     * 通过redis消息群发
     */
    @Override
    public ResultBean<String> sendToMany(String client, List<String> ids, String content) {
        try {
            if (StringUtils.isBlank(content)) {
                return ResultBean.createFailResult("内容不能为空");
            }
            if (ids == null || ids.size() <= 0) {
                return ResultBean.createFailResult("请选择需要发送的好友");
            }
            if (JedisUtil.getInstance().sismember(WX_SEND_MANY_SENDERS_KEY, client)) {
                return ResultBean.createFailResult("当前有未完成的任务正在进行，请稍候再试");
            }
            // 加入队列，方便支持断点重发
            JedisUtil.getInstance().sadd(WX_SEND_MANY_SENDERS_KEY, client);
            JedisUtil.getInstance().set(WX_SEND_CONTENT_KEY + client, content);
            String[] idsArr = new String[ids.size()];
            ids.toArray(idsArr);
            JedisUtil.getInstance().lpush(WX_SEND_QUEUE_KEY + client, idsArr);
            new SendToManyRunner(client).start();
            return ResultBean.createSuccessResult("群发信息已开启，共需要发送给 " + ids.size() + " 位好友，内容：" + content);
        } catch (Exception e) {
            logger.error("微信群发发生异常：client=" + client + " ids=" + ids + " content=" + content, e);
            return ResultBean.createFailResult("发生异常");
        }

    }

    @Override
    public ResultBean<String> continueSendToMany(String client) {
        try {
            final String content = JedisUtil.getInstance().get(WX_SEND_CONTENT_KEY + client);
            int all = JedisUtil.getInstance().llen(WX_SEND_QUEUE_KEY + client).intValue();
            if (all > 0 && StringUtils.isNotBlank(content)) {
                new SendToManyRunner(client).start();
                return ResultBean.createSuccessResult("共有 " + all + " 条信息需要继续发送，内容为：" + content);
            } else {
                cleanMission(client);
                return ResultBean.createFailResult("没有需要发送的消息");
            }
        } catch (Exception e) {
            logger.error("断点重发微信发生异常：client=" + client, e);
            return ResultBean.createFailResult("发生异常");
        }
    }

    @Override
    public ResultBean<String> cleanMission(String client) {
        try {
            // 移除发送人
            long rs = JedisUtil.getInstance().srem(WX_SEND_MANY_SENDERS_KEY, client);
            // 删除内容
            JedisUtil.getInstance().del(WX_SEND_CONTENT_KEY + client);
            // 清除队列
            JedisUtil.getInstance().del(WX_SEND_QUEUE_KEY + client);
            if (rs > 0) {
                return ResultBean.createSuccessResult("取消任务成功");
            } else {
                return ResultBean.createFailResult("没有任务需要取消");
            }
        } catch (Exception e) {
            logger.error("取消群发任务发生异常：client=" + client, e);
            return ResultBean.createFailResult("发生异常");
        }
    }

    @Override
    public ResultBean<List<FriendGroupVo>> getFriendGroup(String client) {
        if (StringUtils.isBlank(client)) {
            return ResultBean.createFailResult("用户名错误");
        }
        try {
            List<FriendGroupVo> friendGroupVos = new LinkedList<>();
            // 添加分组名称
            Set<String> groups = JedisUtil.getInstance().smembers(WX_FRIEND_GROUPS_KEY + client);
            for (String groupName : groups) {
                Set<String> members = JedisUtil.getInstance().smembers(WX_FRIEND_GROUP_MEMBER_KEY + client + ":" + groupName);
                FriendGroupVo vo = new FriendGroupVo(groupName, new ArrayList<>(members));
                friendGroupVos.add(vo);
            }
            // 分组名称对应的组员列表
            return ResultBean.createSuccessResult(friendGroupVos);
        } catch (Exception e) {
            logger.error("添加分组发生异常：client=" + client, e);
            return ResultBean.createFailResult("发生异常");
        }
    }

    @Override
    public ResultBean<String> addFriendGroup(String client, FriendGroupVo friendGroupVo) {
        if (StringUtils.isBlank(client)) {
            return ResultBean.createFailResult("用户名错误");
        }
        if (StringUtils.isBlank(friendGroupVo.getName())) {
            return ResultBean.createFailResult("请设置分组名称");
        }
        if (CollectionUtils.isEmpty(friendGroupVo.getMembers())) {
            return ResultBean.createFailResult("请选择要添加到该分组的好友");
        }
        try {
            // 添加分组名称
            JedisUtil.getInstance().sadd(WX_FRIEND_GROUPS_KEY + client, friendGroupVo.getName());
            // 分组名称对应的组员列表
            JedisUtil.getInstance().sadd(WX_FRIEND_GROUP_MEMBER_KEY + client + ":" + friendGroupVo.getName(), friendGroupVo.getMembers().toArray(new String[0]));
            return ResultBean.createSuccessResult("分组添加成功");
        } catch (Exception e) {
            logger.error("添加分组发生异常：client=" + client + ", friendGroupVo=" + JSON.toJSONString(friendGroupVo), e);
            return ResultBean.createFailResult("发生异常");
        }
    }

    @Override
    public ResultBean<String> removeFriendGroup(String client, FriendGroupVo friendGroupVo) {
        if (StringUtils.isBlank(client)) {
            return ResultBean.createFailResult("用户名错误");
        }
        if (StringUtils.isBlank(friendGroupVo.getName())) {
            return ResultBean.createFailResult("请选择分组名称");
        }
        try {
            String memberKey = WX_FRIEND_GROUP_MEMBER_KEY + client + ":" + friendGroupVo.getName();
            if (CollectionUtils.isEmpty(friendGroupVo.getMembers())) {
                // 删除分组名称
                JedisUtil.getInstance().srem(WX_FRIEND_GROUPS_KEY + client, friendGroupVo.getName());
                // 删除名称对应的组员列表
                JedisUtil.getInstance().del(memberKey);
            } else {
                JedisUtil.getInstance().srem(memberKey, friendGroupVo.getMembers().toArray(new String[0]));
            }
            return ResultBean.createSuccessResult("分组删除成功");
        } catch (Exception e) {
            logger.error("删除分组发生异常：client=" + client + ", friendGroupVo=" + JSON.toJSONString(friendGroupVo), e);
            return ResultBean.createFailResult("发生异常");
        }
    }


    private class SendToManyRunner extends Thread {
        private String client;

        SendToManyRunner(String client) {
            this.client = client;
        }

        @Override
        public void run() {
            int done = 0;
            boolean suc = false;
            final String queueKey = WX_SEND_QUEUE_KEY + client;
            final String content = JedisUtil.getInstance().get(WX_SEND_CONTENT_KEY + client);
            int all = JedisUtil.getInstance().llen(queueKey).intValue();
            while (true) {
                String uid = JedisUtil.getInstance().lpop(queueKey);
                if (StringUtils.isBlank(uid)) {
                    suc = true;
                    break;
                }
                Integer code = sendFriendMsg(client, uid, content);
                done++;
                SendManySingleResult result = new SendManySingleResult(code, uid, all, done);
                result.setDesc(result.toString());
                if (code != null && code == 1) {
                    result.setDesc("登录状态无效");
                    break;
                }
                TimeUtils.randomSleep(500, 3000);
                WsMsg wsMsg = new WsMsg<>(WsMsg.Type.WX_SEND_MANY.getCode(), result);
                WsPool.sendMessageToUser(client, JSON.toJSONString(wsMsg));
            }
            // 完成任务则清理任务
            if (suc) {
                if (done < all) {
                    SendManySingleResult result = new SendManySingleResult(-1, "", all, done);
                    result.setDesc("任务被取消，进度：" + done + "/" + all);
                    WsMsg wsMsg = new WsMsg<>(WsMsg.Type.WX_SEND_MANY.getCode(), result);
                    WsPool.sendMessageToUser(client, JSON.toJSONString(wsMsg));
                }
                cleanMission(client);
            }
        }
    }

    private Integer sendFriendMsg(String client, String uid, String message) {
        try {
            Map<String, String> data = new HashMap<>();
            data.put("client", client);
            data.put("content", message);
            data.put("id", uid);
            String rsp;
            if (uid.startsWith(GROUP_ID_SYMBOL)) {
                rsp = Jsoup.connect(OPENWX_SERVER + "/openwx/send_group_message").data(data).ignoreContentType(true).get().text();
            } else {
                rsp = Jsoup.connect(OPENWX_SERVER + "/openwx/send_friend_message").data(data).ignoreContentType(true).get().text();
            }
            JSONObject obj = JSON.parseObject(rsp);
            return obj.getInteger("code");
        } catch (IOException e) {
            logger.error("发送消息发生异常", e);
            return -1;
        }
    }


}
