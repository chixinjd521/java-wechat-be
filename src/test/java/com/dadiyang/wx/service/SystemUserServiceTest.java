package com.dadiyang.wx.service;

import com.alibaba.fastjson.JSON;
import com.dadiyang.wx.BaseSpringTestCase;
import com.dadiyang.wx.entity.SystemUser;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class SystemUserServiceTest extends BaseSpringTestCase {
    @Autowired
    SystemUserService systemUserService;
    private static SystemUser user = new SystemUser();

    @BeforeClass
    public static void setUp() throws Exception {
        user.setUsername("lisi");
        user.setPassword("^lisipwd~%$");
        user.setRealName("李四");
        user.setEmail("lisi@gmail.com");
        user.setPhone("15988889992");
        Date now = new Date();
        user.setCreateTime(now);
        user.setUpdateTime(now);
        user.setIsDisabled(false);
    }

    @Test
    public void runTest() {
        addNewUser();
        getUser();
    }

    public void addNewUser() {
        boolean rs = systemUserService.addNewUser(user);
        System.out.println(rs);
        assertTrue(rs);
    }

    public void getUser() {
        SystemUser existUser = systemUserService.getUser(user.getUsername(), user.getPassword());
        System.out.println(JSON.toJSONString(existUser));
        assertion(existUser);
    }

    private void assertion(SystemUser existUser) {
        assertNotNull(existUser);
        assertEquals(existUser.getUsername(), user.getUsername());
        assertEquals(existUser.getRealName(), user.getRealName());
        assertEquals(existUser.getEmail(), user.getEmail());
        assertEquals(existUser.getPhone(), user.getPhone());
        assertEquals(existUser.getIsDisabled(), user.getIsDisabled());
    }
}