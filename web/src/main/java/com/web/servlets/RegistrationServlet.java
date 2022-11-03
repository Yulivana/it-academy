package com.web.servlets;

import com.pvt.exceptions.LoginException;
import com.pvt.facade.UserFacade;
import com.pvt.model.User;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "RegistrationServlet", urlPatterns = {"/add"})
public class RegistrationServlet extends HttpServlet {

    private static UserFacade userDAO = new UserFacade();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        RequestDispatcher rd=request.getRequestDispatcher("WEB-INF/add.jsp");
        rd.forward(request,response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        response.setContentType("text/html");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        String email = request.getParameter("email");
        HttpSession session = request.getSession();
        if (password.equals(confirmPassword)) {
            try {
                User newUser = new User();
                newUser.setPassword(password);
                newUser.setUserName(username);
                newUser.setEmail(email);
                newUser.setRole("user");
                userDAO.createUser(newUser);
            } catch (LoginException ex) {
                PrintWriter out = response.getWriter();
                out.print("<p style=\"color:red\">"+ ex.getMessage() +"</p>");
                RequestDispatcher rd=request.getRequestDispatcher("WEB-INF/add.jsp");
                rd.include(request,response);
                out.close();
            } catch (Exception e) {
                request.getServletContext().log(e.getMessage(), e);
                RequestDispatcher rd=request.getRequestDispatcher("error.jsp");
                rd.forward(request,response);
            }
        } else {
            PrintWriter out = response.getWriter();
            out.print("<p style=\"color:red\">Password is not confirmed</p>");
            RequestDispatcher rd=request.getRequestDispatcher("WEB-INF/add.jsp");
            rd.include(request,response);
            out.close();
        }

        session.invalidate();
        String conPath = request.getContextPath();
        response.sendRedirect(conPath+"/");

    }
}
