package com.dadiyang.wx.controllers;

import com.dadiyang.wx.config.AppConfig;
import com.dadiyang.wx.dto.ResultBean;
import com.dadiyang.wx.entity.SystemUser;
import com.dadiyang.wx.service.SystemUserService;
import com.dadiyang.wx.util.Crypt;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import java.util.concurrent.TimeUnit;

/**
 * 登录
 *
 * @author dadiyang
 * @date 2017/11/18
 */
@Controller
@RequestMapping("/api")
public class LoginController extends BaseController {
    private static Logger logger = Logger.getLogger(LoginController.class);
    private static final int COOKIE_EXPIRY = 5 * 24 * 60 * 60;
    private final AppConfig appConfig;
    private final SystemUserService systemUserService;
    private final RedisTemplate<String, Object> redisTemplate;

    @Autowired
    public LoginController(AppConfig appConfig, SystemUserService systemUserService, RedisTemplate<String, Object> redisTemplate) {
        this.appConfig = appConfig;
        this.systemUserService = systemUserService;
        this.redisTemplate = redisTemplate;
    }

    @PostMapping("/login")
    public void authLogin(String username, String password) {
        try {
            if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
                writeJsonObj(ResultBean.createFailResult("账号或密码错误"));
                return;
            }
            String debugUser = appConfig.getDebugUser();
            if (StringUtils.isNotBlank(debugUser)) {
                String[] info = debugUser.split(",");
                if (!username.equals(info[0]) || !password.equals(info[1])) {
                    logger.warn("账号或密码错误: " + username + " " + password);
                    writeJsonObj(ResultBean.createFailResult("账号或密码错误"));
                    return;
                }
            } else {
                SystemUser user = systemUserService.getUser(username, password);
                if (user == null) {
                    logger.warn("账号或密码错误: " + username + " " + password);
                    writeJsonObj(ResultBean.createFailResult("账号或密码错误"));
                    return;
                }
            }
            addCookie(username, password);
            writeJsonObj(ResultBean.createSuccessResult("登录成功"));
        } catch (Exception e) {
            writeJsonObj(ResultBean.createFailResult("用户登录发生异常"));
            logger.error("用户登录发生异常：username:" + username + ", password:" + password, e);
        }
    }

    private void addCookie(String username, String password) {
        String sign = Crypt.md5WithSalt(password, appConfig.getCryptRule());
        String key = appConfig.getPsSignKeyPre() + sign;
        redisTemplate.opsForValue().set(key, username);
        redisTemplate.expire(key, COOKIE_EXPIRY, TimeUnit.SECONDS);
        String token = genToken(username, sign, COOKIE_EXPIRY);
        Cookie cookie = new Cookie(appConfig.getAuthCookieName(), token);
        cookie.setPath("/");
        cookie.setMaxAge(COOKIE_EXPIRY);
        response.addCookie(cookie);
    }

    private String genToken(String username, String sign, int expire) {
        String content = username + ":" + sign + ":" + (System.currentTimeMillis() + expire * 1000);
        return Crypt.aesEncode(content, appConfig.getCryptRule());
    }

    @GetMapping("/logout")
    public void logout() {
        ResultBean<String> result = new ResultBean<>();
        Cookie[] cookies = request.getCookies();
        if (null != cookies && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(appConfig.getAuthCookieName())) {
                    cookie.setValue(null);
                    cookie.setMaxAge(0);
                    cookie.setPath("/");
                    response.addCookie(cookie);
                    break;
                }
            }
        }
        writeJsonObj(result);
    }

}
