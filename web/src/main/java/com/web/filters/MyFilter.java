package com.web.filters;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;


@WebFilter(servletNames = {"UsersServlet", "WelcomeServlet", "DeleteServlet", "UpdateServlet"})
public class MyFilter implements Filter {

    private ServletContext context;

    @Override
    public void init(FilterConfig filterConfig) {
        this.context = filterConfig.getServletContext();
        this.context.log("MyFilter initialized");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        HttpSession session = req.getSession(false);

        if(session == null || session.getAttribute("id") == null){
            this.context.log("Unauthorized access request");
            String conPath = req.getContextPath();
            res.sendRedirect(conPath+"/");
        }else{
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {
        this.context.log("MyFilter destroy");
    }
}
