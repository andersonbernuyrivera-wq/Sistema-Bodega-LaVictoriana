<%@page import="java.util.*"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="dao.CompraDAO"%>
<%@page import="modelo.Compra"%>
<%@page import="modelo.DetalleCompra"%>
<%@page import="modelo.Usuario"%>
<%
    String fechaSeleccionada = request.getParameter("fecha");

    CompraDAO dao = new CompraDAO();
    List<Compra> compras = new ArrayList<>();
    double totalDia = 0;

    if(fechaSeleccionada != null &&
            !fechaSeleccionada.isEmpty()){
        compras =
                dao.listarComprasPorFecha(fechaSeleccionada);
        for(Compra c : compras){
            totalDia += c.getTotal();
        }
    }
    SimpleDateFormat formatoHora =
            new SimpleDateFormat("HH:mm:ss");
%>

<%
if(session.getAttribute("usuario")==null){
    response.sendRedirect("login.jsp");
    return;
}
%>
<!DOCTYPE html>
<html>
<head>
    <title>Reporte de Compras</title>

    <style>
        body{
            font-family: Arial;
            margin:50px;
            background-color:#ffcc00;
        }
        .boleta{
            border:1px solid black;
            padding:15px;
            margin-top:20px;
            width:700px;
            background-color:#ffff33;
        }
        table{
            width:100%;
            border-collapse:collapse;
            margin-top:10px;
            background:white;
        }
        th,td{
            border:1px solid #ccc;
            padding:8px;
            text-align:left;
        }
        th{
            background:#01033b;
            color:white;
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
<%
Usuario usuarioLogueado =
        (Usuario) session.getAttribute("usuario");
%>

<p>
    Bienvenido:
    <strong><%= usuarioLogueado.getNombre() %></strong>
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
<h2>Reporte de Compras</h2>
<form method="get">

    <label>Seleccione una fecha:</label>

    <input
        type="date"
        name="fecha"
        value="<%= fechaSeleccionada != null ? fechaSeleccionada : "" %>">

    <button type="submit">
        Buscar
    </button>

</form>

<%
if(fechaSeleccionada != null){
%>

<h3>
    Fecha consultada:
    <%= fechaSeleccionada %>
</h3>

<%

if(compras.isEmpty()){

%>

<p>No existen compras para esa fecha.</p>

<%

}else{

for(Compra compra : compras){

List<DetalleCompra> detalles =
        dao.obtenerDetalle(compra.getId());

%>

<div class="boleta">

    <h3>
        COMPRA N° <%= compra.getId() %>
    </h3>

    <p>
        Hora:
        <%= formatoHora.format(compra.getFecha()) %>
    </p>

    <p>
        Proveedor:
        <%= compra.getProveedor() %>
    </p>

    <table>

        <tr>
            <th>Producto</th>
            <th>Cantidad</th>
            <th>Costo Unitario</th>
            <th>Subtotal</th>
        </tr>
        <%

        for(DetalleCompra d : detalles){

        %>

        <tr>

            <td><%= d.getNombreProducto() %></td>

            <td><%= d.getCantidad() %></td>

            <td>
                S/
                <%= String.format(Locale.US,
                        "%.2f",
                        d.getCostoUnitario()) %>
            </td>

            <td>
                S/
                <%= String.format(Locale.US,
                        "%.2f",
                        d.getSubtotal()) %>
            </td>

        </tr>

        <%

        }

        %>

    </table>

    <h3>

        TOTAL:

        S/

        <%= String.format(Locale.US,
                "%.2f",
                compra.getTotal()) %>

    </h3>

</div>

<%

}

%>

<hr>

<h2>
    TOTAL COMPRADO DEL DÍA
</h2>

<h2 style="color:black;">
    S/
    <%= String.format(Locale.US,
            "%.2f",
            totalDia) %>
</h2>

<p>
    Compras realizadas:
    <strong><%= compras.size() %></strong>
</p>

<%

}

}

%>

</body>
</html>