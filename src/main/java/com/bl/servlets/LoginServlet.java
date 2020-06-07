package com.bl.servlets;

import com.bl.model.User;
import com.bl.model.UserDAO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

@WebServlet(
        description = "login Servlet testing",
        urlPatterns= {"/LoginServlet"}
)
public class LoginServlet  extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String pwd = request.getParameter("password");
        User user = null;
        try {
            user = UserDAO.getUser(email,pwd);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        if (user!=null){
            request.setAttribute("user",user);
            request.getRequestDispatcher("welcome.jsp").forward(request,response);
        }else{
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/login.html");
            PrintWriter writer = response.getWriter();
            writer.println("<font color=red>Either username or password is wrong</font>");
            rd.include(request,response);
        }
    }
}
