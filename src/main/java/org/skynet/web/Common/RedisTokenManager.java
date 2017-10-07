package org.skynet.web.Common;

import org.skynet.web.Cache.RedisCache;
import org.skynet.web.Model.LoginUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.Map.Entry;

@Component
public class RedisTokenManager extends TokenManager {
    private static Logger LOGGER = LoggerFactory.getLogger(RedisTokenManager.class);

    private RedisCache<DummyUser> redisCache;

    @Autowired
    public void setRedisCache(RedisCache<DummyUser> redisCache) {
        this.redisCache = redisCache;
    }

    @Override
    public void verifyExpired() {
        Date now = new Date();
        for (Entry<String, DummyUser> entry : redisCache.hashGetEntries("tokenManage").entrySet()) {
            String token = entry.getKey();
            DummyUser dummyUser = entry.getValue();
            if (dummyUser == null) {
                return;
            }
            // 当前时间大于过期时间
            if (now.compareTo(dummyUser.expired) > 0) {
                // 已过期，清除对应token
                if (now.compareTo(dummyUser.expired) > 0) {
                    redisCache.hashDelete("tokenManage", token);
                    LOGGER.debug("token : " + token + "已失效");
                }
            }
        }
    }

    @Override
    public void addToken(String token, LoginUser loginUser) {
        DummyUser dummyUser = new DummyUser();
        extendExpiredTime(dummyUser);
        dummyUser.loginUser = loginUser;
        redisCache.hashSet("tokenManage", token, dummyUser);
    }

    @Override
    public LoginUser validate(String token) {
        DummyUser dummyUser = redisCache.hashGet("tokenManage", token);
        if (dummyUser == null) {
            return null;
        }
        extendExpiredTime(dummyUser);
        return dummyUser.loginUser;
    }

    @Override
    public void remove(String token) {
        redisCache.delete(token);
    }

    private class DummyUser implements Serializable {
        private static final long serialVersionUID = -1L;
        private LoginUser loginUser;
        private Date expired; // 过期时间
    }

    private void extendExpiredTime(DummyUser dummyUser) {
        dummyUser.expired = new Date(new Date().getTime() + tokenTimeOut*1000);
    }
}
