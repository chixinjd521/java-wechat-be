package com.dadiyang.wx.service;

import com.dadiyang.wx.dto.ResultBean;
import com.dadiyang.wx.vo.FriendGroupVo;
import org.springframework.stereotype.Service;
import com.dadiyang.wx.dto.ResultBean;
import com.dadiyang.wx.vo.FriendGroupVo;

import java.util.List;

/**
 * 微信相关服务
 *
 * @author huangxuyang
 * @date 2018/4/6
 */
@Service
public interface WxService {
    /**
     * 群发信息
     *
     * @param client
     * @param ids
     * @param content
     * @return
     */
    ResultBean<String> sendToMany(String client, List<String> ids, String content);

    /**
     * 断点续发
     *
     * @param client
     * @return
     */
    ResultBean<String> continueSendToMany(String client);

    /**
     * 清除发送
     *
     * @param client
     */
    ResultBean<String> cleanMission(String client);

    /**
     * 获取所有好友分组信息
     *
     * @param client
     * @return
     */
    ResultBean<List<FriendGroupVo>> getFriendGroup(String client);

    /**
     * 添加好友分组
     *
     * @param client
     * @param friendGroupVo
     * @return
     */
    ResultBean<String> addFriendGroup(String client, FriendGroupVo friendGroupVo);

    /**
     * 移除好友分组
     *
     * @param client
     * @param friendGroupVo
     * @return
     */
    ResultBean<String> removeFriendGroup(String client, FriendGroupVo friendGroupVo);
}
