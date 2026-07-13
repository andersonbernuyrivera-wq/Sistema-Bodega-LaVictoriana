package servlet;
import dao.UsuarioDAO;
import modelo.Usuario;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/LoginServlet")
public class LoginServlet
        extends HttpServlet {
    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        String usuario =
                request.getParameter(
                        "usuario");
        String contrasena =
                request.getParameter(
                        "contrasena");
        UsuarioDAO dao =
                new UsuarioDAO();
        Usuario u =
                dao.validar(usuario,contrasena);
        
        if(u != null){
            HttpSession session =request.getSession();
            session.setAttribute("usuario",u);
            session.setAttribute("rol", u.getRol());
            response.sendRedirect( "index.jsp");
        }else{
            response.sendRedirect(
                    "login.jsp");
        }
        System.out.println("Usuario recibido: " + usuario);
        System.out.println("Contrasena recibida: " + contrasena);
        
    }
}