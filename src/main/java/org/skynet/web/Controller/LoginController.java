package org.skynet.web.Controller;

import org.skynet.web.Common.TokenManager;
import org.skynet.web.Model.LoginUser;
import org.skynet.web.Model.Result;
import org.skynet.web.Model.ResultCode;
import org.skynet.web.Model.User;
import org.skynet.web.Service.UserService;
import org.skynet.web.Utils.CookiesUtils;
import org.skynet.web.Utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/login")
public class LoginController {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);
    // 登录页面
    private static final String LOGIN_PATH = "login";

    @Resource
    private UserService userService;
    @Resource
    private TokenManager tokenManager;

    // 页面
    @RequestMapping(method = RequestMethod.GET)
    public String login(HttpServletRequest request, HttpServletResponse response) {
        String token = CookiesUtils.getCookie(request, "token");
        if (token == null) {
            return goLoginPath();
        } else {
            LoginUser loginUser = tokenManager.validate(token);
            if (loginUser != null) {
                return "UserIndex";
            } else {
                return goLoginPath();
            }
        }
    }

    // 登录提交
    @RequestMapping(method = RequestMethod.POST)
    public void singIn(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> loginMap = new JacksonJsonParser().parseMap(request.getParameter("json"));
        String username = loginMap.get("username").toString();
        String password = loginMap.get("password").toString();

        Result<User> result = userService.login(username, password);
        PrintWriter pw = response.getWriter();
        pw.printf("{\"status\":%d}", result.getCode());
        response.setContentType("text/json");

        if (result.getCode().equals(ResultCode.SUCCESS)) {
            User currentUser = result.getData();
            LoginUser loginUser = new LoginUser(currentUser.getId(), currentUser.getUsername());
            String token = CookiesUtils.getCookie(request, "token");

            // 未登录
            if (StringUtils.isEmpty(token) || tokenManager.validate(token) == null) {
                token = createToken(loginUser);
                addTokenToCookies(request, response, token);
            }
        }
    }

    @RequestMapping("AddUser")
    public void addUser(HttpServletResponse response, HttpServletRequest request) throws Exception {
        JacksonJsonParser jacksonJsonParser = new JacksonJsonParser();
        Map<String, Object> userMap = jacksonJsonParser.parseMap(request.getParameter("userinfo"));

        int status;
        if (userService.addUser(userMap.get("username").toString(), userMap.get("password").toString())) {
            status = 0;
        } else {
            status = -1;
        }

        PrintWriter pw = response.getWriter();
        pw.printf("{\"status\":%d}", status);
        response.setContentType("text/json");
    }

    @RequestMapping(value = "checkUsername", method = RequestMethod.POST)
    public void checkUser(HttpServletResponse response, HttpServletRequest request) throws Exception {
        String username = request.getParameter("username");

        int status;
        if (userService.findUser(username) == null) {
            status = 0;
        } else {
            status = -1;
        }

        PrintWriter pw = response.getWriter();
        pw.printf("{\"status\":%d}", status);
        response.setContentType("text/json");
    }

    /*
    @RequestMapping(value = "CheckCookies", method = RequestMethod.POST)
    public void checkCookies(HttpServletRequest request, HttpServletResponse response) throws Exception {
        int status;
        if (userService.cookiesCheck(request)) {
            status = 0;
        } else {
            status = -1;
        }

        PrintWriter pw = response.getWriter();
        pw.printf("{\"status\":%d}", status);
        response.setContentType("text/json");
    }
    */

    private String goLoginPath() {
        return LOGIN_PATH;
    }

    private String createToken(LoginUser loginUser) {
        String token = UUID.randomUUID().toString().replaceAll("-", "");

        tokenManager.addToken(token, loginUser);
        return token;
    }

    private void addTokenToCookies(HttpServletRequest request, HttpServletResponse response, String token) {
        Cookie tokenCookie = new Cookie("token", token);
        tokenCookie.setPath("/");

        if ("https".equals(request.getScheme())) {
            tokenCookie.setSecure(true);
        }
        tokenCookie.setHttpOnly(true);
        response.addCookie(tokenCookie);
    }

}
