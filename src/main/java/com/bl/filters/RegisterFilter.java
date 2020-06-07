package com.bl.filters;

import com.bl.model.UserDAO;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

@WebFilter("/RegisterServlet")
public class RegisterFilter implements Filter {
    private String namePattern = "^[A-Z][a-z]{2,}$";
    private String passwordPattern = "^(?=.*[A-Z])(?=.*[0-9])(?=.*[A-Za-z0-9@#!$%^&*()_-]{8,})[A-Za-z0-9]+?[@#!$%^&*()_-][A-Za-z0-9]{1,}?$";
    private String emailPattern="[\\w\\d]{1,}[.\\-#!]?[\\w\\d]{1,}@[\\w\\d]{1,}.[a-z]{2,3}.?([a-z]{2})?";
    private String username;
    private String email;
    private String password;
    private String passwordRepeat;
    private RequestDispatcher rd;
    private PrintWriter writer;
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        username = request.getParameter("username");
        email = request.getParameter("email");
        password = request.getParameter("password");
        passwordRepeat = request.getParameter("passwordRepeat");
        rd = request.getRequestDispatcher("/register.jsp");
        writer = response.getWriter();
        try {
            if(isFieldNotEmpty() && isPasswordSame() && doesNameMatch() && doesEmailMatch() && doesPasswordMatch() && ifUserNotExist()){
                filterChain.doFilter(request,response);
            }else{
                rd.include(request,response);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private boolean ifUserNotExist() throws SQLException {
        if(UserDAO.getUser(email)==null){
            writer.println("<font color=green>Successfully Registered</font>");
            return true;
        }else{
            writer.println("<font color=red>User Already Exist</font>");
            return false;
        }
    }

    private boolean doesPasswordMatch() {
        if (!password.matches(passwordPattern)) {
            writer.println("<font color=red>Password is not valid</font>");
            return false;
        }
        return true;
    }

    private boolean doesEmailMatch() {
        if (!email.matches(emailPattern)){
            writer.println("<font color=red>Email is not valid</font>");
        }
        return true;
    }

    private boolean doesNameMatch() {
        if (!username.matches(namePattern)) {
            writer.println("<font color=red>Username is not valid</font>");
            return false;
        }
        return true;
    }

    private boolean isPasswordSame() {
        if (!password.equals(passwordRepeat)) {
            writer.println("<font color=red>password dont match</font>");
            return false;
        }
        return true;
    }

    private boolean isFieldNotEmpty() {
        if(username.isEmpty() || email.isEmpty() || password.isEmpty() || passwordRepeat.isEmpty()) {
            writer.println("<font color=red>Please fill all fields</font>");
            return false;
        }
        return true;
    }

    @Override
    public void destroy() {

    }
}
