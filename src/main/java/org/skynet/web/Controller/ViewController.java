package org.skynet.web.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ViewController {
    @RequestMapping("/")
    public String index() {
        return "index";
    }

//    @RequestMapping("LogIn")
//    public String logIn() {
//        return "LogIn";
//    }

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
        return "userIndex";
    }

    @RequestMapping("UserProfile")
    public String userProfile() {
        return "UserProfile";
    }
}
