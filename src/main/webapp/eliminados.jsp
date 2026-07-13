<%@page import="java.util.List"%>
<%@page import="dao.ProductoDAO"%>
<%@page import="modelo.Producto"%>

<%
    ProductoDAO dao = new ProductoDAO();
    List<Producto> lista = dao.listarEliminados();
%>

<%if(session.getAttribute("usuario")==null){
    response.sendRedirect("login.jsp");
    return;
}%>

<!DOCTYPE html>
<html>

<head>
    <title>Productos Eliminados</title>
    <style>
        body{
            font-family:Arial;
            margin:50px;
            background:#ffcc00;
        }
        h1{
            color:#01033b;
        }
        table{
            width:100%;
            border-collapse:collapse;
            background:white;
            margin-top:20px;
        }
        th,td{
            border:1px solid #ccc;
            padding:10px;
            text-align:center;
        }
        th{
            background:#01033b;
            color:white;
        }
        .menu{
            margin-bottom:20px;
        }
        .menu a{
            text-decoration:none;
            padding:15px;
            color:black;
            font-weight:bold;
        }
        .menu a:visited{
            color:black;
        }
        .btn{
            background:#28a745;
            color:white;
            border:none;
            padding:8px 15px;
            border-radius:5px;
            cursor:pointer;
        }
        .btn:hover{
            background:#1d7d34;
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
    String mensaje = (String) session.getAttribute("mensaje");
    if (mensaje != null) {
    %>
    <div style="
        background:#d4edda;
        color:#155724;
        border:1px solid #c3e6cb;
        padding:12px;
        border-radius:8px;
        margin-bottom:20px;
        font-weight:bold;">
        <%= mensaje %>
    </div>

    <%
        session.removeAttribute("mensaje");
    }
    %>
    <h2>Productos Eliminados</h2>
    <table>
        <tr>
            <th>ID</th>
            <th>Producto</th>
            <th>Precio</th>
            <th>Costo Compra</th>
            <th>Stock</th>
            <th>Presentación</th>
            <th>Fecha Eliminación</th>
            <th>Acción</th>
        </tr>

        <%
        for(Producto p : lista){
            %>

            <tr>
            <td><%=p.getId()%></td>

            <td><%=p.getNombre()%></td>
            <td>S/ <%=String.format("%.2f",p.getPrecio())%></td>
            <td>S/ <%=String.format("%.2f",p.getCostoCompra())%></td>
            <td><%=p.getStock()%></td>
            <td><%=p.getPresentacion()%></td>
            <td><%=p.getFechaEliminacion()%></td>
            <td>

            <form action="RestaurarProductoServlet" method="post">
                <input type="hidden"
                       name="id"
                       value="<%=p.getId()%>">
                <button class="btn"
                        type="submit">
                Restaurar
                </button>
            </form>
            </td>
            </tr>
            <%
        }
        %>
    </table>

</body>

</html>