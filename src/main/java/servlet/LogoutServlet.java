package servlet;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/LogoutServlet")
public class LogoutServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {
        HttpSession session =
                request.getSession();
        session.invalidate();
        response.sendRedirect("login.jsp");
    }
}