package servlet;

import dao.ProductoDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/RestaurarProductoServlet")
public class RestaurarProductoServlet extends HttpServlet {

    @Override
protected void doPost(HttpServletRequest request,
        HttpServletResponse response)
        throws ServletException, IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            ProductoDAO dao = new ProductoDAO();
            HttpSession session = request.getSession();
            if (dao.restaurar(id)) {
                session.setAttribute(
                        "mensaje",
                        "Producto restaurado correctamente");
            } else {
                session.setAttribute(
                        "mensaje",
                        "No se pudo restaurar el producto");
            }
        } catch (Exception e) {
            HttpSession session = request.getSession();
            session.setAttribute(
                    "mensaje",
                    "Error al restaurar el producto");
            System.out.println("Error al restaurar: " + e);
        }
        response.sendRedirect("eliminados.jsp");
    }
}