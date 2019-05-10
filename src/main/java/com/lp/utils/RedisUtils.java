package com.lp.utils;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @Description: redis工具类
 * @Author: 师岩岩
 * @Date: 2019/5/9 12:43
 */
@Component
public class RedisUtils {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 读取缓存
     *
     * @param key
     * @return
     */
    public String get(final String key) {
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * 写入缓存
     */
    public boolean set(final String key, String value) {
        boolean result = false;
        try {
            redisTemplate.opsForValue().set(key, value);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 更新缓存
     */
    public boolean getAndSet(final String key, String value) {
        boolean result = false;
        try {
            redisTemplate.opsForValue().getAndSet(key, value);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    /**
     * 删除缓存
     */
    public boolean delete(final String key) {
        boolean result = false;
        try {
            redisTemplate.delete(key);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public void setKey(String key, Object value) {
        if(value == null){
            return;
        }
        if(value instanceof String){
            String obj = (String) value;
            stringRedisTemplate.opsForValue().set(key, obj);
        }else if(value instanceof List){
            List obj = (List) value;
            stringRedisTemplate.opsForList().leftPushAll(key,obj);
        }else if(value instanceof Map){
            Map obj = (Map) value;
            stringRedisTemplate.opsForHash().putAll(key,obj);
        }
    }

    public Object getKey(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }
}
