package com.dadiyang.wx.util;

import com.alibaba.fastjson.JSON;
import org.apache.log4j.Logger;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

/**
 * @author dadiyang
 * @date 2017/11/18
 */
public class HttpUtils {
    private static Logger logger = Logger.getLogger(HttpUtils.class);

    public static void writeImage(HttpServletResponse response, String filePath) {
        try (FileInputStream fin = new FileInputStream(filePath)) {
            writeImage(response, fin);
        } catch (Exception e) {
            logger.error("出错了。", e);
        }
    }

    public static void writeImage(HttpServletResponse response, InputStream in) {
        try (ServletOutputStream outputStream = response.getOutputStream()) {
            response.setContentType("image/jpeg");
            byte[] imgByte = new byte[in.available()];
            int length = in.read(imgByte);
            if (length > 0) {
                writeImage(response, imgByte);
            }
        } catch (Exception e) {
            logger.error("出错了。", e);
        }
    }

    public static void writeImage(HttpServletResponse response, byte[] imgByte) {
        if (imgByte.length <= 0) {
            logger.info("要发送的图片为空");
            return;
        }
        try (ServletOutputStream outputStream = response.getOutputStream()) {
            response.setContentType("image/jpeg");
            response.setContentLength(imgByte.length);
            outputStream.write(imgByte);
            outputStream.flush();
        } catch (Exception e) {
            logger.error("出错了。", e);
        }
    }

    public static void writeJson(HttpServletResponse response, Object obj) {
        writeJson(response, JSON.toJSONBytes(obj));
    }

    public static void writeJsonWithDate(HttpServletResponse response, Object obj) {
        writeStringAsJsonObj(response, JSON.toJSONStringWithDateFormat(obj, "yyyy-MM-dd HH:mm:ss"));
    }

    public static void writeJson(HttpServletResponse response, byte[] bytes) {
        try (ServletOutputStream outputStream = response.getOutputStream()) {
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            response.setContentLength(bytes.length);
            outputStream.write(bytes);
            outputStream.flush();
        } catch (Exception e) {
            logger.error("返回JSON结果出错了", e);
        }
    }

    public static void writeStringAsJsonObj(HttpServletResponse response, String str) {
        try {
            writeJson(response, str.getBytes("utf-8"));
        } catch (UnsupportedEncodingException e) {
            logger.error("返回JSON结果出错了:" + str, e);
        }
    }

    public static void writeHtml(HttpServletResponse response, String html) {
        try (ServletOutputStream outputStream = response.getOutputStream()) {
            response.setContentType("text/html");
            response.setCharacterEncoding("utf-8");
            byte[] bytes = html.getBytes("UTF-8");
            response.setContentLength(bytes.length);
            outputStream.write(bytes);
            outputStream.flush();
        } catch (Exception e) {
            logger.error("出错了。", e);
        }
    }
}
