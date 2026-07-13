package modelo;
public class DetalleCompra {

    private int id;
    private int compraId;
    private int productoId;
    private int cantidad;
    private double costoUnitario;
    private double subtotal;
    private String NombreProducto;
    
    public String getNombreProducto() {
            return NombreProducto;
    }
    public void setNombreProducto(String NombreProducto) {
        this.NombreProducto = NombreProducto;
    }
        
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public int getCompraId() {
        return compraId;
    }
    public void setCompraId(int compraId) {
        this.compraId = compraId;
    }

    public int getProductoId() {
        return productoId;
    }
    public void setProductoId(int productoId) {
        this.productoId = productoId;
    }

    public int getCantidad() {
        return cantidad;
    }
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getCostoUnitario() {
        return costoUnitario;
    }
    public void setCostoUnitario(double costoUnitario) {
        this.costoUnitario = costoUnitario;
    }

    public double getSubtotal() {
        return subtotal;
    }
    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }
}