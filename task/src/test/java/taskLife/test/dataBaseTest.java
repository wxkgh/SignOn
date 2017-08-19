package taskLife.test;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.life.task.Config.RootConfig;
import org.life.task.DAO.Dao;
import org.life.task.Model.User;
import org.life.task.Service.UserService;
import org.omg.Messaging.SYNC_WITH_TRANSPORT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = RootConfig.class)

public class dataBaseTest {
    @Autowired
    UserService userService;


    @Test
    public void findUserTest() throws IllegalAccessException, InstantiationException {
//        User user = userService.findUser("xiaomng", "123456");
//        if (user == null ) {
//            System.out.println("not found");
//        } else {
//            System.out.println("found");
//            System.out.println("userid : " + user.getId());
//        }
    }

    @Test
    public void addUserTest() {
//        User xiaohong = new User("xiaohong", "654321");
//        if (userService.addUser(xiaohong)) {
//            System.out.println("added");
//        } else {
//            System.out.println("failed");
//        }
    }

    @Test
    public void deleteUserTest() {
        User xiaohong = new User(12002, "xiaohong", "654321");
        Assert.assertTrue(userService.deleteUser(xiaohong));
    }

}
