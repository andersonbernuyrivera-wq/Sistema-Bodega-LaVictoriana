<%@page import="dao.ProductoDAO"%>
<%@page import="modelo.Producto"%>
<%@page import="java.util.List"%>
<%@page import="modelo.ItemCarrito"%>
<%@page import="java.util.List"%>
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
    <title>Sistema Bodega</title>     
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

<body class="color_fondo">
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
    <h2>Registrar Producto</h2>
    <form action="ProductoServlet" method="post">
        <input type="text" name="nombre" placeholder="Nombre" required>
        <input type="number" step="0.01" name="precio" placeholder="Precio" required>
        <input type="number" step="0.01" name="costoCompra" placeholder="Costo" required>
        <input type="number" name="stock" placeholder="Stock" required>
        <input type="text" name="presentacion" placeholder="Presentación" required>
        <button type="submit">Guardar</button>
    </form>
    <hr>
    <h2>Lista de Productos</h2>
    <table>
        <tr>
            <th>ID</th>
            <th>Nombre</th>
            <th>Precio</th>
            <th>Costo compra</th>
            <th>Stock</th>
            <th>Presentación</th>
            <th>Estado de inventario</th>
            <% if("ADMIN".equals(rol)){ %>
                <th>Acción</th>
            <% } %>
        </tr>
    <%
        for (Producto p : lista) {
    %>
    <tr>
        <td><%= p.getId() %></td>
        <td><%= p.getNombre() %></td>
        <td><%= p.getPrecio() %></td>
        <td><%= p.getCostoCompra()%></td>
        <td><%= p.getStock() %></td>
        <td><%= p.getPresentacion() %></td>
        <td>
                <%
                    if(p.getStock() == 0){
                %>
                <span style="color:red;font-weight:bold;">
                    Agotado
                </span>
                <%
                    }else if(p.getStock() <= 10){
                %>
                <span style="color:orange;font-weight:bold;">
                    Stock Bajo
                </span>
                <%
                    }else{
                %>
                <span style="color:green;font-weight:bold;">
                    Disponible
                </span>
                <%
                    }
                %>
            </td>
            <% if("ADMIN".equals(rol)){ %>
            <td>

                <a href="EditarProductoServlet?id=<%=p.getId()%>">
                    Editar
                </a>

                |

                <a href="EliminarProductoServlet?id=<%=p.getId()%>"
                   onclick="return confirm('¿Eliminar este producto?')">
                    Eliminar
                </a>

            </td>
            <% } %>
    </tr>
    <%
        }
    %>
    </table>
</body>
</html>