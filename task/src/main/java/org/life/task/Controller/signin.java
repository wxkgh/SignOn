package org.life.task.Controller;

import org.life.task.DAO.Dao;
import org.life.task.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@Controller
public class SignIn {
    @Autowired
    Dao userDB;

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping("/signin")
    public void signin(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
//        int status;
//        String username = request.getParameter("username");
//        String password = request.getParameter("password");
//        User currentUser = userDB.findUser(username, password);
//        if (currentUser == null) {
//            status = -1;
//        } else {
//            status = 0;
//            HttpSession session = request.getSession();
//            session.setAttribute("username", username);
//        }
//
//        PrintWriter pw = response.getWriter();
//        pw.printf("{\"status\":%d}", status);
//        response.setContentType("text/json");
    }

    @RequestMapping("/userIndex")
    public String userIndex() {
        return "userIndex";
    }

}
