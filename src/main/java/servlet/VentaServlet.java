package servlet;

import conexion.Conexion;
import modelo.ItemCarrito;
import java.io.IOException;
import java.sql.*;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/VentaServlet")
public class VentaServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        List<ItemCarrito> carrito =
                (List<ItemCarrito>) session.getAttribute("carrito");
        String metodoPago =
        request.getParameter("metodoPago");
        if (carrito == null || carrito.isEmpty()) {
            response.sendRedirect("venta.jsp");
            return;
        }

        Connection con = null;

        try {
            Conexion cn = new Conexion();
            con = cn.conectar();

            double total = 0;
            for (ItemCarrito item : carrito) {
                total += item.getSubtotal();
            }
            // INSERTAR VENTA
            String sqlVenta =
        "INSERT INTO ventas(total, cantidad_productos, estado, metodo_pago) "
        + "VALUES(?,?,?,?)";
            PreparedStatement psVenta =
                    con.prepareStatement(sqlVenta,
                            Statement.RETURN_GENERATED_KEYS);
            
            psVenta.setDouble(1, total);
            psVenta.setInt(2, carrito.size());
            psVenta.setString(3, "COMPLETADA");
            psVenta.setString(4, metodoPago);
            
            psVenta.executeUpdate();
            ResultSet rs = psVenta.getGeneratedKeys();

            int ventaId = 0;

            if (rs.next()) {
                ventaId = rs.getInt(1);
            }

            // INSERTAR DETALLE
            for (ItemCarrito item : carrito) {
                String sqlDetalle =
                        "INSERT INTO detalle_venta "
                        + "(venta_id, producto_id, cantidad, "
                        + "precio_unitario, subtotal) "
                        + "VALUES(?,?,?,?,?)";
                PreparedStatement psDetalle =
                        con.prepareStatement(sqlDetalle);

                psDetalle.setInt(1, ventaId);
                psDetalle.setInt(2, item.getId());
                psDetalle.setInt(3, item.getCantidad());
                psDetalle.setDouble(4, item.getPrecio());
                psDetalle.setDouble(5, item.getSubtotal());

                psDetalle.executeUpdate();

                // DESCONTAR STOCK
                String sqlStock =
                        "UPDATE productos "
                        + "SET stock = stock - ? "
                        + "WHERE id = ?";

                PreparedStatement psStock =
                        con.prepareStatement(sqlStock);
                psStock.setInt(1, item.getCantidad());
                psStock.setInt(2, item.getId());
                psStock.executeUpdate();
            }
            // LIMPIAR CARRITO
            session.removeAttribute("carrito");

            // MENSAJE
            session.setAttribute("mensaje",
                    "Compra realizada correctamente");
        } catch (Exception e) {
            System.out.println("Error venta: " + e);
        }
        response.sendRedirect("venta.jsp");
    }
}