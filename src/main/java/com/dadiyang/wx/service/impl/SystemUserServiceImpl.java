package com.dadiyang.wx.service.impl;

import com.dadiyang.wx.dao.SystemUserMapper;
import com.dadiyang.wx.entity.SystemUser;
import com.dadiyang.wx.entity.SystemUserExample;
import com.dadiyang.wx.service.SystemUserService;
import com.dadiyang.wx.util.Crypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户相关服务实现
 *
 * @author huangxuyang
 */
@Service
public class SystemUserServiceImpl implements SystemUserService {
    private final SystemUserMapper systemUserMapper;

    @Autowired
    public SystemUserServiceImpl(SystemUserMapper systemUserMapper) {
        this.systemUserMapper = systemUserMapper;
    }

    @Override
    public SystemUser getUser(String username, String password) {
        SystemUserExample example = new SystemUserExample();
        // 加盐
        String pwd = Crypt.md5WithSalt(password);
        example.createCriteria().andUsernameEqualTo(username).andPasswordEqualTo(pwd);
        List<SystemUser> users = systemUserMapper.selectByExample(example);
        if (users == null || users.size() <= 0) {
            return null;
        }
        users.get(0).setPassword("");
        return users.get(0);
    }

    @Override
    public boolean addNewUser(SystemUser user) {
        // 加盐加密
        String pswd = user.getPassword();
        user.setPassword(Crypt.md5WithSalt(user.getPassword()));
        int rs = systemUserMapper.insert(user);
        user.setPassword(pswd);
        return rs > 0;
    }
}
