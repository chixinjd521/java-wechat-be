package com.dadiyang.wx.interceptors;

import com.dadiyang.wx.config.AppConfig;
import com.dadiyang.wx.dto.ResultBean;
import com.dadiyang.wx.service.SystemUserService;
import com.dadiyang.wx.util.HttpUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

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
    private final AppConfig appConfig;
    private SystemUserService systemUserService;

    @Autowired
    public LoginInterceptor(AppConfig appConfig, SystemUserService systemUserService) {
        this.appConfig = appConfig;
        this.systemUserService = systemUserService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) {
        if (LOGIN_URI.equalsIgnoreCase(request.getRequestURI())) {
            return true;
        }
        // cookie 检验
        if (StringUtils.isNotBlank(systemUserService.auth(request))) {
            return true;
        }
        // cookie校验不通过
        String loginUrl = appConfig.getLoginUrl() + request.getRequestURL().toString();
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
