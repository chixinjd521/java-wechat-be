package com.dadiyang.wx.controllers;

import com.dadiyang.wx.dto.ResultBean;
import com.dadiyang.wx.entity.SystemUser;
import com.dadiyang.wx.service.SystemUserService;
import com.dadiyang.wx.util.Conf;
import com.dadiyang.wx.util.Crypt;
import com.dadiyang.wx.util.JedisUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;

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
    @Autowired
    private SystemUserService systemUserService;

    @PostMapping("/login")
    public void authLogin(String username, String password) {
        try {
            if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
                writeJsonObj(ResultBean.createFailResult("账号或密码错误"));
                return;
            }
            String debugUser = Conf.getValue("debugUser");
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
        String sign = Crypt.md5WithSalt(password);
        JedisUtil.getInstance().setex(Conf.PS_SIGN_KEY + sign, username, COOKIE_EXPIRY);
        String token = Crypt.genToken(username, sign, COOKIE_EXPIRY);
        Cookie cookie = new Cookie(Conf.getValue("authCookieName"), token);
        cookie.setPath("/");
        cookie.setMaxAge(COOKIE_EXPIRY);
        response.addCookie(cookie);
    }


    @GetMapping("/logout")
    public void logout() {
        ResultBean<String> result = new ResultBean<>();
        Cookie[] cookies = request.getCookies();
        if (null != cookies && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(Conf.getValue("authCookieName"))) {
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
