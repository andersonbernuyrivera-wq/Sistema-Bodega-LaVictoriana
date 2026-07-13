package dao;

import conexion.Conexion;
import modelo.Producto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAO {

    Conexion cn = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;

    // INSERTAR
    public boolean agregar(Producto p) {
    String sql = "INSERT INTO productos(nombre,precio,costo_compra,stock,presentacion) VALUES(?,?,?,?,?)";
    try {
        con = cn.conectar();
        if (con == null) {
            System.out.println("CONEXIÓN ES NULL");
            return false;
        }
        ps = con.prepareStatement(sql);

        ps.setString(1, p.getNombre());
        ps.setDouble(2, p.getPrecio());
        ps.setDouble(3, p.getCostoCompra());
        ps.setInt(4, p.getStock());
        ps.setString(5, p.getPresentacion());

        int r = ps.executeUpdate();
        System.out.println("FILAS INSERTADAS: " + r);
        return r > 0;
    } catch (Exception e) {
        System.out.println("ERROR DAO: " + e);
        return false;
    }
}
    // LISTAR
    public List<Producto> listar() {
        List<Producto> lista = new ArrayList<>();
        String sql = "SELECT * FROM productos WHERE eliminado = 0 ORDER BY id";
        try {
            con = cn.conectar();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Producto p = new Producto();

                p.setId(rs.getInt("id"));
                p.setNombre(rs.getString("nombre"));
                p.setPrecio(rs.getDouble("precio"));
                p.setCostoCompra(rs.getDouble("costo_compra"));
                p.setStock(rs.getInt("stock"));
                p.setPresentacion(rs.getString("presentacion"));
                lista.add(p);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return lista;
    }
    public List<Producto> listarEliminados() {
        List<Producto> lista = new ArrayList<>();
        String sql =
            "SELECT * FROM productos " +
            "WHERE eliminado = 1 " +
            "ORDER BY fecha_eliminacion DESC";
        try {
            con = cn.conectar();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Producto p = new Producto();

                p.setId(rs.getInt("id"));
                p.setNombre(rs.getString("nombre"));
                p.setPrecio(rs.getDouble("precio"));
                p.setCostoCompra(rs.getDouble("costo_compra"));
                p.setStock(rs.getInt("stock"));
                p.setPresentacion(rs.getString("presentacion"));
                p.setFechaEliminacion(
                        rs.getTimestamp("fecha_eliminacion"));
                lista.add(p);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return lista;
    }
    // ELIMINAR
    public void eliminar(int id) {
        String sql =
        "UPDATE productos " +
        "SET eliminado = 1, " +
        "fecha_eliminacion = NOW() " +
        "WHERE id = ?";
        try {
            con = cn.conectar();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public boolean restaurar(int id){
        String sql =
            "UPDATE productos " +
            "SET eliminado = 0, " +
            "fecha_eliminacion = NULL " +
            "WHERE id=?";
        try{
            con = cn.conectar();
            ps = con.prepareStatement(sql);
            ps.setInt(1,id);
            return ps.executeUpdate()>0;
        }catch(Exception e){
            System.out.println(e);
        }
        return false;
    }
    //BUSCAR PRODUCTOS
    public Producto buscarPorId(int id) {
        Producto p = new Producto();
        String sql = "SELECT * FROM productos WHERE id=?";
        try {
            con = cn.conectar();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                p.setId(rs.getInt("id"));
                p.setNombre(rs.getString("nombre"));
                p.setPrecio(rs.getDouble("precio"));
                p.setCostoCompra(rs.getDouble("costo_compra"));
                p.setStock(rs.getInt("stock"));
                p.setPresentacion(rs.getString("presentacion"));
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return p;
    }
    //EDITAR PRODUCTOS
    public Producto obtenerPorId(int id) {
        Producto p = new Producto();
        String sql = "SELECT * FROM productos WHERE id=?";
        try {
            con = cn.conectar();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                p.setId(rs.getInt("id"));
                p.setNombre(rs.getString("nombre"));
                p.setPrecio(rs.getDouble("precio"));
                p.setStock(rs.getInt("stock"));
                p.setPresentacion(rs.getString("presentacion"));
            }
        } catch (Exception e) {
            System.out.println("Error obtener: " + e);
        }
        return p;
    }
    //ACTUALIZAR PRODUCTOS
    public boolean actualizar(Producto p) {
        String sql =
                "UPDATE productos "
                + "SET nombre=?, precio=?, costo_compra=?, stock=?, presentacion=? "
                + "WHERE id=?";
        try {
            con = cn.conectar();
            ps = con.prepareStatement(sql);
            ps.setString(1, p.getNombre());
            ps.setDouble(2, p.getPrecio());
            ps.setDouble(3, p.getCostoCompra());
            ps.setInt(4, p.getStock());
            ps.setString(5, p.getPresentacion());
            ps.setInt(6, p.getId());
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println("Error actualizar: " + e);
        }
        return false;
    }
}