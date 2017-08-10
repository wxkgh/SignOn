package org.life.task.Controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class hello {
    private static final Logger LOGGER = LoggerFactory.getLogger(hello.class);
    @RequestMapping("/")
    public String index() {
        LOGGER.debug("index page");
        return "index";
    }

}
