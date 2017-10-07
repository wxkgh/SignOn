package org.skynet.web.Redis;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skynet.web.Cache.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@DirtiesContext
public class redisTest {

    @Autowired
    private RedisCache<String> redisCache;

    @Test
    public void test() throws Exception {
        String key = "key";
        String field = "field";
        String value = "value";

        redisCache.hashSet(key, field, value);
        String temp = redisCache.hashGet(key, field);
        Assert.assertTrue(temp.equals("value"));
    }

}
