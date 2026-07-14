package modelo;
import java.sql.Timestamp;

public class Producto {

    private int id;
    private String nombre;
    private double precio;
    private double costoCompra;
    private int stock;
    private String presentacion;
    private boolean eliminado;
    private Timestamp fechaEliminacion;
    public Producto() {}

    public Producto(int id, String nombre, double precio, double costoCompra,
            int stock, String cantidad, String presentacion) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.costoCompra= costoCompra;
        this.stock = stock;
        this.presentacion = presentacion;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }

    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }

    public String getPresentacion() { return presentacion; }
    public void setPresentacion(String presentacion) { this.presentacion = presentacion; }
    
    public double getCostoCompra() {return costoCompra; }
    public void setCostoCompra(double costoCompra) {this.costoCompra = costoCompra; }
    
    public boolean isEliminado() {
        return eliminado;
    }
    public void setEliminado(boolean eliminado) {
        this.eliminado = eliminado;
    }
    public Timestamp getFechaEliminacion() {
        return fechaEliminacion;
    }
    public void setFechaEliminacion(Timestamp fechaEliminacion) {
        this.fechaEliminacion = fechaEliminacion;
    }
}