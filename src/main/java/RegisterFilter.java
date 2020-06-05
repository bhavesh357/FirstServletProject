import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

@WebFilter("/RegisterServlet")
public class RegisterFilter implements Filter {
    private String namePattern = "^[A-Z][a-z]{2,}$";
    private String passwordPattern = "^(?=.*[A-Z])(?=.*[0-9])(?=.*[A-Za-z0-9@#!$%^&*()_-]{8,})[A-Za-z0-9]+?[@#!$%^&*()_-][A-Za-z0-9]{1,}?$";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String passwordRepeat = request.getParameter("passwordRepeat");
        RequestDispatcher rd = request.getRequestDispatcher("/register.jsp");
        PrintWriter writer = response.getWriter();
        if(!(username.isEmpty() || password.isEmpty() || passwordRepeat.isEmpty())) {
            if (password.equals(passwordRepeat)) {
                if (username.matches(namePattern)) {
                    if (password.matches(passwordPattern)) {
                        try {
                            if(UserDAO.getUser(username)==null){
                                writer.println("<font color=green>Successfully Registered</font>");
                                }else{
                                writer.println("<font color=red>User Already Exist</font>");
                            }
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }
                    } else {
                        writer.println("<font color=red>Password is not valid</font>");
                    }
                } else {
                    writer.println("<font color=red>Username is not valid</font>");
                }
            } else {
                writer.println("<font color=red>password dont match</font>");
            }
        }else {
            writer.println("<font color=red>Please fill all fields</font>");
        }
        rd.include(request,response);
    }

    @Override
    public void destroy() {

    }
}
