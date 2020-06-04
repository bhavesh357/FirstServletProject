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
    String namePattern="^[A-Z][a-z]{2,}$";
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String user = request.getParameter("user");
        String password = request.getParameter("password");
        String passwordRepeat = request.getParameter("passwordRepeat");

        if(password.equals(passwordRepeat) && user.matches(namePattern)){
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/register.jsp");
            PrintWriter writer = response.getWriter();
            writer.println("<font color=green>Successfully Registered</font>");
            rd.include(request,response);
        }else{
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/register.jsp");
            PrintWriter writer = response.getWriter();
            writer.println("<font color=red>Either username is not valid or passwords dont match</font>");
            rd.include(request,response);
        }
    }
}
