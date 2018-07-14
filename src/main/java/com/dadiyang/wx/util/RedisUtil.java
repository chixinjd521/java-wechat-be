package com.dadiyang.wx.util;

import org.springframework.data.redis.core.RedisTemplate;

import java.util.Arrays;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 对 redisTemplate 的封装，目的是使其 API 与 redis 命令保持基本一致
 *
 * @author dadiyang
 * @date 2018/7/14
 */
public class RedisUtil {

    private RedisTemplate<String, String> redisTemplate;

    public void setRedisTemplate(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }


    public boolean sismember(String key, Object member) {
        return redisTemplate.opsForSet().isMember(key, member);
    }


    public Long sadd(String key, String... values) {
        return redisTemplate.opsForSet().add(key, values);
    }

    public void set(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public Long lpush(String key, String... values) {
        return redisTemplate.opsForList().leftPushAll(key, values);
    }

    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public Long llen(String key) {
        return redisTemplate.opsForList().size(key);
    }

    public long srem(String key, Object... values) {
        return redisTemplate.opsForSet().remove(key, values);
    }

    public void del(String... keys) {
        if (keys.length == 1) {
            redisTemplate.delete(keys[0]);
        } else if (keys.length > 1) {
            redisTemplate.delete(Arrays.asList(keys));
        }
    }

    public Set<String> smembers(String key) {
        return redisTemplate.opsForSet().members(key);
    }

    public Object lpop(String key) {
        return redisTemplate.opsForList().leftPop(key);
    }

    public void setex(String key, String value, int timeout) {
        redisTemplate.opsForValue().set(key, value);
        redisTemplate.expire(key, timeout, TimeUnit.SECONDS);
    }
}
