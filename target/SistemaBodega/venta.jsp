<%@page import="java.util.List"%>
<%@page import="modelo.Producto"%>
<%@page import="modelo.ItemCarrito"%>
<%@page import="dao.ProductoDAO"%>

<%
    ProductoDAO dao = new ProductoDAO();
    List<Producto> lista = dao.listar();
%>
<%if(session.getAttribute("usuario")
        == null){
    response.sendRedirect(
            "login.jsp");
    return;
}%>
<!DOCTYPE html>
<html>
<head>
    <title>Realizar Venta</title>

    <style>
        body{
            font-family: Arial;
            margin:50px;
            background-color: #ffcc00;
        }
        table{
            width:100%;
            border-collapse: collapse;
            margin-top:20px;
            background-color: #ffffff;
        }
        th, td{
            border:1px solid black;
            padding:10px;
        }
        th{
            background-color: #01033b;
            color: #ffffff;
        }
        input{
            padding:5px;
        }
        .menu{
            margin-bottom:20px;
        }
        .menu a{
            padding:15px;
            text-decoration:none;
            color:black;
            font-weight:bold;
        }
        .menu a:visited{
            color:black;
        }
    </style>

</head>

<body>
    <h1>Sistema de Bodega</h1>
    <%@page import="modelo.Usuario"%>
    <%
    Usuario usuarioLogueado =
            (Usuario) session.getAttribute("usuario");
    %>

    <p>
        Bienvenido:
        <strong>
            <%= usuarioLogueado.getNombre() %>
        </strong>
    </p>
    <p>
        Rol:
        <strong><%= session.getAttribute("rol") %></strong>
    </p>
    <%  String rol = (String) session.getAttribute("rol");%>
    <div class="menu">
        <a href="index.jsp">Productos</a> |
        <a href="venta.jsp">Realizar Venta</a> |
        <a href="compra.jsp">Compras Proveedor</a> |
        <% if("ADMIN".equals(rol)){ %>
        <a href="reportes.jsp">Reporte Ventas</a> |
        <a href="reporteCompras.jsp">Reporte Compras</a> |
        <% } %>
        <a href="utilidad.jsp">Reporte Utilidad</a> |
        <% if("ADMIN".equals(rol)){ %>
        <a href="eliminados.jsp">Eliminados</a> |
        <% } %>
        <a href="LogoutServlet">Cerrar Sesión</a>
    </div>
    <hr>
        <%
        String mensaje =
            (String) session.getAttribute("mensaje");
        if (mensaje != null){
            String color="green";
            if (mensaje.contains("cancelada")){
            color="red";
            }
            %>
            <h3 style="color:<%= color %>;">
                <%= mensaje %>
            </h3>
            <%
                    session.removeAttribute("mensaje");
                }
            %>
    <h2>Realizar Venta</h2>
    BUSCAR PRODUCTO
    <script>
        function filtrarProductos() {
            let filtro =
                document.getElementById(
                    "buscarProducto"
                ).value.toLowerCase();
            let filas =
                document.getElementsByClassName(
                    "filaProducto"
                );
            for(let i = 0; i < filas.length; i++) {
                let nombreProducto =
                    filas[i].cells[0]
                            .innerText
                            .toLowerCase();
                if(nombreProducto.includes(filtro)) {
                    filas[i].style.display = "";
                } else {
                    filas[i].style.display = "none";
                }
            }
        }
    </script>
    
    <input type="text"
       id="buscarProducto"
       placeholder="Buscar producto..."
       onkeyup="filtrarProductos()">

    <table>
        <tr>
            <th>Nombre</th>
            <th>Precio</th>
            <th>Stock</th>
            <th>Presentacion</th>
            <th>Acción</th>
        </tr>

    <%
        for (Producto p : lista) {
    %>

    <tr class="filaProducto">
        <td><%= p.getNombre() %></td>
        <td>S/ <%= p.getPrecio() %></td>
        <td><%= p.getStock() %></td>
        <td><%= p.getPresentacion() %></td>
        <td>

            <form action="CarritoServlet" method="get">
                <input type="hidden"
                       name="id"
                       value="<%= p.getId() %>">
                <input type="number"
                       name="cantidad"
                       value="1"
                       min="1"
                       style="width:60px;">
                <button type="submit">
                    Agregar
                </button>
            </form>
        </td>
    </tr>
    <%
        }
    %>
    </table>
    <hr>
    <h2>Carrito de Compras</h2>
    <%
        List<ItemCarrito> carrito =
                (List<ItemCarrito>) session.getAttribute("carrito");
        double total = 0;
        if (carrito != null && !carrito.isEmpty()) {
    %>

    <table>
        <tr>
            <th>Producto</th>
            <th>Precio</th>
            <th>Cantidad</th>
            <th>Subtotal</th>
        </tr>
    <%
            for (ItemCarrito item : carrito) {
                total += item.getSubtotal();
    %>
    <tr>
        <td><%= item.getNombre() %></td>
        <td>S/ <%= item.getPrecio() %></td>
        <td><%= item.getCantidad() %></td>
        <td>S/ <%= String.format("%.2f", item.getSubtotal()) %></td>
    </tr>
    <%
            }
    %>
    </table>

    <h3>Total: S/
        <%= String.format("%.2f",total)%> 
    </h3>
    <form action="VentaServlet" method="post">
        <h3>Método de Pago</h3>
        <label>
            <input type="radio"
                   name="metodoPago"
                   value="EFECTIVO"
                   checked>
            Efectivo
        </label>
        <label style="margin-left:20px;">
            <input type="radio"
                   name="metodoPago"
                   value="TARJETA">
            Tarjeta
        </label>
        <br><br>
        <button type="submit">
            Finalizar Compra
        </button>
    </form>
    <br>
    <form action="CancelarCompraServlet" method="get">
        <button type="submit">
            Cancelar Compra
        </button>
    </form>
    <%
        } else {
    %>
    <p>No hay productos en el carrito.</p>
    <%
        }
    %>
</body>
</html>