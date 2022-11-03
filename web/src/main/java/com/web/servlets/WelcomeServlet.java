package com.web.servlets;

import com.pvt.facade.UserFacade;
import com.pvt.model.User;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet(name = "WelcomeServlet", urlPatterns = {"/welcome"})
public class WelcomeServlet extends HttpServlet {

    private static UserFacade userDAO = new UserFacade();

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        long id = (Long) session.getAttribute("id");
        User user = null;
        try {
            user = userDAO.findUser(id);
        } catch (Exception e) {
            request.getServletContext().log(e.getMessage(), e);
            RequestDispatcher rd=request.getRequestDispatcher("error.jsp");
            rd.forward(request,response);
        }
        if (user != null) {
            request.setAttribute("name", user.getUserName());
        }
        RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/welcome.jsp");
        rd.forward(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
