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
import java.io.PrintWriter;


@WebServlet(name = "LoginServlet", urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {
    private static UserFacade userDAO = new UserFacade();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        RequestDispatcher rd=request.getRequestDispatcher("index.jsp");
        rd.forward(request,response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        response.setContentType("text/html");

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        HttpSession session = request.getSession();

        User user = userDAO.isValidLogin(username, password);

        if (user == null) {
            PrintWriter out = response.getWriter();
            out.print("<p style=\"color:red\">Sorry username or password error</p>");
            RequestDispatcher rd=request.getRequestDispatcher("index.jsp");
            rd.include(request,response);
            out.close();
        } else {
            session.setAttribute("id", user.getId());
            session.setAttribute("role", user.getRole());

            RequestDispatcher rd=request.getRequestDispatcher("/welcome");
            rd.forward(request,response);
        }

    }

}
