package com.telfa.andrei.helper;

import com.telfa.andrei.common.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Redis帮助类
 * @since 1.8
 */
@Component
public class RedisHelper {

    private static RedisTemplate<String, Object> staticRedisTemplate;

    private final RedisTemplate<String, Object> redisTemplate;

    @Autowired
    public RedisHelper(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
        if(RedisHelper.staticRedisTemplate == null) {
            RedisHelper.staticRedisTemplate = redisTemplate;
        }
    }

    /**
     * 保存到redis hash
     * @param key redis的key
     * @param hashKey hash key
     * @param object hash value
     */
    public static void saveAsHashToRedis(String key, String hashKey, Object object) {
        staticRedisTemplate.opsForHash().put(key, hashKey, object);
    }

    /**
     * 根据key和hashKey获取值
     * @param key key
     * @param hashKey hash key
     * @return 值
     */
    public static Object getAsHashFromRedis(String key, String hashKey) {
        return staticRedisTemplate.opsForHash().get(key, hashKey);
    }

    /**
     * 保存到redis
     * @param key 键
     * @param value 值
     * @param timeout 超时时间, 默认分钟
     */
    public static void saveToRedis(String key, Object value, long timeout) {
        saveToRedis(key, value, timeout, TimeUnit.MINUTES);
    }

    /**
     * 保存到redis, 设置超时时间
     * @param key 键
     * @param value 值
     */
    public static void saveToRedis(String key, Object value, long timeout, TimeUnit timeUnit) {
        staticRedisTemplate.opsForValue().set(Constants.REDIS_KEY.concat(key), value, timeout, timeUnit);
    }

    /**
     * 根据key获取值
     * @param key key
     * @return 值
     */
    public static Object getFromRedis(String key) {
        return staticRedisTemplate.opsForValue().get(Constants.REDIS_KEY.concat(key));
    }

    public static Object getHashMapFromRedis(String hashKey){
        return staticRedisTemplate.opsForHash().entries(hashKey);
    }

    /**
     * 根据key删除redis值
     * @param key key
     */
    public static void deleteByKey(String key) {
        staticRedisTemplate.delete(key);
    }

    /**
     * 根据key删除redis hash值
     */
    public static  void deleteByKey(String key,String hashKey){
        staticRedisTemplate.opsForHash().delete(key,hashKey);
    }
    /**
     * 根据key列表删除redis值
     * @param keys key列表
     */
    public static void deleteByKeys(List<String> keys) {
        staticRedisTemplate.delete(keys);
    }


}
