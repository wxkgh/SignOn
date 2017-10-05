package org.skynet.web.DataBase;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skynet.web.Model.User;
import org.skynet.web.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@SpringBootTest
@DirtiesContext
public class UserTest {
    @Autowired
    private UserService userService;

    @Test
    public void findUserTest() throws Exception {
        User user = userService.findUser("Jack");
        Assert.assertNotNull(user);
    }

    @Test
    public void addUserTest() throws Exception {
        Assert.assertTrue(userService.addUser("xiaoming", "123456abc"));
    }

    @Test
    public void logInTest() throws Exception {
        //Assert.assertTrue(userService.logIn("xiaohong", "abcdef123"));
    }

    @Test
    public void deleteUserTest() throws Exception {
        User user = userService.findUser("xiaohong");
        Assert.assertTrue(userService.deleteUser(user.getId()));
    }
}
