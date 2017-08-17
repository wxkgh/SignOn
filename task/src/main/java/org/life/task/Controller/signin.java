package org.life.task.Controller;

import org.life.task.DAO.userDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class signin {
//    private static final Logger LOGGER = LoggerFactory.getLogger(signin.class);

    @Autowired
    userDAO userDataBase;
    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping("/signin")
    public void signin(HttpServletRequest request, HttpServletResponse response) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");


        userDataBase.finduser(username, password);
    }
}
