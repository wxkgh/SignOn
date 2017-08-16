package org.life.task.Controller;

import static org.springframework.web.bind.annotation.RequestMethod.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class home {
    @RequestMapping(value = "/", method = GET)
    public String home() {
        return "home";
    }
}
