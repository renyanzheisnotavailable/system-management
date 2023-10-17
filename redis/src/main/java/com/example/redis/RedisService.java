package com.example.redis;

import cn.hutool.json.JSONUtil;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.redis.core.BoundSetOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static com.example.common.constant.RedisConstant.LOGIN_USER_TTL;

@Component
public class RedisService {
    @Resource
    private StringRedisTemplate stringredisTemplate;
    @Resource
    private RedisTemplate redisTemplate;

    /**
     * 缓存对象
     *
     * @param key
     * @param value
     */
    public <T> void setCacheObject(String key, T value) {
        stringredisTemplate.opsForValue().set(key, JSONUtil.toJsonStr(value));
    }

    /**
     * 缓存带ttl的对象(过期时间为分钟)
     *
     * @param key
     * @param value
     * @param time 过期时间
     * @param <T>
     */
    public <T> void setCacheObject(String key, T value, long time) {
        stringredisTemplate.opsForValue().set(key, JSONUtil.toJsonStr(value), time, TimeUnit.MINUTES);
    }

    /**
     * 缓存user
     *
     * @param key
     * @param value
     * @param <T>
     */
    public <T> void setCacheUser(String key, T value) {
        stringredisTemplate.opsForValue().set(key, JSONUtil.toJsonStr(value), LOGIN_USER_TTL , TimeUnit.DAYS);
    }

    /**
     * 设置有效时间
     *
     * @param key
     * @param time
     * @return
     */
    public  boolean expire(String key, long time) {
        return redisTemplate.expire(key, time, TimeUnit.MINUTES);
    }

    /**
     * 更新用户登录态
     *
     * @param loginuser
     * @return
     */
    public  boolean expire(String loginuser) {
        return redisTemplate.expire(loginuser, LOGIN_USER_TTL, TimeUnit.DAYS);
    }

    /**
     * 是否存在key
     *
     * @param key
     * @return
     */
    public boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 获得缓存对象
     *
     * @param key
     * @param <T>
     * @return
     */
    public <T> T getCacheObject(String key) {
        return (T) stringredisTemplate.opsForValue().get(key);
    }

    /**
     * 删除单个对象
     *
     * @param key
     */
    public boolean deleteObject(String key) {
        return stringredisTemplate.delete(key);
    }

    /**
     * 缓存List数据
     *
     * @param key 缓存的键值
     * @param dataList 待缓存的List数据
     * @return 缓存的对象
     */
    public <T> long setCacheList(String key, List<T> dataList)  {
        Long count = redisTemplate.opsForList().rightPushAll(key, dataList);
        return count == null ? 0 : count;
    }

    /**
     * 获得缓存list
     *
     * @param key
     * @param <T>
     * @return
     */
    public <T> List<T> getCacheList(String key) {
        return redisTemplate.opsForList().range(key, 0, -1);
    }

    /**
     * 缓存set
     *
     * @param key
     * @param dataSet
     * @param <T>
     * @return
     */
    public <T> BoundSetOperations<String, T> setCacheSet(String key, Set<T> dataSet) {
        Iterator<T> iterator = dataSet.iterator();
        BoundSetOperations<String, T> setOperations = redisTemplate.boundSetOps(key);
        while (iterator.hasNext()) {
            setOperations.add(iterator.next());
        }
        return setOperations;
    }



    /**
     * 缓存Map
     *
     * @param key
     * @param dataMap
     */
    public <T> void setCacheMap( String key,  Map<String, T> dataMap) {
        redisTemplate.opsForHash().putAll(key, dataMap);
        redisTemplate.expire(key, LOGIN_USER_TTL, TimeUnit.MINUTES);
    }

    /**
     * 缓存Map
     *
     * @param key
     * @param dataMap
     */
    public <T> void setCacheMap( String key,  Map<String, T> dataMap, Long time) {
        redisTemplate.opsForHash().putAll(key, dataMap);
        redisTemplate.expire(key, time, TimeUnit.MINUTES);
    }


}
