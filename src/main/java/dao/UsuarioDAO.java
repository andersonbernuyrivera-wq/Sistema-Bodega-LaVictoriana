package dao;
import conexion.Conexion;
import java.sql.*;
import modelo.Usuario;

public class UsuarioDAO {

    Conexion cn = new Conexion();
    public Usuario validar(String usuario,
                           String contrasena){
        Usuario u = null;
        try{
            Connection con = cn.conectar();
            String sql =
                "SELECT * FROM usuarios "
              + "WHERE usuario=? "
              + "AND contrasena=?";
            PreparedStatement ps =
                con.prepareStatement(sql);

            ps.setString(1, usuario);
            ps.setString(2, contrasena);

            ResultSet rs =
                ps.executeQuery();
            if(rs.next()){
                u = new Usuario();
                u.setId(rs.getInt("id"));
                u.setUsuario(rs.getString("usuario"));
                u.setNombre(rs.getString("nombre"));
                u.setRol(rs.getString("rol"));
            }

        }catch(Exception e){
            System.out.println(e);
        }
        return u;
    }

    public boolean registrar(Usuario u){
        try{
            Connection con =
                    cn.conectar();
            String sql =
                "INSERT INTO usuarios"
              + "(usuario,contrasena,"
              + "nombre,rol)"
              + "VALUES(?,?,?,?)";

            PreparedStatement ps =
                con.prepareStatement(sql);
            ps.setString(1,
                    u.getUsuario());
            ps.setString(2,
                    u.getContrasena());
            ps.setString(3,
                    u.getNombre());
            ps.setString(4,
                    u.getRol());

            return ps.executeUpdate()>0;

        }catch(Exception e){

            System.out.println(e);
        }
        return false;
    }
}
