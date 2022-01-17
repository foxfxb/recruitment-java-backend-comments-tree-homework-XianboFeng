package com.foxfxb.interviewee.redis;

import com.foxfxb.interviewee.utils.JSONUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

@SuppressWarnings("unchecked")
@Component
@Log4j2
public class RedisClusterCache {


    @Autowired
    private RedisTemplate<Object, Object> template;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 设置缓存
     *
     * @param key   缓存key
     * @param value 缓存value
     */
    public void set(String key, String value) {
        template.opsForValue().set(key, value);
        log.debug("RedisUtil:set cache key=" + key + ",value=" + value);
    }

    public void flushAll() {
        template.getConnectionFactory().getClusterConnection().flushAll();
    }


    public void fuzzyDelByKey(String pattern) {
        //template.keys(pattern);
        template.delete(template.keys(pattern));
    }


    /**
     * 设置缓存对象
     *
     * @param key 缓存key
     * @param obj 缓存value
     */
    public <T> void setObject(String key, T obj, int expireTime) {
        template.opsForValue().set(key, JSONUtils.toJson(obj), expireTime);
    }

    public <T> void setObject(String key,T obj){
        template.opsForValue().set(key,obj);
    }

    /**
     * 获取指定key的缓存
     *
     * @param key---JSON.parseObject(value, User.class);
     */
    public <T> T getObject(String key, Class<T> clazz) {
        String keyValue = get(key);
        return JSONUtils.toObject(keyValue, clazz);
    }

    /**
     * 判断当前key值 是否存在
     *
     * @param key
     */
    public boolean hasKey(String key) {
        return template.hasKey(key);
    }


    /**
     * 设置缓存，并且自己指定过期时间
     *
     * @param key
     * @param value
     * @param expireTime 过期时间
     */
    public void setWithExpireTime(String key, String value, int expireTime) {
        stringRedisTemplate.opsForValue().set(key,value, expireTime);
        log.debug("RedisUtil:setWithExpireTime cache key=" + key + ",value=" + value + ",expireTime=" + expireTime);
    }

    public void setWithExpireTime(String key, Object obj, int expireTime) {
        setWithExpireTime(key, JSONUtils.toJson(obj), expireTime);
    }


    /**
     * 获取指定key的缓存
     *
     * @param key
     */
    public String get(String key) {
        Object obj = template.opsForValue().get(key);
        String value = JSONUtils.toJson(obj);
        log.debug("RedisUtil:get cache key=" + key + ",value=" + value);
        return value;
    }

    /**
     * 删除指定key的缓存
     *
     * @param key
     */
    public void delete(String key) {
        template.delete(key);
        log.debug("RedisUtil:delete cache key=" + key);
    }

    /**
     * 添加缓存数据
     *
     * @param key
     * @param obj
     * @param <T>
     * @return
     * @throws Exception
     */
    public <T> void putCache(String key, T obj) {
        if (obj == null) {
            return ;
        }
        setObject(key,obj);
    }


    /**
     * 根据key取缓存数据
     *
     * @param key
     * @param <T>
     * @return
     * @throws Exception
     */
    public <T> T getCache(final String key) {
        String redisKey = key;
        if(hasKey(key)){
            return (T)template.boundValueOps(redisKey).get();
        }
        return null;
    }

    /**
     * 根据key删除缓存数据
     *
     * @return
     * @throws Exception
     */
    public void delCache(final String key) {
        template.delete(key.getBytes());
    }

    /**
     * 根据key删除缓存数据
     *
     * @return
     * @throws Exception
     */
    public void delCache(final List<Long> keys, String prex) {
        for (Long id : keys) {
            String key = prex + id;
            template.delete(key);
        }
    }

    /**
     * 序列化
     *
     * @param object
     * @return
     */
    private static byte[] serializeObj(Object object) {
        ObjectOutputStream oos = null;
        ByteArrayOutputStream baos = null;
        try {
            baos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(baos);
            oos.writeObject(object);
            byte[] bytes = baos.toByteArray();
            return bytes;
        } catch (Exception e) {
            throw new RuntimeException("序列化失败!", e);
        }
    }

    /**
     * 反序列化
     *
     * @param bytes
     * @return
     */
    private static Object deserializeObj(byte[] bytes) {
        if (bytes == null) {
            return null;
        }
        ByteArrayInputStream bais = null;
        try {
            bais = new ByteArrayInputStream(bytes);
            ObjectInputStream ois = new ObjectInputStream(bais);
            return ois.readObject();
        } catch (Exception e) {
            throw new RuntimeException("反序列化失败!", e);
        }
    }


    /**
     * 失效时间
     *
     * @param key key值
     * @return 返回失效时间
     */
    public long ttl(String key) {
        return template.boundValueOps(key).getExpire();
    }
}
