package org.skynet.web.Controller;

import org.skynet.web.Model.Result;
import org.skynet.web.Model.User;
import org.skynet.web.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Map;

@Controller
@RequestMapping("/register")
public class RegisterController {
    private static final String REGISTER_PATH = "register";
    @Autowired
    UserService userService;

    @RequestMapping(method = RequestMethod.GET)
    public String register() {
        return REGISTER_PATH;
    }

    @RequestMapping(method = RequestMethod.POST)
    public void addUser(HttpServletResponse response, HttpServletRequest request) throws Exception {
        JacksonJsonParser jacksonJsonParser = new JacksonJsonParser();
        Map<String, Object> userMap = jacksonJsonParser.parseMap(request.getParameter("userinfo"));

        Result<User> result = userService.addUser(userMap.get("username").toString(), userMap.get("password").toString());

        PrintWriter pw = response.getWriter();
        pw.printf("{\"status\":%d}", result.getCode());
        response.setContentType("text/json");
    }
}
