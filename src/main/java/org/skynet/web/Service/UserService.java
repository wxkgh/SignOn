package org.skynet.web.Service;

import org.skynet.web.DAO.Dao;
import org.skynet.web.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class UserService {
    @Autowired
    private Dao userDao;

    public void executeSQLFile(String filePath) {
        userDao.executeSQLFile(filePath);
    }

    public boolean addUser(User newUser) {
        String username = newUser.getUsername();
        String password = newUser.getPassword();
        Map<String, Object> fieldMap = new HashMap<>();
        fieldMap.put("username", username);
        fieldMap.put("password", password);
        return userDao.insertEntity(User.class, fieldMap);
    }

    public boolean deleteUser(User oldUser) {
        return userDao.deleteEntity(User.class, oldUser.getId());
    }

    public boolean updateUser(User currentUser) {
        Map<String, Object> fieldMap = new HashMap<>();
        fieldMap.put("username", currentUser.getUsername());
        fieldMap.put("password", currentUser.getPassword());
        return userDao.updateEntity(User.class, currentUser.getId(), fieldMap);
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
