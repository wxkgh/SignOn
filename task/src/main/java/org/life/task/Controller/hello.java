package org.life.task.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class hello {

    @RequestMapping("/")
    public String index() {
        return "index";
    }

    @RequestMapping("/Login")
    public String login() {
        return "Login";
    }

}
