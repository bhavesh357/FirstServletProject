import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(
        description = "login Servlet testing",
        urlPatterns= {"/LoginServlet"},
        initParams={
            @WebInitParam(name = "user", value = "Bhavesh"),
            @WebInitParam(name = "password", value = "leo")
    }
)
public class LoginServlet  extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("user");
        String pwd = request.getParameter("password");

        String user = getServletConfig().getInitParameter("user");
        String password = getServletConfig().getInitParameter("password");
        if (user.equals(name) && password.equals(pwd)){
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
