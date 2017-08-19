package org.life.task.Service;

import org.life.task.DAO.Dao;
import org.life.task.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserService {
    @Autowired
    private Dao userDao;

    public boolean addUser(User newUser) {
        String username = newUser.getUsername();
        String password = newUser.getPassword();
        String sql = "INSERT INTO user(username, password) VALUES ('" + username + "', '"+ password +"')";
        return userDao.insertEntity(sql);
    }

    public boolean deleteUser(User oldUser) {
        long userid = oldUser.getId();
        String sql = "DELETE FROM user WHERE id = " + String.valueOf(userid);
        return userDao.deleteEntity(sql);
    }

    public boolean updateUser(User currentUser) {
        String username = currentUser.getUsername();
        String password = currentUser.getPassword();
        String sql = "UPDATE user SET username = \"" + username + "\" , password = \"" + password + "\"" + "WHERE id = " +currentUser.getId();
        return userDao.updateEntity(sql);
    }

    public User findUser(String username, String password) throws InstantiationException, IllegalAccessException {
        String sql = "SELECT * FROM user WHERE username = \""+ username +"\" && password = \"" + password + "\"";
        return userDao.queryEntity(User.class, sql);
    }

    public User getUser(long id) {
        String sql = "SELECT * FROM user WHERE id = " + id;
        return userDao.queryEntity(User.class, sql);
    }


}
