package taskLife.test;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
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

public class UserDataBaseTest {
    @Autowired
    UserService userService;

    @Autowired
    Dao userDao;

    @Before
    public void init() {
        userDao.executeSQLFile("sql/user_init.sql");
    }

    @Test
    public void findUserTest() throws IllegalAccessException, InstantiationException {
        Assert.assertNotNull(userService.findUser("Jack", "13512345678"));
        Assert.assertNull(userService.findUser("Mr.Boom", "77711124"));
    }

//    @Test
//    public void addUserTest() {
//        User xiaohong = new User("xiaohong", "654321");
//        Assert.assertFalse(userService.addUser(xiaohong));
//    }
//
//    @Test
//    public void deleteUserTest() throws IllegalAccessException, InstantiationException {
//        User targetUser = userService.findUser("xiaohong", "654321");
//        Assert.assertTrue(userService.deleteUser(targetUser));
//    }
//
//    @Test
//    public void updateUserTest() throws IllegalAccessException, InstantiationException {
//        User targetUser = userService.findUser("xiaohong", "654321");
//        targetUser.setUsername("xiaoming");
//        Assert.assertFalse(userService.updateUser(targetUser));
//    }

}
