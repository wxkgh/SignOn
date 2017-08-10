package org.life.task.Controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("home")
public class hello {
    private static final Logger LOGGER = LoggerFactory.getLogger(hello.class);
    @RequestMapping("/index")
    public void index() {
        LOGGER.debug("index page");
    }

}
