package dao;

import conexion.Conexion;
import modelo.Venta;
import modelo.DetalleVenta;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VentaDAO {
    Conexion cn = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;

    public List<Venta> listarVentasPorFecha(String periodo, String fecha, String orden) {
        List<Venta> lista = new ArrayList<>();
        String sql = "";
        if ("dia".equals(periodo)) {
            sql = "SELECT * FROM ventas WHERE DATE(fecha)=?";
        } else if ("semana".equals(periodo)) {
            sql = "SELECT * FROM ventas "
                + "WHERE YEARWEEK(fecha,1)=YEARWEEK(?,1)";
        } else if ("mes".equals(periodo)) {
            sql = "SELECT * FROM ventas "
                + "WHERE YEAR(fecha)=YEAR(?) "
                + "AND MONTH(fecha)=MONTH(?)";
        }
        // Ordenamiento
        if ("reciente".equals(orden)) {
            sql += " ORDER BY fecha DESC";
        } else if ("mayor".equals(orden)) {
            sql += " ORDER BY total DESC";
        } else if ("menor".equals(orden)) {
            sql += " ORDER BY total ASC";
        }
        try {
            con = cn.conectar();
            ps = con.prepareStatement(sql);
            if ("mes".equals(periodo)) {
                ps.setString(1, fecha);
                ps.setString(2, fecha);
            } else {
                ps.setString(1, fecha);
            }
            rs = ps.executeQuery();
            while (rs.next()) {
                Venta v = new Venta();
                v.setId(rs.getInt("id"));
                v.setFecha(rs.getTimestamp("fecha"));
                v.setTotal(rs.getDouble("total"));
                v.setCantidadProductos(rs.getInt("cantidad_productos"));
                v.setEstado(rs.getString("estado"));
                v.setMetodoPago(rs.getString("metodo_pago"));
                lista.add(v);
            }
        } catch (Exception e) {
            System.out.println("Error listar ventas: " + e);
        }
        return lista;
    }
    
    public List<DetalleVenta> obtenerDetalle(int ventaId) {
        List<DetalleVenta> lista = new ArrayList<>();
        String sql =
            "SELECT p.nombre, d.cantidad, " +
            "d.precio_unitario, d.subtotal " +
            "FROM detalle_venta d " +
            "INNER JOIN productos p " +
            "ON d.producto_id = p.id " +
            "WHERE d.venta_id = ?";

        try {
            con = cn.conectar();
            ps = con.prepareStatement(sql);
            ps.setInt(1, ventaId);
            rs = ps.executeQuery();
            while(rs.next()) {
                DetalleVenta d = new DetalleVenta();

                d.setNombreProducto(
                        rs.getString("nombre"));

                d.setCantidad(
                        rs.getInt("cantidad"));

                d.setPrecioUnitario(
                        rs.getDouble("precio_unitario"));
                
                d.setSubtotal(
                        rs.getDouble("subtotal"));
                lista.add(d);
            }
        } catch(Exception e) {
            System.out.println("Error detalle: " + e);
        }
        return lista;
    }
    public double obtenerMayorVenta(String periodo, String fecha) {
        double mayor = 0;
        String sql = "";
        if ("dia".equals(periodo)) {
            sql = "SELECT MAX(total) AS mayor "
                + "FROM ventas "
                + "WHERE DATE(fecha)=?";
        } else if ("semana".equals(periodo)) {
            sql = "SELECT MAX(total) AS mayor "
                + "FROM ventas "
                + "WHERE YEARWEEK(fecha,1)=YEARWEEK(?,1)";
        } else if ("mes".equals(periodo)) {
            sql = "SELECT MAX(total) AS mayor "
                + "FROM ventas "
                + "WHERE YEAR(fecha)=YEAR(?) "
                + "AND MONTH(fecha)=MONTH(?)";
        }
        try {
            con = cn.conectar();
            ps = con.prepareStatement(sql);
            if ("mes".equals(periodo)) {
                ps.setString(1, fecha);
                ps.setString(2, fecha);
            } else {
                ps.setString(1, fecha);
            }
            rs = ps.executeQuery();
            if (rs.next()) {
                mayor = rs.getDouble("mayor");
            }
        } catch (Exception e) {
            System.out.println("Error mayor venta: " + e);
        }
        return mayor;
    }
    public double obtenerMenorVenta(String periodo, String fecha) {
        double menor = 0;
        String sql = "";
        if ("dia".equals(periodo)) {
            sql = "SELECT MIN(total) AS menor FROM ventas WHERE DATE(fecha)=?";
        } else if ("semana".equals(periodo)) {
            sql = "SELECT MIN(total) AS menor FROM ventas WHERE YEARWEEK(fecha,1)=YEARWEEK(?,1)";
        } else if ("mes".equals(periodo)) {
            sql = "SELECT MIN(total) AS menor FROM ventas WHERE YEAR(fecha)=YEAR(?) AND MONTH(fecha)=MONTH(?)";
        }
        try {
            con = cn.conectar();
            ps = con.prepareStatement(sql);
            if ("mes".equals(periodo)) {
                ps.setString(1, fecha);
                ps.setString(2, fecha);
            } else {
                ps.setString(1, fecha);
            }
            rs = ps.executeQuery();
            if (rs.next()) {
                menor = rs.getDouble("menor");
            }
        } catch (Exception e) {
            System.out.println("Error menor venta: " + e);
        }
        return menor;
    }
    public double obtenerPromedioVentas(String periodo, String fecha) {
        double promedio = 0;
        String sql = "";
        if ("dia".equals(periodo)) {
            sql = "SELECT AVG(total) AS promedio FROM ventas WHERE DATE(fecha)=?";
        } else if ("semana".equals(periodo)) {
            sql = "SELECT AVG(total) AS promedio FROM ventas WHERE YEARWEEK(fecha,1)=YEARWEEK(?,1)";
        } else if ("mes".equals(periodo)) {
            sql = "SELECT AVG(total) AS promedio FROM ventas WHERE YEAR(fecha)=YEAR(?) AND MONTH(fecha)=MONTH(?)";
        }
        try {
            con = cn.conectar();
            ps = con.prepareStatement(sql);
            if ("mes".equals(periodo)) {
                ps.setString(1, fecha);
                ps.setString(2, fecha);
            } else {
                ps.setString(1, fecha);
            }
            rs = ps.executeQuery();
            if (rs.next()) {
                promedio = rs.getDouble("promedio");
            }
        } catch (Exception e) {
            System.out.println("Error promedio: " + e);
        }
        return promedio;
    }
    public int obtenerCantidadVentas(String periodo, String fecha) {
        int cantidad = 0;
        String sql = "";
        if ("dia".equals(periodo)) {
            sql = "SELECT COUNT(*) AS cantidad FROM ventas WHERE DATE(fecha)=?";
        } else if ("semana".equals(periodo)) {
            sql = "SELECT COUNT(*) AS cantidad FROM ventas WHERE YEARWEEK(fecha,1)=YEARWEEK(?,1)";
        } else if ("mes".equals(periodo)) {
            sql = "SELECT COUNT(*) AS cantidad FROM ventas WHERE YEAR(fecha)=YEAR(?) AND MONTH(fecha)=MONTH(?)";
        }
        try {
            con = cn.conectar();
            ps = con.prepareStatement(sql);
            if ("mes".equals(periodo)) {
                ps.setString(1, fecha);
                ps.setString(2, fecha);
            } else {
                ps.setString(1, fecha);
            }
            rs = ps.executeQuery();
            if (rs.next()) {
                cantidad = rs.getInt("cantidad");
            }
        } catch (Exception e) {
            System.out.println("Error cantidad ventas: " + e);
        }
        return cantidad;
    }
    public double obtenerVentasTotales(String periodo, String fecha) {
        double total = 0;
        String filtro = "";
        if ("dia".equals(periodo)) {
            filtro = "DATE(fecha)=?";
        } else if ("semana".equals(periodo)) {
            filtro = "YEARWEEK(fecha,1)=YEARWEEK(?,1)";
        } else if ("mes".equals(periodo)) {
            filtro = "YEAR(fecha)=YEAR(?) AND MONTH(fecha)=MONTH(?)";
        }
        String sql =
                "SELECT SUM(total) AS totalVentas " +
                "FROM ventas " +
                "WHERE " + filtro;
        try {
            con = cn.conectar();
            ps = con.prepareStatement(sql);
            if ("mes".equals(periodo)) {
                ps.setString(1, fecha);
                ps.setString(2, fecha);
            } else {
                ps.setString(1, fecha);
            }
            rs = ps.executeQuery();
            if (rs.next()) {
                total = rs.getDouble("totalVentas");
            }
        } catch (Exception e) {
            System.out.println("Error ventas totales: " + e);
        }
        return total;
    }
    
    public double obtenerCostoTotal(String periodo, String fecha) {
        double costo = 0;
        String filtro = "";

        if ("dia".equals(periodo)) {
            filtro = "DATE(v.fecha)=?";
        } else if ("semana".equals(periodo)) {
            filtro = "YEARWEEK(v.fecha,1)=YEARWEEK(?,1)";
        } else {
            filtro = "YEAR(v.fecha)=YEAR(?) AND MONTH(v.fecha)=MONTH(?)";
        }
        String sql =
            "SELECT SUM(d.cantidad * p.costo_compra) costo " +
            "FROM detalle_venta d " +
            "INNER JOIN ventas v ON d.venta_id=v.id " +
            "INNER JOIN productos p ON d.producto_id=p.id " +
            "WHERE " + filtro;

        try {
            con = cn.conectar();
            ps = con.prepareStatement(sql);
            if ("mes".equals(periodo)) {
                ps.setString(1, fecha);
                ps.setString(2, fecha);
            } else {
                ps.setString(1, fecha);
            }
            rs = ps.executeQuery();
            if (rs.next()) {
                costo = rs.getDouble("costo");
            }
        } catch (Exception e) {
            System.out.println("Error costo total: " + e);
        }
        return costo;
    }
    
    public double obtenerUtilidad(String periodo, String fecha) {
        double ventas = obtenerVentasTotales(periodo, fecha);
        double costo = obtenerCostoTotal(periodo, fecha);
        return ventas - costo;
    }
    
    public boolean devolverVenta(int ventaId) {
        Connection con = null;
        try {
            con = cn.conectar();
            con.setAutoCommit(false);
            
            // Obtener los productos vendidos
            String sqlDetalle =
                    "SELECT producto_id, cantidad " +
                    "FROM detalle_venta " +
                    "WHERE venta_id=?";
            PreparedStatement psDetalle =
                    con.prepareStatement(sqlDetalle);
            psDetalle.setInt(1, ventaId);
            ResultSet rs = psDetalle.executeQuery();
            while (rs.next()) {
                int productoId = rs.getInt("producto_id");
                int cantidad = rs.getInt("cantidad");
                String sqlStock =
                        "UPDATE productos " +
                        "SET stock = stock + ? " +
                        "WHERE id = ?";
                PreparedStatement psStock =
                        con.prepareStatement(sqlStock);
                psStock.setInt(1, cantidad);
                psStock.setInt(2, productoId);
                psStock.executeUpdate();
            }

            // Cambiar estado de la venta
            String sqlVenta =
                    "UPDATE ventas " +
                    "SET estado='Devuelta' " +
                    "WHERE id=?";
            PreparedStatement psVenta =
                    con.prepareStatement(sqlVenta);
            psVenta.setInt(1, ventaId);
            psVenta.executeUpdate();
            con.commit();
            return true;

        } catch (Exception e) {
            try {
                if (con != null) {
                    con.rollback();
                }
            } catch (Exception ex) {
            }
            System.out.println("Error devolución: " + e);
        } finally {
            try {
                if (con != null) {
                    con.setAutoCommit(true);
                }
            } catch (Exception e) {
            }
        }
        return false;
    }
}
