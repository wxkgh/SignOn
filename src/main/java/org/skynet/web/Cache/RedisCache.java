package org.skynet.web.Cache;


import org.hibernate.cache.CacheException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class RedisCache<T> {
    private static final Logger LOGGER = LoggerFactory.getLogger(RedisCache.class);

    @Autowired
    private RedisTemplate redisTemplate;

    public void delete(String key) {
        try {
            redisTemplate.delete(key);
        } catch (CacheException e) {
            LOGGER.error("Redis Cache delete key failed." + e);
            throw new CacheException(e);
        }
    }

    @SuppressWarnings("unchecked")
    public Map<T, T> getHash(String key) {
        HashOperations<String, String, String> hashOperations = redisTemplate.opsForHash();
        return (Map<T, T>)hashOperations.entries(key);
    }

    @SuppressWarnings("unchecked")
    public void setHash(String key, Map<T, T> valueMap) {
        HashOperations<String, String, String> hashOperations = redisTemplate.opsForHash();
        hashOperations.putAll(key, (Map<String, String>)valueMap);
    }

}
