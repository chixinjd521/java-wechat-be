package com.dadiyang.wx.interceptors;

import com.dadiyang.wx.dto.ResultBean;
import com.dadiyang.wx.util.Conf;
import com.dadiyang.wx.util.Crypt;
import com.dadiyang.wx.util.HttpUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登录状态校验拦截器
 *
 * @author huangxuyang
 * @date 2018/3/25
 */
@Component
public class LoginInterceptor implements HandlerInterceptor {
    private static final Logger logger = Logger.getLogger(LoginInterceptor.class);
    private static final String LOGIN_URI = "/api/login";
    private static final String API_PRE = "/api";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) {
        if (LOGIN_URI.equalsIgnoreCase(request.getRequestURI())) {
            return true;
        }
        Cookie[] cookies = request.getCookies();
        if (null != cookies && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(Conf.getValue("authCookieName"))) {
                    String auth = cookie.getValue();
                    String username = Crypt.auth(auth);
                    if (StringUtils.isNotBlank(username)) {
                        request.setAttribute("loginUser", username);
                        return true;
                    }
                }
            }
        }
        // cookie校验不通过
        String loginUrl = Conf.getValue("loginUrl") + request.getRequestURL().toString();
        // 调用 api 则返回 json 格式数据
        if (request.getRequestURI().startsWith(API_PRE)) {
            ResultBean<String> rs = new ResultBean<>();
            rs.setCode(ResultBean.NEED_LOGIN);
            rs.setMsg(loginUrl);
            HttpUtils.writeJson(response, rs);
        } else {
            // 否则跳转到登录界面
            try {
                response.sendRedirect(loginUrl);
            } catch (IOException e) {
                logger.error("跳转失败", e);
            }
        }
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
