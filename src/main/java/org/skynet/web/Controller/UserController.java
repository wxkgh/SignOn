package org.skynet.web.Controller;

import org.skynet.web.Cache.RedisCache;
import org.skynet.web.Helper.MD5Helper;
import org.skynet.web.Mapper.UserMapper;
import org.skynet.web.Model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@Controller
public class UserController {
//    private static Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserMapper userMapper;

    @Autowired
    RedisCache redisCache;

    @RequestMapping("/")
    public String index() {
        return "Index";
    }

    @RequestMapping("LogIn")
    public String logIn() {
        return "LogIn";
    }

    @RequestMapping(value = "SignIn", method = RequestMethod.POST)
    public void singIn(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String md5pswd = MD5Helper.encodeByMd5(password);

        int status;
        User currentUser = userMapper.findUserByAccount(username, password);
        if (currentUser != null) {
            status = 0;
            // User ID
            String userStr = Long.toString(currentUser.getId());
            Cookie userCookie = new Cookie("userid", userStr);
            response.addCookie(userCookie);

            // Sequence
            int num = (int) (Math.random() * 123);
            String sequenceStr = MD5Helper.encodeByMd5(Integer.toString(num));
            Cookie sequenceCookie = new Cookie("sequence", sequenceStr);
            response.addCookie(sequenceCookie);

            // Token
            String tokenStr = MD5Helper.encodeByMd5(currentUser.getUsername() + System.currentTimeMillis());
            Cookie tokenCookie = new Cookie("token", tokenStr);
            response.addCookie(tokenCookie);

            /* Cache */
            Map<String, String> userMap = new HashMap<>();
            userMap.put("sequence", sequenceStr);
            userMap.put("token", tokenStr);
            redisCache.setHash(userStr, userMap);


//            LOGGER.debug(request.getRemoteAddr());
        } else {
            status = -1;
        }

        PrintWriter pw = response.getWriter();
        pw.printf("{\"status\":%d}", status);
        response.setContentType("text/json");
    }

    @RequestMapping("LogOut")
    public void logOut(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession(false);
        if (session == null) {
            response.sendRedirect("/");
            return;
        }

        // cookies
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            cookie.setValue(null);
            cookie.setMaxAge(0);
            cookie.setPath("SignIn");
            response.addCookie(cookie);
        }

        // session
        session.removeAttribute("user");
        response.sendRedirect("/");
    }

    @RequestMapping("AddUser")
    public void addUser(HttpServletResponse response, HttpServletRequest request) throws Exception {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String md5pswd = MD5Helper.encodeByMd5(password);

        int status;
        if (userMapper.insertUserByAccount(username, md5pswd) == 1) {
            status = 0;
        } else {
            status = -1;
        }

        PrintWriter pw = response.getWriter();
        pw.printf("{\"status\":%d}", status);
        response.setContentType("text/json");
    }

    @RequestMapping("NotLogIn")
    public String notLogIn() {
        return "NotLogIN";
    }

    @RequestMapping("Register")
    public String register() {
        return "Register";
    }

    @RequestMapping("UserIndex")
    public String userIndex() {
        return "UserIndex";
    }

    @RequestMapping("UserProfile")
    public String userProfile() {
        return "UserProfile";
    }

//    @RequestMapping(value = "/{reader}", method = RequestMethod.GET)
//    public String readersBooks(@PathVariable("reader") String reader, Model model) {
//        List<Book> readingList = readingListRepository.findByReader(reader);
//        if (readingList != null) {
//            model.addAttribute("books", readingList);
//        }
//        return "userList";
//    }
//
//    @RequestMapping(value = "/{reader}", method = RequestMethod.POST)
//    public String addToReadingList(@PathVariable("reader") String reader, Book book) {
//        book.setReader(reader);
//        readingListRepository.save(book);
//        return "redirect:/{reader}";
//    }
}
