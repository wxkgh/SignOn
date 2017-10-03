package org.skynet.web.Controller;

import org.skynet.web.Common.TokenManager;
import org.skynet.web.Utils.CookiesUtils;
import org.skynet.web.Utils.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/logout")
public class LogoutController {
    @Resource
    private TokenManager tokenManager;

    @RequestMapping(method = RequestMethod.GET)
    public String logout(HttpServletRequest request) {
        String token = CookiesUtils.getCookie(request, "token");
        if (StringUtils.isNotEmpty(token)) {
            tokenManager.remove(token);
        }
        return "NotLogin";
    }
}
