package com.dadiyang.wx.util;

import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

/**
 * 配置项
 *
 * @author dadiyang
 * @date 2017/11/18
 */
public class Conf {
    private static final Logger logger = Logger.getLogger(Conf.class);
    private static Properties properties;
    public static final String IMAGE_FILE_PATH = getRootPath() + "/data/webresources/wx/img/";
    public static final String PS_SIGN_KEY = "ps_sign:";
    private static long version = System.currentTimeMillis();

    static {
        try (InputStream in = Conf.class.getClassLoader().getResourceAsStream("server.properties")) {
            properties = new Properties();
            properties.load(new InputStreamReader(in, "utf-8"));
        } catch (IOException e) {
            logger.error("读取配置文件出错", e);
        }
    }

    public static String getValue(String name) {
        return properties.getProperty(name);
    }

    public static List<String> getValues(String name) {
        if (!properties.containsKey(name)) {
            return Collections.emptyList();
        }
        return Arrays.asList(Conf.getValue(name).split(","));
    }

    public static int getIntValue(String name) {
        return Integer.parseInt(properties.getProperty(name));
    }

    public static String getRootPath() {
        File file = new File(System.getProperty("user.dir"));
        String path = file.getAbsolutePath().replace('\\', '/');
        path = path.substring(0, path.indexOf('/'));
        return path;
    }

    public static long getVersion() {
        return version;
    }

    public static void setVersion(long version) {
        Conf.version = version;
    }
}
