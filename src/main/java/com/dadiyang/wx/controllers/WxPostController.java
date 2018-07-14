package com.dadiyang.wx.controllers;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dadiyang.wx.dto.WsMsg;
import com.dadiyang.wx.dto.WxMessage;
import com.dadiyang.wx.vo.WxPostMsgWsVo;
import com.dadiyang.wx.ws.MsgPusher;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Date;

/**
 * 微信上报事件处理，给Openwx回调用的
 *
 * @author huangxuyang
 * @date 2018/4/7
 */
@Controller
@RequestMapping("/api/post_message")
public class WxPostController extends BaseController {
    private static final Logger logger = Logger.getLogger(WxPostController.class);
    private final MsgPusher msgPusher;

    @Autowired
    public WxPostController(MsgPusher msgPusher) {
        this.msgPusher = msgPusher;
    }

    @RequestMapping(method = {RequestMethod.POST})
    public void postMessage(@RequestBody JSONObject wxMessageJsonObj) {
        String client = request.getParameter("client");
        logger.debug("收到上报事件：client=" + client + ", msgJson=" + wxMessageJsonObj.toJSONString());
        WxMessage wxMessage = JSON.parseObject(wxMessageJsonObj.toJSONString(), WxMessage.class);
        WxPostMsgWsVo vo = new WxPostMsgWsVo(wxMessage.getSender_id(), wxMessage.getReceiver_id(), wxMessage.getContent());
        if (wxMessage.getType() == WxMessage.MsgType.group_message) {
            vo.setGroup(wxMessage.getGroup());
            vo.setGroupId(wxMessage.getGroup_id());
        }
        vo.setId(wxMessage.getId());
        vo.setPostType(wxMessage.getPost_type() != null ? wxMessage.getPost_type().toString() : "");
        vo.setEventType(wxMessage.getEvent() != null ? wxMessage.getEvent().toString() : "");
        vo.setParams(wxMessage.getParams());
        vo.setClazz(toStringOrEmpty(wxMessage.getClazz()));
        vo.setTime(new Date(wxMessage.getTime() * 1000L));
        vo.setFormat(toStringOrEmpty(wxMessage.getFormat()));
        vo.setType(toStringOrEmpty(wxMessage.getType()));
        vo.setFromName(wxMessage.getSender());
        WsMsg.Type msgType = WsMsg.Type.WX_POST_MSG;
        if (wxMessage.getPost_type() == WxMessage.PostType.event) {
            msgType = WsMsg.Type.WX_EVT_POST_MSG;
        }
        WsMsg<WxPostMsgWsVo> msg = new WsMsg<>(msgType.getCode(), vo);
        msgPusher.sendMsg(client, JSON.toJSONStringWithDateFormat(msg, "yyyy-MM-dd HH:mm:ss"));
    }

    private String toStringOrEmpty(Object obj) {
        if (obj == null) {
            return "";
        } else {
            return obj.toString();
        }
    }
}
