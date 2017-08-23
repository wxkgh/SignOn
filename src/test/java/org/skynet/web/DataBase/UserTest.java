package org.skynet.web.DataBase;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skynet.web.Mapper.UserMapper;
import org.skynet.web.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext
public class UserTest {
    @Autowired
    private UserMapper userMapper;

    @Test
    public void find() throws Exception {
        User user = userMapper.findUser("Jack", "13512345678");
        Assert.assertNotNull(user);
    }
}
