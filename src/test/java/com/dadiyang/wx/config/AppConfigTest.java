package com.dadiyang.wx.config;

import com.dadiyang.wx.BaseSpringTestCase;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class AppConfigTest extends BaseSpringTestCase {
    @Autowired
    private AppConfig appConfig;

    @Test
    public void getValue() {
        System.out.println(appConfig);
    }
}