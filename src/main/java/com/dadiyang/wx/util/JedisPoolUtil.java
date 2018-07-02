package com.dadiyang.wx.util;

import org.apache.commons.lang.StringUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisPoolUtil {
    private static JedisPool instance;

    public static final int WX = 1;

    public static JedisPool getInstance() {
        if (instance == null) {
            synchronized (JedisPoolUtil.class) {
                String host = Conf.getValue("redis.host");
                int port = Conf.getIntValue("redis.port");
                String pswd = Conf.getValue("redis.password");
                int db = Conf.getIntValue("redis.db");
                instance = createPool(pswd, host, port, db);
            }
        }
        return instance;
    }

    private static JedisPool createPool(String pswd, String host, int port, int db) {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(1000);
        poolConfig.setMaxIdle(16);
        poolConfig.setMaxWaitMillis(1000);
        poolConfig.setTestOnBorrow(true);
        if (StringUtils.isBlank(pswd)) {
            return new JedisPool(poolConfig, host, port, 1000, null, db);
        } else {
            return new JedisPool(poolConfig, host, port, 1000, pswd, db);
        }
    }

    public static void release(Jedis jedis) {
        if (jedis != null) {
            jedis.close();
        }
    }
}
