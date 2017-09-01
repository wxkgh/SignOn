package org.skynet.web.Controller;

import org.apache.commons.codec.digest.Md5Crypt;
import org.skynet.web.Cache.RedisCache;
import org.skynet.web.Service.UserService;
import org.skynet.web.Utils.CookiesUtils;
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
        String username = userMap.get("username").toString();
        String password = userMap.get("password").toString();

        int status;
        if (userService.logIn(username, password)) {
            // Username
            Cookie userCookie = new Cookie("username", username);
            response.addCookie(userCookie);

            // Sequence
            int num = (int) (Math.random() * 123);
            String sequenceStr = Md5Crypt.md5Crypt(Integer.toString(num).getBytes());
            Cookie sequenceCookie = new Cookie("sequence", sequenceStr);
            response.addCookie(sequenceCookie);

            // Token
            String tokenStr = Md5Crypt.md5Crypt((username + System.currentTimeMillis()).getBytes());
            Cookie tokenCookie = new Cookie("token", tokenStr);
            response.addCookie(tokenCookie);

            /* Cache */
            Map<String, String> userCacheMap = new HashMap<>();
            userCacheMap.put("sequence", sequenceStr);
            userCacheMap.put("token", tokenStr);
            redisCache.setHash(username, userCacheMap);
            status = 0;
        } else {
            status = -1;
        }

        PrintWriter pw = response.getWriter();
        pw.printf("{\"status\":%d}", status);
        response.setContentType("text/json");
    }

    @RequestMapping("LogOut")
    public String logOut(HttpServletRequest request, HttpServletResponse response) {
        String username = CookiesUtils.getCookie(request, "username");
        if (username != null && username.length() != 0) {
            redisCache.delete(username);
        }
        CookiesUtils.removeCookies(response, "username", null, null);
        CookiesUtils.removeCookies(response, "sequence", null, null);
        CookiesUtils.removeCookies(response, "token", null, null);
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

        int status;
        if (userService.findUser(username) == null) {
            status = 0;
        } else {
            status = -1;
        }

        PrintWriter pw = response.getWriter();
        pw.printf("{\"status\":%d}", status);
        response.setContentType("text/json");
    }

    @RequestMapping(value = "CheckCookies", method = RequestMethod.POST)
    public void checkCookies(HttpServletRequest request, HttpServletResponse response) throws Exception {
        int status;
        if (userService.cookiesCheck(request)) {
            status = 0;
        } else {
            status = -1;
        }

        PrintWriter pw = response.getWriter();
        pw.printf("{\"status\":%d}", status);
        response.setContentType("text/json");
    }
}
