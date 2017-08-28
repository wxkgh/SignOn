package org.skynet.web.Filter;

import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Order(1)
@WebFilter(filterName = "testFilter1", urlPatterns = "/*", asyncSupported = true,
    initParams = {
        @WebInitParam(name = "included", value = "UserIndex;UserProfile")
})
public class UserFilter implements Filter {
    private FilterConfig config;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.config = filterConfig;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
        throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;

        String enableFileter = config.getInitParameter("included");
        String[] includedList = enableFileter.split(";");
        if (!isContains(req.getRequestURI(), includedList)) {
            filterChain.doFilter(request, response);
            return;
        }

        HttpSession session = req.getSession();

        if (session.getAttribute("user") != null) {
            // 如果现在存在了session，则请求向下继续传递
            filterChain.doFilter(request, response);
        } else {
            // 跳转到提示登陆页面
            request.getRequestDispatcher("NotLogIn").forward(request, response);
        }
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
