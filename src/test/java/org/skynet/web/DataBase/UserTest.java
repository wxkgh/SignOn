package org.skynet.web.DataBase;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
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
    public void findUserTest() throws Exception {
        User user = userMapper.findUserByAccount("Jack", "13512345678");
        Assert.assertNotNull(user);
    }

    @Test
    public void addUserTest() throws Exception {
        int i = userMapper.insertUserByAccount("xiaoming", "123456");
        Assert.assertEquals(1, i);
        User user = userMapper.findUserByAccount("xiaoming", "123456");
        Assert.assertNotNull(user);
    }

    @Test
    public void updateUserTest() throws Exception {
        User user = userMapper.findUserByAccount("xiaoming", "123456");
        int i = userMapper.updateUser("xiaohong", "123456", user.getId());
        Assert.assertEquals(1, i);
    }

    @Test
    public void deleteUserTest() throws Exception {
        User user = userMapper.findUserByAccount("xiaohong", "123456");
        int i = userMapper.deleteUser(user.getId());
        Assert.assertEquals(1, i);
    }
}
