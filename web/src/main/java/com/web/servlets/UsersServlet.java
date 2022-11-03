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
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "UsersServlet", urlPatterns = {"/users"})
public class UsersServlet extends HttpServlet {

    private static UserFacade userDAO = new UserFacade();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        List<User> otherUserList = new ArrayList<>();
        HttpSession session = request.getSession();
        String role = (String) session.getAttribute("role");
        Long id = (Long) session.getAttribute("id");
        if(role.equals("admin")) {
            List<User> allUsers;
            try {
                allUsers = userDAO.listAll();
                User user = userDAO.findUser(id);
                allUsers.forEach(u -> {
                    if(!u.equals(user)) {
                        otherUserList.add(u);
                    }
                });
            } catch (Exception e) {
                RequestDispatcher rd=request.getRequestDispatcher("error.jsp");
                rd.forward(request,response);
            }

        }
        request.setAttribute("list", otherUserList);
        RequestDispatcher rd=request.getRequestDispatcher("WEB-INF/users.jsp");
        rd.forward(request,response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }
}
