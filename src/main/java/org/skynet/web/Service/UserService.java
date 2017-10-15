package org.skynet.web.Service;

import org.skynet.web.Dao.Mybatis.UserMapper;
import org.skynet.web.Model.Result;
import org.skynet.web.Model.ResultCode;
import org.skynet.web.Model.User;
import org.skynet.web.Utils.BCrypt;
import org.skynet.web.Utils.CookiesUtils;
import org.skynet.web.Cache.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisCache redisCache;
//    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    public Result<User> addUser(String username, String password) {
        Result<User> result = Result.createSuccessResult();
        User currentUser = findUser(username);
        if (currentUser != null) {
            result.setCode(ResultCode.ERROR).setMessage("用户名已存在");
        } else {
            String salt = BCrypt.gensalt(10);
            String pswd = BCrypt.hashpw(password, salt);
            if (userMapper.insertUserByAccount(username, pswd, salt) == 1) {
                currentUser = findUser(username);
                result.setData(currentUser);
            } else {
                result.setCode(ResultCode.ERROR).setMessage("注册失败");
            }
        }
        return result;
    }

    public boolean deleteUser(long userid) {
        return userMapper.deleteUser(userid) == 1;
    }

    public User findUser(String username) {
        return userMapper.findUserByAccount(username);
    }

    @SuppressWarnings("unchecked")
    public Result<User> login(String username, String password) {
        Result<User> result = Result.createSuccessResult();
        User currentUser = findUser(username);
        if (currentUser == null) {
            result.setCode(ResultCode.ERROR).setMessage("用户名不存在");
        }
        if (!currentUser.getPassword().equals(BCrypt.hashpw(password, currentUser.getSalt()))) {
            result.setCode(ResultCode.ERROR).setMessage("密码错误");
        }
        result.setData(currentUser);
        return result;
    }

    /*
    @SuppressWarnings("unchecked")
    public boolean cookiesCheck(HttpServletRequest request) {
        String currentUsername = CookiesUtils.getCookie(request, "username");
        if (currentUsername != null && currentUsername.length() != 0) {
            Map<String, String> tokenMap = redisCache.getHash(currentUsername);
            if (tokenMap != null) {
                if (tokenMap.get("sequence").equals(CookiesUtils.getCookie(request, "sequence")) &&
                        tokenMap.get("token").equals(CookiesUtils.getCookie(request, "token"))) {
                    return true;
                }
            }
        }
        return false;
    }
    */

}
