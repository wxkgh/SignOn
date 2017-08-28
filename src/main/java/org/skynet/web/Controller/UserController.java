package org.skynet.web.Controller;

import com.sun.deploy.net.HttpResponse;
import org.skynet.web.Helper.MD5Helper;
import org.skynet.web.Mapper.UserMapper;
import org.skynet.web.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;

@Controller
public class UserController {

    @Autowired
    UserMapper userMapper;

    @RequestMapping("/")
    public String index() {
        return "Index";
    }

    @RequestMapping("LogIn")
    public String logIn() {
        return "LogIn";
    }

    @RequestMapping("SignIn")
    public void singIn(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String md5pswd = MD5Helper.encodeByMd5(password);

        int status;
        if (userMapper.findUserByAccount(username, md5pswd) != null) {
            status = 0;
            User currentUser = new User(username, password);
            request.getSession().setAttribute("user", currentUser);
            Cookie cookie = new Cookie("username", username);
            response.addCookie(cookie);
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
