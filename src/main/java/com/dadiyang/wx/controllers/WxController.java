package com.dadiyang.wx.controllers;

import com.dadiyang.wx.dto.ResultBean;
import com.dadiyang.wx.service.WxService;
import com.dadiyang.wx.vo.FriendGroupVo;
import com.dadiyang.wx.vo.SendToManyVo;
import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 处理微信相关的请求
 * 对openwx接口的封装，以实现一些管理功能
 *
 * @author huangxuyang
 * @date 2018/3/24
 */
@Controller
@RequestMapping("api/wx")
public class WxController extends BaseController {
    private static final Logger logger = Logger.getLogger(WxController.class);
    private final WxService wxService;

    @Autowired
    public WxController(WxService wxService) {
        this.wxService = wxService;
    }

    /**
     * 群发
     */
    @PostMapping("sendToMany")
    public void sentToMany(SendToManyVo sendToManyVo) {
        List<String> ids = sendToManyVo.getIds();
        String content = sendToManyVo.getContent();
        ResultBean<String> rs = wxService.sendToMany(getUserName(), ids, content);
        writeJsonObj(rs);
    }

    /**
     * 恢复暂停的发送任务
     */
    @PostMapping("continueSendToMany")
    public void continueSendToMany() {
        ResultBean<String> rs = wxService.continueSendToMany(getUserName());
        writeJsonObj(rs);
    }

    /**
     * 取消群发任务
     */
    @PostMapping("cleanMission")
    public void cleanMission() {
        ResultBean<String> rs = wxService.cleanMission(getUserName());
        writeJsonObj(rs);
    }

    /**
     * 获取好友分组信息
     */
    @GetMapping("getFriendGroups")
    public void getFriendGroups() {
        writeJsonObj(wxService.getFriendGroup(getUserName()));
    }

    /**
     * 新建分组
     *
     * @param friendGroupVo 所需参数
     */
    @PostMapping("addFriendGroup")
    public void addFriendGroup(FriendGroupVo friendGroupVo) {
        writeJsonObj(wxService.addFriendGroup(getUserName(), friendGroupVo));
    }

    /**
     * 删除组员成功
     *
     * @param friendGroupVo 所需参数
     */
    @PostMapping("removeFriendGroupMembers")
    public void removeFriendGroupMembers(FriendGroupVo friendGroupVo) {
        if (CollectionUtils.isEmpty(friendGroupVo.getMembers())) {
            writeJsonObj(ResultBean.createFailResult("请选择要删除的组内好友"));
            return;
        }
        writeJsonObj(wxService.removeFriendGroup(getUserName(), friendGroupVo));
    }

    /**
     * 删除分组，如果传递的参数中members为空则删除整个分组
     * 建议将组内成员移除调用 removeFriendGroupMembers 要删除整个分组再调用此方法
     *
     * @param name 组名
     */
    @DeleteMapping("removeFriendGroup/{name}")
    public void removeFriendGroup(@PathVariable(value = "name") String name) {
        ResultBean<String> rs = wxService.removeFriendGroup(getUserName(), new FriendGroupVo(name));
        writeJsonObj(rs);
    }
}
