import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
    private String namePattern="^[A-Z][a-z]{2,}$";
    private String passwordPattern="^(?=.*[A-Z])(?=.*[0-9])(?=.*[A-Za-z0-9@#!$%^&*()_-]{8,})[A-Za-z0-9]+?[@#!$%^&*()_-][A-Za-z0-9]{1,}?$";
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String user = request.getParameter("user");
        String password = request.getParameter("password");
        String passwordRepeat = request.getParameter("passwordRepeat");
        RequestDispatcher rd = getServletContext().getRequestDispatcher("/register.jsp");
        PrintWriter writer = response.getWriter();

        if(password.equals(passwordRepeat)){
            if(user.matches(namePattern)){
                if (password.matches(passwordPattern)){
                    writer.println("<font color=green>Successfully Registered</font>");
                }else {
                    writer.println("<font color=red>Password is not valid</font>");
                }
            }else{
                writer.println("<font color=red>Username is not valid</font>");
            }
        }else {
            writer.println("<font color=red>password dont match</font>");
        }
        rd.include(request,response);
    }
}
