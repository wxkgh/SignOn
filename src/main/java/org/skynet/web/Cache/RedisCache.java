package org.skynet.web.Cache;


import org.hibernate.annotations.Source;
import org.hibernate.cache.CacheException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
public class RedisCache<T> {
    private static final Logger LOGGER = LoggerFactory.getLogger(RedisCache.class);

    private RedisTemplate<String, T> redisTemplate;

    @Autowired
    public void setRedisTemplate(RedisTemplate<String, T> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public long TTL(String key) {
        return redisTemplate.getExpire(key);
    }

    /**
     * key-value
     */
    public T get(String key) {
        if (key == null) {
            return null;
        } else {
            return redisTemplate.opsForValue().get(key);
        }
    }

    public T set(String key, T value) {
        redisTemplate.opsForValue().set(key, value);
        return value;
    }

    public T set(String key, T value, long timeout) {
        redisTemplate.opsForValue().set(key, value, timeout, TimeUnit.MINUTES);
        return value;
    }

    public void delete(String key) {
        redisTemplate.delete(key);
    }

    /**
     * Hash
     */
    public void hashSet(String key, String field, T value) {
        HashOperations<String, String, T>hashOperations = redisTemplate.opsForHash();
        hashOperations.putIfAbsent(key, field, value);
    }

    public void hashDelete(String key, String field) {
        HashOperations<String, String, T>hashOperations = redisTemplate.opsForHash();
        hashOperations.delete(key, field);
    }

    public T hashGet(String key, String filed) {
        HashOperations<String, String, T>hashOperations = redisTemplate.opsForHash();
        return hashOperations.get(key, filed);
    }

    public List<T> hashGetValues(String key) {
        HashOperations<String, String, T>hashOperations = redisTemplate.opsForHash();
        return hashOperations.values(key);
    }

    public Map<String, T> hashGetEntries(String key) {
        HashOperations<String, String, T>hashOperations = redisTemplate.opsForHash();
        return hashOperations.entries(key);
    }
}
