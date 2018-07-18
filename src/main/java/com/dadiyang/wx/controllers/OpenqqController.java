package com.dadiyang.wx.controllers;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 处理 QQ 相关的请求
 * 对 openqq 接口的封装，以实现一些管理功能
 *
 * @author dadiyang
 * @date 2018/7/16
 */
@Controller
@RequestMapping("/openqq/**")
public class OpenqqController extends BaseController {
    private static final Logger logger = Logger.getLogger(OpenqqController.class);

    @Value("${openqqServer}")
    private String server;

    @RequestMapping(method = {RequestMethod.POST, RequestMethod.GET})
    public void handle() {
        dispatchToServer(server);
    }

}
