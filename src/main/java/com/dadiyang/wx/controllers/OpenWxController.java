package com.dadiyang.wx.controllers;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 处理微信相关的请求
 * 对openwx接口的封装，以实现一些管理功能
 *
 * @author dadiyang
 * @date 2018/3/24
 */
@Controller
@RequestMapping("/openwx/**")
public class OpenWxController extends BaseController {
    private static final Logger logger = Logger.getLogger(OpenWxController.class);

    @Value("${openwxServer}")
    private String server;

    @RequestMapping(method = {RequestMethod.POST, RequestMethod.GET})
    public void handle() {
        dispatchToServer(server);
    }

}
