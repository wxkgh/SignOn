package org.life.task.Controller;

import org.life.task.DAO.UserDao;
import org.life.task.Model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Controller
public class signin {
    private static final Logger LOGGER = LoggerFactory.getLogger(signin.class);
    @Autowired
    UserDao userDB;

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping("/signin")
    public void signin(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        int status;
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        User currentUser = userDB.findUser(username, password);
        if (currentUser == null) {
            status = -1;
        } else {
            status = 0;
        }

        PrintWriter pw = response.getWriter();
        pw.printf("{\"status\":%d}", status);
        response.setContentType("text/json");
    }

}
