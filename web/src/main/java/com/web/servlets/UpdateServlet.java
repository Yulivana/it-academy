package com.web.servlets;

import com.pvt.exceptions.LoginException;
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

@WebServlet(name = "UpdateServlet", urlPatterns = {"/update"})
public class UpdateServlet extends HttpServlet {

    private static UserFacade userDAO = new UserFacade();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        long id = request.getParameter("id") != null ? Long.parseLong(request.getParameter("id")) : 0;
        HttpSession session = request.getSession();
        User user = null;
        try {
            if (id == 0) {
                long idS = (Long) session.getAttribute("id");
                user = userDAO.findUser(idS);
            } else {
                user = userDAO.findUser(id);
            }
        } catch (Exception e) {
            request.getServletContext().log(e.getMessage(), e);
            RequestDispatcher rd=request.getRequestDispatcher("error.jsp");
            rd.forward(request,response);
        }

        request.setAttribute("user", user);

        RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/update.jsp");
        rd.forward(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        long id = Long.parseLong(request.getParameter("id"));
        String name = request.getParameter("userName");
        String password = request.getParameter("password");
        String newPassword = request.getParameter("newPassword");
        String email = request.getParameter("email");
        String role = request.getParameter("role");
        try {
            User userById = userDAO.findUser(id);

            if (userById.getPassword().equals(newPassword)) {
                request.setAttribute("error", "New password equals old password");
                doGet(request, response);
            } else {
                User user = new User();
                user.setId(id);
                user.setUserName(name);
                if (newPassword != null && !newPassword.trim().equals("")) {
                    user.setPassword(newPassword);
                } else {
                    user.setPassword(password);
                }

                user.setEmail(email);
                user.setRole(role);
                try {
                    userDAO.updateUser(user);
                    String conPath = request.getContextPath();
                    response.sendRedirect(conPath + "/welcome");
                } catch (LoginException ex) {
                    request.setAttribute("error", ex.getMessage());
                    doGet(request, response);
                }
            }
        } catch (Exception e) {
            request.getServletContext().log(e.getMessage(), e);
            RequestDispatcher rd=request.getRequestDispatcher("error.jsp");
            rd.forward(request,response);
        }

    }
}
