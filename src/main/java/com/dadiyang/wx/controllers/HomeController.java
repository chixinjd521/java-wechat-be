package com.dadiyang.wx.controllers;

import com.dadiyang.wx.config.AppConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.concurrent.atomic.AtomicLong;

/**
 * 主页
 *
 * @author dadiyang
 * @date 2018/6/29
 */
@Controller
@RequestMapping("home")
public class HomeController extends BaseController {
    private final AppConfig appConfig;
    private volatile AtomicLong version = new AtomicLong(System.currentTimeMillis());

    @Autowired
    public HomeController(AppConfig appConfig) {
        this.appConfig = appConfig;
    }

    @GetMapping()
    public ModelAndView index() {
        ModelAndView view = new ModelAndView("home/index");
        view.addObject("version", String.valueOf(version.get()));
        view.addObject("cdnPath", appConfig.getCdnPath());
        return view;
    }

    /**
     * 生成新的js/css版本号
     */
    @PostMapping("changeVn")
    public void changeVn() {
        version.incrementAndGet();
    }
}
