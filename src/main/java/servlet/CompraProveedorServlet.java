package servlet;

import conexion.Conexion;
import java.io.IOException;
import java.sql.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/CompraProveedorServlet")
public class CompraProveedorServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        String proveedor =
                request.getParameter("proveedor");

        String[] productos =
                request.getParameterValues("productoId");

        String[] cantidades =
                request.getParameterValues("cantidad");

        String[] costos =
                request.getParameterValues("costo");

        Connection con = null;

        try {
            Conexion cn = new Conexion();
            con = cn.conectar();
            double totalCompra = 0;

            // Calcular total
            for (int i = 0; i < productos.length; i++) {
                int cantidad =
                        Integer.parseInt(cantidades[i]);
                double costo =
                        Double.parseDouble(costos[i]);
                if (cantidad > 0) {
                    totalCompra +=
                            cantidad * costo;
                }
            }

            // Registrar compra
            String sqlCompra =
                    "INSERT INTO compras(proveedor,total) "
                    + "VALUES(?,?)";
            PreparedStatement psCompra =
                    con.prepareStatement(
                            sqlCompra,
                            Statement.RETURN_GENERATED_KEYS);

            psCompra.setString(1, proveedor);
            psCompra.setDouble(2, totalCompra);

            psCompra.executeUpdate();

            ResultSet rs =
                    psCompra.getGeneratedKeys();
            int compraId = 0;
            if (rs.next()) {
                compraId = rs.getInt(1);
            }

            // Registrar detalle y aumentar stock
            for (int i = 0; i < productos.length; i++) {
                int productoId =
                        Integer.parseInt(productos[i]);
                int cantidad =
                        Integer.parseInt(cantidades[i]);
                double costo =
                        Double.parseDouble(costos[i]);
                if (cantidad > 0) {
                    double subtotal =
                            cantidad * costo;
                    String sqlDetalle =
                            "INSERT INTO detalle_compra "
                            + "(compra_id, producto_id, cantidad, "
                            + "costo_unitario, subtotal) "
                            + "VALUES(?,?,?,?,?)";
                    PreparedStatement psDetalle =
                            con.prepareStatement(sqlDetalle);

                    psDetalle.setInt(1, compraId);
                    psDetalle.setInt(2, productoId);
                    psDetalle.setInt(3, cantidad);
                    psDetalle.setDouble(4, costo);
                    psDetalle.setDouble(5, subtotal);
                    psDetalle.executeUpdate();

                    // ACTUALIZAR STOCK Y COSTO DE COMPRA
                    String sqlStock =
                    "UPDATE productos "
                    + "SET stock = stock + ?, "
                    + "costo_compra = ? "
                    + "WHERE id = ?";
                    PreparedStatement psStock =
                            con.prepareStatement(sqlStock);

                    psStock.setInt(1, cantidad);
                    psStock.setDouble(2, costo);
                    psStock.setInt(3, productoId);
                    psStock.executeUpdate();
                }
            }

            HttpSession session =
                    request.getSession();
            session.setAttribute(
                    "mensaje",
                    "Compra registrada correctamente");
        } catch (Exception e) {
            System.out.println(
                    "Error compra proveedor: " + e);
        }
        response.sendRedirect("compra.jsp");
    }
}