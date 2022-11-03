package com.web.servlets;

import com.pvt.facade.UserFacade;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet(name = "LogoutServlets", urlPatterns = {"/logout"})
public class LogoutServlets extends HttpServlet {

    private static UserFacade userDAO = new UserFacade();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();

        session.invalidate();
        String conPath = request.getContextPath();
        response.sendRedirect(conPath+"/");
    }
}
