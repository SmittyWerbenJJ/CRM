package com.zhiyiyo.crm.filter;


import com.zhiyiyo.crm.settings.entity.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        String path = req.getServletPath();
        if ("/login.html".equals(path) || "/index.html".equals(path)) {
            chain.doFilter(request, response);
        } else {
            User user = (User) req.getSession().getAttribute("user");
            if (user != null) {
                chain.doFilter(request, response);
            }

            resp.sendRedirect(req.getContextPath() + "/login.html");
        }
    }
}
