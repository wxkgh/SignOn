package org.skynet.web.DataBase;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.omg.Messaging.SYNC_WITH_TRANSPORT;
import org.skynet.web.Model.User;
import org.skynet.web.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)

public class UserDataBaseTest {
    @Autowired
    UserService userService;

    @Before
    public void init() {
        userService.executeSQLFile("sql/user_init.sql");
    }

    @Test
    public void findUserTest() throws IllegalAccessException, InstantiationException {
        Assert.assertNotNull(userService.findUser("Jack", "13512345678"));
        Assert.assertNull(userService.findUser("Mr.Boom", "77711124"));
    }

    @Test
    public void addUserTest() {
        User xiaohong = new User("xiaohong", "654321");
        Assert.assertTrue(userService.addUser(xiaohong));
    }

    @Test
    public void deleteUserTest() throws IllegalAccessException, InstantiationException {
        User targetUser = userService.findUser("xiaoming", "123456");
        Assert.assertTrue(userService.deleteUser(targetUser));
    }

    @Test
    public void updateUserTest() throws IllegalAccessException, InstantiationException {
        User targetUser = userService.findUser("xiaoming", "123456");
        targetUser.setUsername("xiaohong");
        Assert.assertTrue(userService.updateUser(targetUser));
        Assert.assertNotNull(userService.getUser(targetUser.getId()));
    }

}
