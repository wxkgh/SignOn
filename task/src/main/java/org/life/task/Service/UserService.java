package org.life.task.Service;

import org.life.task.DAO.Dao;
import org.life.task.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserService {
    @Autowired
    private Dao userDao;

    public User getUser(String username, String password) throws InstantiationException, IllegalAccessException {
        String sql = "SELECT * FROM user WHERE username = \""+ username +"\" && password = \"" + password + "\"";
        return userDao.queryEntity(User.class, sql);
    }
}
