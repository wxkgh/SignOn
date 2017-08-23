package org.skynet.web.Controller;

import org.skynet.web.Model.User;
import org.skynet.web.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.xml.ws.RequestWrapper;
import java.util.List;
import java.util.Map;


@Controller
public class hello {

    @Autowired
    UserService userService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(Map<String, Object> model) throws IllegalAccessException, InstantiationException {
        model.put("Jack", userService.findUser("Jack", "13512345678"));
        return "index";
    }

    @RequestMapping("/register")
    public String register() {
        return "register";
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }
}
