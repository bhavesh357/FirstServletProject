package com.bl.servlets;

import com.bl.model.UserDAO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/com.bl.servlets.RegisterServlet")
public class RegisterServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        RequestDispatcher rd = getServletContext().getRequestDispatcher("/register.jsp");
        try {
            UserDAO.insertUser(username, password);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        rd.include(request,response);
    }

}

