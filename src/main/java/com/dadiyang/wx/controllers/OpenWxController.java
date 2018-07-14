package com.dadiyang.wx.controllers;

import com.dadiyang.wx.config.AppConfig;
import com.dadiyang.wx.dto.ResultBean;
import org.apache.log4j.Logger;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.HashMap;
import java.util.Map;

/**
 * 处理微信相关的请求
 * 对openwx接口的封装，以实现一些管理功能
 *
 * @author huangxuyang
 * @date 2018/3/24
 */
@Controller
@RequestMapping("/openwx/**")
public class OpenWxController extends BaseController {
    private static final Logger logger = Logger.getLogger(WxController.class);
    private static final String CONTENT_TYPE_IMG = "image";
    private static final String CONTENT_TYPE_JSON = "application/json";
    private final AppConfig appConfig;

    @Autowired
    public OpenWxController(AppConfig appConfig) {
        this.appConfig = appConfig;
    }

    @RequestMapping(method = {RequestMethod.POST, RequestMethod.GET})
    public void handle() {
        try {
            Map<String, String[]> pars = request.getParameterMap();
            Map<String, String> data = new HashMap<>();
            if (pars.size() > 0) {
                for (Map.Entry<String, String[]> entry : pars.entrySet()) {
                    if (entry.getValue() != null && entry.getValue().length > 0) {
                        data.put(entry.getKey(), entry.getValue()[0]);
                    }
                }
            }
            String client = getUserName();
            data.put("client", client);
            String url = appConfig.getOpenwxServer() + request.getRequestURI();
            Connection.Response rsp = Jsoup.connect(url).data(data).ignoreContentType(true).execute();
            if (rsp.contentType().startsWith(CONTENT_TYPE_IMG) && rsp.bodyAsBytes() != null) {
                writeImage(rsp.bodyAsBytes());
            } else if (CONTENT_TYPE_JSON.equalsIgnoreCase(rsp.contentType())) {
                writeStringAsJsonObj(rsp.body());
            }
        } catch (Exception e) {
            logger.error("异常:" + request.getRequestURL(), e);
            writeJsonObj(ResultBean.createFailResult("发生异常"));
        }
    }
}
