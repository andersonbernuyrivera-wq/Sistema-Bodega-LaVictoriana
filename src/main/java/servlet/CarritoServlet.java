package servlet;

import modelo.ItemCarrito;
import modelo.Producto;
import dao.ProductoDAO;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/CarritoServlet")
public class CarritoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int id = Integer.parseInt(request.getParameter("id"));
        int cantidad = Integer.parseInt(request.getParameter("cantidad"));

        ProductoDAO dao = new ProductoDAO();
        Producto p = dao.buscarPorId(id);

        HttpSession session = request.getSession();
        List<ItemCarrito> carrito = (List<ItemCarrito>) session.getAttribute("carrito");

        if (carrito == null) {
            carrito = new ArrayList<>();
        }
        carrito.add(new ItemCarrito(
                p.getId(),
                p.getNombre(),
                p.getPrecio(),
                cantidad
        ));
        session.setAttribute("carrito", carrito);
        response.sendRedirect("venta.jsp");
    }
}