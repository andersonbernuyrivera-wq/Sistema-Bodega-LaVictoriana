package servlet;
import dao.ProductoDAO;
import modelo.Producto;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/ProductoServlet")
public class ProductoServlet extends HttpServlet {
    ProductoDAO dao = new ProductoDAO();
    @Override
    protected void doPost(HttpServletRequest request,
        HttpServletResponse response)
        throws ServletException, IOException {
        System.out.println("ENTRÓ AL SERVLET");
        String nombre = request.getParameter("nombre");
        double precio = Double.parseDouble(request.getParameter("precio"));
        double costoCompra = Double.parseDouble(request.getParameter("costoCompra"));
        int stock = Integer.parseInt(request.getParameter("stock"));
        String presentacion = request.getParameter("presentacion");
        String accion = request.getParameter("accion");
        System.out.println("DATOS RECIBIDOS: " + nombre);
        Producto p = new Producto();
        p.setNombre(nombre);
        p.setPrecio(precio);
        p.setCostoCompra(costoCompra);
        p.setStock(stock);
        p.setPresentacion(presentacion);
        if ("actualizar".equals(accion)) {
            int id = Integer.parseInt(request.getParameter("id"));
            p.setId(id);
            dao.actualizar(p);
        } else {
            dao.agregar(p);
        }
        response.sendRedirect("index.jsp");
    }
    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        String accion = request.getParameter("accion");
        if ("eliminar".equals(accion)) {
            int id = Integer.parseInt(request.getParameter("id"));
            dao.eliminar(id);
            response.sendRedirect("index.jsp");
        }
        if ("editar".equals(accion)) {
            int id = Integer.parseInt(request.getParameter("id"));
            Producto p = dao.obtenerPorId(id);
            request.setAttribute("producto", p);
            request.getRequestDispatcher("editar.jsp")
            .forward(request, response);
        }
    }
}