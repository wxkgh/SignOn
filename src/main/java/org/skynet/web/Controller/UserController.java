package org.skynet.web.Controller;

import org.apache.commons.codec.digest.Md5Crypt;
import org.skynet.web.Cache.RedisCache;
import org.skynet.web.Dao.Mybatis.UserMapper;
import org.skynet.web.Model.User;
import org.skynet.web.Service.UserService;
import org.skynet.web.Utils.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@Controller
public class UserController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "SignIn", method = RequestMethod.POST)
    public void singIn(HttpServletRequest request, HttpServletResponse response) throws Exception {
        JacksonJsonParser jacksonJsonParser = new JacksonJsonParser();
        Map<String, Object> userMap = jacksonJsonParser.parseMap(request.getParameter("userinfo"));
//        String salt = BCrypt.gensalt(12);
//        String pswd = BCrypt.hashpw(password, salt);
//
//        int status;
//        User currentUser = userMapper.findUserByAccount(username, pswd);
//        if (currentUser != null) {
//            status = 0;
//            // User ID
//            String userStr = Long.toString(currentUser.getId());
//            Cookie userCookie = new Cookie("userid", userStr);
//            response.addCookie(userCookie);
//
//            // Sequence
//            int num = (int) (Math.random() * 123);
//            String sequenceStr = Md5Crypt.md5Crypt(Integer.toString(num).getBytes());
//            Cookie sequenceCookie = new Cookie("sequence", sequenceStr);
//            response.addCookie(sequenceCookie);
//
//            // Token
//            String tokenStr = Md5Crypt.md5Crypt((currentUser.getUsername() + System.currentTimeMillis()).getBytes());
//            Cookie tokenCookie = new Cookie("token", tokenStr);
//            response.addCookie(tokenCookie);
//
//            /* Cache */
//            Map<String, String> userMap = new HashMap<>();
//            userMap.put("sequence", sequenceStr);
//            userMap.put("token", tokenStr);
//            redisCache.setHash(userStr, userMap);
//
//        } else {
//            status = -1;
//        }
        int status;
        if (userService.logIn(userMap.get("username").toString(), userMap.get("password").toString())) {
            status = 0;
        } else {
            status = -1;
        }

        PrintWriter pw = response.getWriter();
        pw.printf("{\"status\":%d}", status);
        response.setContentType("text/json");
    }

    @RequestMapping("LogOut")
    public String logOut(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // cookies
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            cookie.setValue(null);
            cookie.setMaxAge(0);
            cookie.setPath("SignIn");
            response.addCookie(cookie);
        }
//        request.getRequestDispatcher("NotLogIn").forward(request, response);

        return "NotLogIn";
    }

    @RequestMapping("AddUser")
    public void addUser(HttpServletResponse response, HttpServletRequest request) throws Exception {
        JacksonJsonParser jacksonJsonParser = new JacksonJsonParser();
        Map<String, Object> userMap = jacksonJsonParser.parseMap(request.getParameter("userinfo"));

        int status;
        if (userService.addUser(userMap.get("username").toString(), userMap.get("password").toString())) {
            status = 0;
        } else {
            status = -1;
        }

        PrintWriter pw = response.getWriter();
        pw.printf("{\"status\":%d}", status);
        response.setContentType("text/json");
    }

    @RequestMapping(value = "checkUsername", method = RequestMethod.POST)
    public void checkUser(HttpServletResponse response, HttpServletRequest request) throws Exception {
        String username = request.getParameter("username");
        LOGGER.debug("checking username : " + username);

        int status;
        if (userService.findUser(username) == null) {
            status = 0;
        } else {
            status = -1;
        }

        LOGGER.debug("check result : " + status);
        PrintWriter pw = response.getWriter();
        pw.printf("{\"status\":%d}", status);
        response.setContentType("text/json");
    }
}
