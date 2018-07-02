package com.dadiyang.wx.service;

import com.dadiyang.wx.entity.SystemUser;

/**
 * 用户相关的服务
 *
 * @author dadiyang
 * @date 2018/6/28
 */
public interface SystemUserService {

    /**
     * 根据用户名和密码查询用户，若存在则返回该用户的信息
     *
     * @param username 用户名
     * @param password 密码
     * @return 用户信息，用户不存在或密码错误则为 null
     */
    SystemUser getUser(String username, String password);

    /**
     * 添加新用户
     *
     * @param user 用户信息
     * @return 是否成功
     */
    boolean addNewUser(SystemUser user);
}
