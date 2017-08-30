package org.skynet.web.Service;

import org.skynet.web.Dao.Mybatis.UserMapper;
import org.skynet.web.Model.User;
import org.skynet.web.Utils.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

//    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    public boolean addUser(String username, String password) {
        String salt = BCrypt.gensalt(10);
        String pswd = BCrypt.hashpw(password, salt);
        return userMapper.insertUserByAccount(username, pswd, salt) == 1;
    }

    public boolean deleteUser(long userid) {
        return userMapper.deleteUser(userid) == 1;
    }

    public User findUser(String username) {
        return userMapper.findUserByAccount(username);
    }

    public boolean logIn(String username, String password) {
        User currentUser = findUser(username);
        if (currentUser == null) {
            return false;
        }
        String inputPw = BCrypt.hashpw(password, currentUser.getSalt());
        return inputPw.equals(currentUser.getPassword());
    }

}
