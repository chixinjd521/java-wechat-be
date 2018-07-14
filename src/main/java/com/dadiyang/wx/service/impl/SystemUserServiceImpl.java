package com.dadiyang.wx.service.impl;

import com.dadiyang.wx.config.AppConfig;
import com.dadiyang.wx.dao.SystemUserMapper;
import com.dadiyang.wx.entity.SystemUser;
import com.dadiyang.wx.entity.SystemUserExample;
import com.dadiyang.wx.service.SystemUserService;
import com.dadiyang.wx.util.Crypt;
import com.dadiyang.wx.util.RedisUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * 用户相关服务实现
 *
 * @author huangxuyang
 */
@Service
public class SystemUserServiceImpl implements SystemUserService {
    private static final Logger logger = Logger.getLogger(SystemUserServiceImpl.class);
    private final SystemUserMapper systemUserMapper;
    private final RedisUtil redisUtil;
    private AppConfig appConfig;

    @Autowired
    public SystemUserServiceImpl(SystemUserMapper systemUserMapper, RedisUtil redisUtil, AppConfig appConfig) {
        this.systemUserMapper = systemUserMapper;
        this.redisUtil = redisUtil;
        this.appConfig = appConfig;
    }

    @Override
    public SystemUser getUser(String username, String password) {
        SystemUserExample example = new SystemUserExample();
        // 加盐
        String pwd = Crypt.md5WithSalt(password, appConfig.getCryptRule());
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
        user.setPassword(Crypt.md5WithSalt(user.getPassword(), appConfig.getCryptRule()));
        int rs = systemUserMapper.insert(user);
        user.setPassword(pswd);
        return rs > 0;
    }

    @Override
    public String auth(HttpServletRequest request) {
        try {
            Cookie[] cookies = request.getCookies();
            if (null == cookies || cookies.length <= 0) {
                return "";
            }
            // 找到认证的 cookie
            Optional<Cookie> cookieOptional = Arrays.stream(cookies)
                    .filter(c -> appConfig.getAuthCookieName().equals(c.getName()))
                    .findAny();
            // 不存在则直接返回
            if (!cookieOptional.isPresent()) {
                return "";
            }
            // 存在则校验
            String token = cookieOptional.get().getValue();
            if (StringUtils.isBlank(token)) {
                return "";
            }
            // 解密
            String str = Crypt.aesDecode(token, appConfig.getCryptRule());
            if (StringUtils.isBlank(str)) {
                return "";
            }
            String[] strings = str.split(":");
            String username = strings[0];
            String sign = strings[1];
            long expTime = Long.parseLong(strings[2]);
            // 是否过期
            if (System.currentTimeMillis() > expTime) {
                return "";
            }
            // 校验签名
            if (checkSign(username, sign)) {
                return "";
            }
            // 签名校验通过
            if (StringUtils.isNotBlank(username)) {
                request.setAttribute("loginUser", username);
                return username;
            }
        } catch (Exception e) {
            logger.error("auth", e);
        }
        return "";
    }

    private boolean checkSign(String username, String sign) {
        try {
            Object cache = redisUtil.get(appConfig.getPsSignKeyPre() + sign);
            return !Objects.equals(cache, username);
        } catch (Exception e) {
            logger.error("签名检验发生异常: username=" + username + ", sign=" + sign, e);
            // 降级
            return true;
        }
    }
}
