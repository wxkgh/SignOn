package taskLife.test;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.life.task.Config.RootConfig;
import org.life.task.ConfigLoader;
import org.life.task.DAO.UserDao;
import org.life.task.Model.User;
import org.omg.Messaging.SYNC_WITH_TRANSPORT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = RootConfig.class)

public class dataBaseTest {
    @Autowired
    UserDao userDao;


    @Test
    public void findUserTest() {
        User user =  userDao.findUser("xiaoming", "123456");
        if (user == null) {
            System.out.println("not found");
        } else {
            System.out.println("found");
        }
    }

}
