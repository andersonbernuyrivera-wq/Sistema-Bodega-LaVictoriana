package conexion;

import java.sql.Connection;
import java.sql.DriverManager;

public class Conexion {

    Connection con;

    public Connection conectar() {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/bodegadb";

            String user = "root";
            String pass = "Tzuyu";

            con = DriverManager.getConnection(url, user, pass);
            System.out.println("Conexion OK");

        } catch (Exception e) {
            System.out.println("Error conexion: " + e);
        }
        return con;
    }
    public static void main(String[] args) {
    Conexion c = new Conexion();
    c.conectar();
}
}