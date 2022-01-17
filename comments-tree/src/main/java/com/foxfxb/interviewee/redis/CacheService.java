package com.foxfxb.interviewee.redis;

import com.foxfxb.interviewee.utils.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

@Service
public class CacheService {
    Logger logger = LoggerFactory.getLogger(CacheService.class);

    @Autowired
    private RedisClusterCache clusterCache;

    public List<String> buildKeys2String(List<Long> keys) {
        if (CommonUtils.isNull(keys)) {
            return null;
        }

        List<String> result = new ArrayList<>();
        for (Long key : keys) {
            if (key == null) {
                continue;
            }
            result.add(key.toString());
        }

        return result;
    }

    public void clearCache(String key, String cacheName) {
        if (CommonUtils.isNull(key)) {
            logger.warn("parameter key is null or empty");
            return;
        }

        if (CommonUtils.isNull(cacheName)) {
            logger.warn("parameter cacheName is null or empty");
            return;
        }

        Cache cache = clusterCache.getCache(cacheName);
        if (cache == null) {
            logger.debug("Current repository: {} cache is null", cacheName);
            return;
        }

        logger.debug("Begin to invalid repository: {} cache on key: {}", cacheName, key);

        cache.evict(key);
    }

    public void clearCache(List<String> keys, String cacheName) {
        if (CommonUtils.isNull(keys)) {
            logger.warn("parameter keys is null or empty");
            return;
        }

        if (CommonUtils.isNull(cacheName)) {
            logger.warn("parameter cacheName is null or empty");
            return;
        }

        Cache cache = clusterCache.getCache(cacheName);
        if (cache == null) {
            logger.debug("Current repository: {} cache is null", cacheName);
            return;
        }

        logger.debug("Begin to invalid repository: {} cache on keys: {}", cacheName, keys.toString());

        for (String key : keys) {
            if (CommonUtils.isNull(key)) {
                continue;
            }
            cache.evict(key);
        }
    }

    public void clearCache(String cacheName) {
        Assert.hasText(cacheName, "cacheName is null or empty");

        Cache cache = clusterCache.getCache(cacheName);
        if (cache == null) {
            logger.debug("Current repository: {} cache is null", cacheName);
            return;
        }

        cache.clear();
    }

    public void clearAllCache() {
        clusterCache.flushAll();
    }

    public void addCache(String key, String cacheName, Object value) {
        Assert.hasText(key, "key is null or empty");
        Assert.hasText(cacheName, "cacheName is null or empty");

        Cache cache = clusterCache.getCache(cacheName);
        if (cache == null) {
            logger.debug("Current repository: {} cache is null", cacheName);
            return;
        }

        if (value != null) {
            cache.put(key, value);
        }
    }

    public <T> T getCache(Class<T> t, String key, String cacheName) {
        Assert.hasText(key, "key is null or empty");
        Assert.hasText(cacheName, "cacheName is null or empty");

        Cache cache = clusterCache.getCache(cacheName);
        if (cache == null) {
            logger.debug("Current repository: {} cache is null", cacheName);
            return null;
        }
        ValueWrapper valueWrapper = cache.get(key);

        if (valueWrapper == null) {
            logger.debug("Current key: has no any cache on repository: {}", key, cacheName);
            return null;
        }

        return (T) valueWrapper.get();
    }
}
