package servlet;
import dao.UsuarioDAO;
import modelo.Usuario;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/UsuarioServlet")
public class UsuarioServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {
        String nombre =
                request.getParameter("nombre");
        String usuario =
                request.getParameter("usuario");
        String contrasena =
                request.getParameter("contrasena");
        String rol =
                request.getParameter("rol");
        
        if (!contrasena.matches("^(?=.*\\d).{8,}$")) {
            HttpSession session =
                    request.getSession();
            session.setAttribute(
                    "mensajeError",
                    "La contraseña debe tener mínimo 8 caracteres y al menos un número");
            response.sendRedirect("registro.jsp");
            return;
        }
      
        Usuario u = new Usuario();
        u.setNombre(nombre);
        u.setUsuario(usuario);
        u.setContrasena(contrasena);
        u.setRol(rol);

        UsuarioDAO dao = new UsuarioDAO();
        dao.registrar(u);
        response.sendRedirect("login.jsp");
    }
}