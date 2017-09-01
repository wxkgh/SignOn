package org.skynet.web.Filter;

import org.skynet.web.Cache.RedisCache;
import org.skynet.web.Utils.CookiesUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

@Order(1)
@WebFilter(filterName = "testFilter1", urlPatterns = "/*", asyncSupported = true,
    initParams = {
        @WebInitParam(name = "included", value = "UserIndex;UserProfile")
})
public class UserFilter implements Filter {
    private FilterConfig config;
    private static final Logger LOGGER = LoggerFactory.getLogger(UserFilter.class);
    @Autowired
    RedisCache redisCache;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.config = filterConfig;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
        throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;

        // 不需要过滤的页面
        String enableFileter = config.getInitParameter("included");
        String[] includedList = enableFileter.split(";");
        if (!isContains(req.getRequestURI(), includedList)) {
            filterChain.doFilter(request, response);
            return;
        }

        // check cookies with redis
        String currentUsername = CookiesUtils.getCookie(req, "username");
        if (currentUsername != null && currentUsername.length() != 0) {
            LOGGER.debug("username is " + currentUsername);
            Map<String, String> tokenMap = redisCache.getHash(currentUsername);
            if (tokenMap != null) {
                if (tokenMap.get("sequence").equals(CookiesUtils.getCookie(req, "sequence")) &&
                    tokenMap.get("token").equals(CookiesUtils.getCookie(req, "token"))) {
                    filterChain.doFilter(request, response);
                    return;
                }
            }
        }
        request.getRequestDispatcher("NotLogIn").forward(request, response);
    }

    @Override
    public void destroy() {
        this.config = null;
    }

    private static boolean isContains(String container, String[] regexs) {
        for (String regex : regexs) {
            if (container.contains(regex)) {
                return true;
            }
        }
        return false;
    }
}
