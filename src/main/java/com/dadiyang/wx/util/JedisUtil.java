package com.dadiyang.wx.util;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPubSub;

import java.util.*;

public class JedisUtil {
    private static final int RETRY_TIMES = 3;
    private static Logger logger = Logger.getLogger(JedisUtil.class);
    private JedisPool dataSource;
    private static Map<Integer, JedisUtil> instances = new HashMap<>();

    static {
        instances.put(JedisPoolUtil.WX, new JedisUtil(JedisPoolUtil.getInstance()));
    }

    public static JedisUtil getInstance() {
        return instances.get(JedisPoolUtil.WX);
    }

    public JedisUtil(JedisPool dataSource) {
        this.dataSource = dataSource;
    }

    public long hincrBy(String key, String field, long value) {
        Jedis jedis = null;
        try {
            jedis = dataSource.getResource();
            return jedis.hincrBy(key, field, value);
        } catch (Exception e) {
            String errMsg = String.format("hincrBy发生异常：key=%s, field=%s, value=%s ", key, field, value);
            logger.error(errMsg, e);
            return -1;
        } finally {
            JedisPoolUtil.release(jedis);
        }
    }

    public long incrBy(String key, long value) {
        Jedis jedis = null;
        try {
            jedis = dataSource.getResource();
            return jedis.incrBy(key, value);
        } catch (Exception e) {
            String errMsg = String.format("hincrBy发生异常：key=%s, value=%s ", key, value);
            logger.error(errMsg, e);
            return -1;
        } finally {
            JedisPoolUtil.release(jedis);
        }
    }

    public Long expire(String key, int seconds) {
        try (Jedis jedis = dataSource.getResource()) {
            return jedis.expire(key, seconds);
        } catch (Exception e) {
            logger.error("expire: key=" + key + " seconds=" + seconds, e);
        }
        return -1L;
    }

    public long incrBy(String key, long value, int seconds) {
        long rs = incrBy(key, value);
        expire(key, seconds);
        return rs;
    }

    public long hset(String key, String field, String value) {
        Jedis jedis = null;
        try {
            jedis = dataSource.getResource();
            return jedis.hset(key, field, value);
        } catch (Exception e) {
            String errMsg = String.format("hset发生异常：key=%s, field=%s, value=%s ", key, field, value);
            logger.error(errMsg, e);
            return -1;
        } finally {
            JedisPoolUtil.release(jedis);
        }
    }

    public long del(String key) {
        Jedis jedis = null;
        try {
            jedis = dataSource.getResource();
            return jedis.del(key);
        } catch (Exception e) {
            String errMsg = String.format("del发生异常：key=%s", key);
            logger.error(errMsg, e);
            return -1;
        } finally {
            JedisPoolUtil.release(jedis);
        }
    }

    public long hdel(String key, String... fields) {
        Jedis jedis = null;
        try {
            jedis = dataSource.getResource();
            return jedis.hdel(key, fields);
        } catch (Exception e) {
            String errMsg = String.format("hdel发生异常：key=%s, fields=%s", key, Arrays.asList(fields));
            logger.error(errMsg, e);
            return -1;
        } finally {
            JedisPoolUtil.release(jedis);
        }
    }


    public long hset(String key, String field, String value, int expireSeconds) {
        if (RegexUtil.isEmpty(key) || RegexUtil.isEmpty(field) || RegexUtil.isEmpty(value)) {
            throw new NullPointerException();
        }
        Jedis jedis = null;
        try {
            jedis = dataSource.getResource();
            long rs = jedis.hset(key, field, value);
            jedis.expire(key, expireSeconds);
            return rs;
        } catch (Exception e) {
            String errMsg = String.format("hset发生异常：key=%s, field=%s, value=%s ", key, field, value);
            logger.error(errMsg, e);
            return -1;
        } finally {
            JedisPoolUtil.release(jedis);
        }
    }

    public Long lpush(String key, String... value) {
        try (Jedis jedis = dataSource.getResource()) {
            return jedis.lpush(key, value);
        } catch (Exception e) {
            logger.error("lpush: key=" + key + " value=" + Arrays.asList(value), e);
        }
        return -1L;
    }

    public String lpop(String key) {
        try (Jedis jedis = dataSource.getResource()) {
            return jedis.lpop(key);
        } catch (Exception e) {
            logger.error("lpop: key=" + key, e);
        }
        return "";
    }

    public Long llen(String key) {
        try (Jedis jedis = dataSource.getResource()) {
            return jedis.llen(key);
        } catch (Exception e) {
            logger.error("llen: key=" + key, e);
        }
        return -1L;
    }

    public String hget(String key, String field) {
        if (RegexUtil.isEmpty(key) || RegexUtil.isEmpty(field)) {
            throw new NullPointerException();
        }
        Jedis jedis = null;
        try {
            jedis = dataSource.getResource();
            return jedis.hget(key, field);
        } catch (Exception e) {
            String errMsg = String.format("hset发生异常：key=%s, field=%s ", key, field);
            logger.error(errMsg, e);
            return "";
        } finally {
            JedisPoolUtil.release(jedis);
        }
    }

    public String set(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = dataSource.getResource();
            return jedis.set(key, value);
        } catch (Exception e) {
            String errMsg = String.format("set发生异常：key=%s, value=%s ", key, value);
            logger.error(errMsg, e);
            return "";
        } finally {
            JedisPoolUtil.release(jedis);
        }
    }

    public String hmset(String key, Map<String, String> hash) {
        Jedis jedis = null;
        try {
            jedis = dataSource.getResource();
            return jedis.hmset(key, hash);
        } catch (Exception e) {
            String errMsg = String.format("hmset发生异常：key=%s, hash=%s ", key, hash);
            logger.error(errMsg, e);
            return "";
        } finally {
            JedisPoolUtil.release(jedis);
        }
    }

    public String setex(String key, String value, int expireSeconds) {
        try (Jedis jedis = dataSource.getResource()) {
            return jedis.setex(key, expireSeconds, value);
        } catch (Exception e) {
            logger.error("setx发生异常：key=" + key + ", value=" + value + ", expireSeconds=" + expireSeconds, e);
            return "";
        }
    }

    public String get(String key) {
        if (RegexUtil.isEmpty(key)) {
            throw new NullPointerException();
        }
        try (Jedis jedis = dataSource.getResource()) {
            return jedis.get(key);
        } catch (Exception e) {
            logger.error("get发生异常：key=" + key, e);
            return "";
        }
    }

    public int getIntValue(String key) {
        return Integer.parseInt(get(key));
    }

    public boolean hexists(String key, String field) {
        if (StringUtils.isEmpty(key) || StringUtils.isEmpty(field)) {
            throw new NullPointerException();
        }
        Jedis jedis = null;
        try {
            jedis = dataSource.getResource();
            boolean bc = jedis.hexists(key, field);
            return bc;
        } catch (Exception e) {
            String errMsg = String.format("hexists发生异常：key=%s, field=%s", key, field);
            logger.error(errMsg, e);
            return false;
        } finally {
            JedisPoolUtil.release(jedis);
        }
    }

    public void publish(String channel, String message) {
        if (StringUtils.isEmpty(channel) || StringUtils.isEmpty(message)) {
            throw new NullPointerException();
        }
        Jedis jedis = null;
        try {
            jedis = dataSource.getResource();
            jedis.publish(channel, message);
        } catch (Exception e) {
            String errMsg = String.format("publish发生异常：channel=%s, message=%s", channel, message);
            logger.error(errMsg, e);
        } finally {
            JedisPoolUtil.release(jedis);
        }
    }

    public void sadd(String key, String... members) {
        if (key == null || members == null) {
            return;
        }
        int c = 0;
        while (c < RETRY_TIMES) {
            try (Jedis jedis = dataSource.getResource()) {
                jedis.sadd(key, members);
                break;
            } catch (Exception e) {
                c++;
                logger.error("sadd：key=" + key + ", members=" + Arrays.asList(members), e);
            }
        }
    }

    public Set<String> smembers(String key) {
        if (key == null) {
            throw new NullPointerException("键名不能为空");
        }
        try (Jedis jedis = dataSource.getResource()) {
            return jedis.smembers(key);
        } catch (Exception e) {
            logger.error("smembers：key=" + key, e);
        }
        return Collections.emptySet();
    }

    public void sadd(String key, int expire, String... members) {
        if (key == null || members == null) {
            return;
        }
        int c = 0;
        while (c < RETRY_TIMES) {
            try (Jedis jedis = dataSource.getResource()) {
                jedis.sadd(key, members);
                jedis.expire(key, expire);
                break;
            } catch (Exception e) {
                c++;
                logger.error("sadd：key=" + key + ", members=" + Arrays.asList(members), e);
            }
        }
    }

    public boolean sismember(String key, String member) {
        if (RegexUtil.isEmpty(key) || member == null) {
            throw new NullPointerException();
        }
        try (Jedis jedis = dataSource.getResource()) {
            return jedis.sismember(key, member);
        } catch (Exception e) {
            logger.error("sismember: key=" + key + ", member=" + member, e);
        }
        return false;
    }

    public long scard(String key) {
        if (StringUtils.isBlank(key)) {
            throw new NullPointerException();
        }
        try (Jedis jedis = dataSource.getResource()) {
            return jedis.scard(key);
        } catch (Exception e) {
            logger.error("scard: key=" + key, e);
        }
        return -1;
    }

    public String spop(String key) {
        try (Jedis jedis = dataSource.getResource()) {
            return jedis.spop(key);
        } catch (Exception e) {
            logger.error("spop: key=" + key, e);
        }
        return "";
    }

    public Long srem(String key, String... members) {
        try (Jedis jedis = dataSource.getResource()) {
            return jedis.srem(key, members);
        } catch (Exception e) {
            logger.error("srem: key=" + key + " member=" + Arrays.toString(members), e);
        }
        return -1L;
    }

    public void subscribe(JedisPubSub pubSub, String subscribeChannel) {
        try (Jedis jedis = dataSource.getResource()) {
            jedis.subscribe(pubSub, subscribeChannel);
        } catch (Exception e) {
            logger.error("subscribe", e);
        }
    }

}
