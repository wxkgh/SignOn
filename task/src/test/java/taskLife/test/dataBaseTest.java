package taskLife.test;

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
        User user = userService.getUser("xiaomng", "123456");
        if (user == null ) {
            System.out.println("not found");
        } else {
            System.out.println("found");
            System.out.println("userid : " + user.getId());
        }
    }

}
