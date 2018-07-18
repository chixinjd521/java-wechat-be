package com.dadiyang.wx.controllers;

import com.dadiyang.wx.dto.ResultBean;
import com.dadiyang.wx.util.HttpUtils;
import org.apache.log4j.Logger;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

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
    private static final String CONTENT_TYPE_IMG = "image";
    private static final String CONTENT_TYPE_JSON = "application/json";

    @ModelAttribute
    public void setReqAndRes(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }

    protected String getUserName() {
        Object username = request.getAttribute("loginUser");
        return username == null ? "" : username.toString();
    }

    /**
     * 将请求转发到指定服务地址并将返回数据直接 response
     *
     * @param server 服务地址
     */
    protected void dispatchToServer(String server) {
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
            String url = server + request.getRequestURI();
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
