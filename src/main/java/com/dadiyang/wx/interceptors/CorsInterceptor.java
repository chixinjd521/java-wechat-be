package com.dadiyang.wx.interceptors;

import com.dadiyang.wx.util.Conf;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 跨域拦截器
 *
 * @author huangxuyang
 * @date 2018/3/16
 */
public class CorsInterceptor implements HandlerInterceptor {
    private static final Logger logger = Logger.getLogger(CorsInterceptor.class);
    private static final String EXCLUDED_URI = "/api/login";
    private static final List<String> ALLOW_ORIGINS = Conf.getValues("allowOrigins");
    private static final String ORIGIN = "Origin";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        if (EXCLUDED_URI.equalsIgnoreCase(request.getRequestURI())) {
            return true;
        }
        String orgHeader = request.getHeader(ORIGIN);
        if (orgHeader != null && ALLOW_ORIGINS.contains(orgHeader)) {
            response.addHeader("Access-Control-Allow-Origin", orgHeader);
            response.addHeader("Access-Control-Allow-Credentials", "true");
            response.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, PATCH, PUT, HEAD");
            response.addHeader("Access-Control-Allow-Headers", "Content-Type, Content-Length, Authorization, Accept, X-Requested-With");
            response.addHeader("Access-Control-Max-Age", "3600");
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
