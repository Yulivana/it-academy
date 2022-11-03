package com.web.servlets;

import com.pvt.facade.UserFacade;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "DeleteServlet", urlPatterns = {"/delete"})
public class DeleteServlet extends HttpServlet {

    private static UserFacade userDAO = new UserFacade();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        long id = Long.parseLong(request.getParameter("id"));

        try {
            userDAO.deleteUser(id);
        } catch (Exception e) {
            request.getServletContext().log(e.getMessage(), e);
            RequestDispatcher rd=request.getRequestDispatcher("error.jsp");
            rd.forward(request,response);
        }
        RequestDispatcher rd=request.getRequestDispatcher("/users");
        rd.forward(request,response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }
}
