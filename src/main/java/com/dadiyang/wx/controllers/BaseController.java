package com.dadiyang.wx.controllers;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.ModelAttribute;
import com.dadiyang.wx.util.HttpUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;

/**
 * Controller的基类，提供能用的方法，如获取当前用户名，返回结果，获取request和response对象
 *
 * @author dadiyang
 * @date 2018/6/29
 */
public class BaseController {
    private static Logger logger = Logger.getLogger(BaseController.class);
    protected HttpServletRequest request;
    protected HttpServletResponse response;

    @ModelAttribute
    public void setReqAndRes(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }

    protected String getUserName() {
        Object username = request.getAttribute("loginUser");
        return username == null ? "" : username.toString();
    }

    protected void writeJsonObj(Object obj) {
        HttpUtils.writeJson(response, obj);
    }

    protected void writeStringAsJsonObj(String str) {
        HttpUtils.writeStringAsJsonObj(response, str);
    }

    protected void writeJsonWithDate(Object obj) {
        HttpUtils.writeJsonWithDate(response, obj);
    }

    protected void writeHtml(String html) {
        HttpUtils.writeHtml(response, html);
    }

    public void writeImage(String filePath) {
        HttpUtils.writeImage(response, filePath);
    }

    public void writeImage(byte[] imgByte) {
        HttpUtils.writeImage(response, imgByte);
    }

    public void writeImage(InputStream in) {
        HttpUtils.writeImage(response, in);
    }
}
