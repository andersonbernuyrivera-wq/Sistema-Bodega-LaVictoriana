package dao;

import conexion.Conexion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import modelo.Compra;
import modelo.DetalleCompra;

public class CompraDAO {

    Conexion cn = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;

    public List<Compra> listarComprasPorFecha(String fecha){

        List<Compra> lista = new ArrayList<>();

        String sql =
                "SELECT * FROM compras "
                + "WHERE DATE(fecha)=?";

        try{

            con = cn.conectar();

            ps = con.prepareStatement(sql);

            ps.setString(1, fecha);

            rs = ps.executeQuery();

            while(rs.next()){

                Compra c = new Compra();

                c.setId(rs.getInt("id"));
                c.setFecha(rs.getTimestamp("fecha"));
                c.setProveedor(
                        rs.getString("proveedor"));
                c.setTotal(
                        rs.getDouble("total"));

                lista.add(c);
            }

        }catch(Exception e){

            System.out.println(e);
        }

        return lista;
    }

    public List<DetalleCompra> obtenerDetalle(
            int compraId){

        List<DetalleCompra> lista =
                new ArrayList<>();

        String sql =
                "SELECT dc.*, p.nombre "
                + "FROM detalle_compra dc "
                + "INNER JOIN productos p "
                + "ON dc.producto_id = p.id "
                + "WHERE dc.compra_id=?";

        try{

            con = cn.conectar();

            ps = con.prepareStatement(sql);

            ps.setInt(1, compraId);

            rs = ps.executeQuery();

            while(rs.next()){

                DetalleCompra d =
                        new DetalleCompra();

                d.setProductoId(
                        rs.getInt("producto_id"));

                d.setNombreProducto(
                        rs.getString("nombre"));

                d.setCantidad(
                        rs.getInt("cantidad"));

                d.setCostoUnitario(
                        rs.getDouble(
                                "costo_unitario"));

                d.setSubtotal(
                        rs.getDouble(
                                "subtotal"));
                lista.add(d);
            }
        }catch(Exception e){
            System.out.println(e);
        }
        return lista;
    }
}