package com.dadiyang.wx.controllers;

import com.dadiyang.wx.util.Conf;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 主页
 *
 * @author dadiyang
 * @date 2018/6/29
 */
@Controller
@RequestMapping("/home")
public class HomeController extends BaseController {
    @GetMapping()
    public ModelAndView index() {
        ModelAndView view = new ModelAndView("home/index");
        view.addObject("version", String.valueOf(Conf.getVersion()));
        view.addObject("cdnPath", Conf.getValue("cdnPath"));
        return view;
    }

    /**
     * 生成新的js/css版本号
     */
    @PostMapping("changeVn")
    public void changeVn() {
        Conf.setVersion(System.currentTimeMillis());
    }
}
