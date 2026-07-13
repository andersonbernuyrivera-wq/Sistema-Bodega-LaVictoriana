package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/CancelarCompraServlet")
public class CancelarCompraServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();

        // ELIMINAR CARRITO
        session.removeAttribute("carrito");

        // MENSAJE
        session.setAttribute("mensaje",
                "Compra cancelada");

        response.sendRedirect("venta.jsp");
    }
}