package org.skynet.web.Redis;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skynet.web.Cache.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@DirtiesContext
public class redisTest {

    @Autowired
    private RedisCache redisCache;

    @Test
    @SuppressWarnings("unchecked")
    public void test() throws Exception {
        Map<String, String> testMap = new HashMap<>();
        testMap.put("user1seq", "111seq");
        testMap.put("user1tok", "111tok");
        redisCache.setHash("user1", testMap);

        Map<String, String> resultMap = (Map<String, String>) redisCache.getHash("user1");
        Assert.assertEquals("111seq", resultMap.get("user1seq"));
        Assert.assertEquals("111tok", resultMap.get("user1tok"));
    }

}
