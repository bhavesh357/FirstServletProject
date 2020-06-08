package com.bl.filters;

import com.bl.model.UserDAO;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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
    HttpServletRequest request;
    HttpServletResponse response;
    String message;
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        request = (HttpServletRequest) servletRequest;
        response = (HttpServletResponse) servletResponse;
        username = request.getParameter("username");
        email = request.getParameter("email");
        password = request.getParameter("password");
        passwordRepeat = request.getParameter("passwordRepeat");
        rd = request.getRequestDispatcher("/register.jsp");
        try {
            if(isFieldNotEmpty() && isPasswordSame() &&
                    doesNameMatch() && doesEmailMatch() &&
                        doesPasswordMatch() && ifUserNotExist()){
                request.setAttribute("message", message);
                filterChain.doFilter(request,response);
            }else{
                request.setAttribute("message", message);
                rd.include(request,response);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private boolean ifUserNotExist() throws SQLException {
        if(UserDAO.getUser(email)==null){
            message ="<h4 style=\"color:green\">Successfully Registered</h4>";
            return true;
        }else{
            message ="<h4 style=\"color:red\">User Already Exist</h4>";
            return false;
        }
    }

    private boolean doesPasswordMatch() {
        if (!password.matches(passwordPattern)) {
            message ="<h4 style=\"color:red\">Password is not valid</h4>";
            return false;
        }
        return true;
    }

    private boolean doesEmailMatch() {
        if (!email.matches(emailPattern)){
            message ="<h4 style=\"color:red\">Email is not valid</h4>";
            return false;
        }
        return true;
    }

    private boolean doesNameMatch() {
        if (!username.matches(namePattern)) {
            message ="<h4 style=\"color:red\">Username is not valid</h4>";
            return false;
        }
        return true;
    }

    private boolean isPasswordSame() {
        if (!password.equals(passwordRepeat)) {
            message ="<h4 style=\"color:red\">password dont match</h4>";
            return false;
        }
        return true;
    }

    private boolean isFieldNotEmpty() {
        if(username.isEmpty() || email.isEmpty() || password.isEmpty() || passwordRepeat.isEmpty()) {
            message="<h4 style=\"color:red\">Please fill all fields</h4>";
            return false;
        }
        return true;
    }

    @Override
    public void destroy() {

    }
}
