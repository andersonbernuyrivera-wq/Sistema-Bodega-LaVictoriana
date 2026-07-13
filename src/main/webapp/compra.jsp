<%@page import="java.util.List"%>
<%@page import="dao.ProductoDAO"%>
<%@page import="modelo.Producto"%>

<%
    ProductoDAO dao = new ProductoDAO();
    List<Producto> productos = dao.listar();
%>

<!DOCTYPE html>
<html>
<head>
    <title>Compras a Proveedores</title>
    <style>
        body{
            font-family: Arial;
            margin:50px;
            background-color: #ffcc00;
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
        table{
            width:100%;
            border-collapse:collapse;
            background:white;
        }
        th{
            background:#01033b;
            color:white;
        }
        th,td{
            border:1px solid #ccc;
            padding:8px;
            text-align:center;
        }
        input[type=number]{
            width:80px;
        }
        .btn{
            background:green;
            color:white;
            border:none;
            padding:8px 15px;
            cursor:pointer;
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
    <h2>Registrar Compra a Proveedor</h2>

    <form action="CompraProveedorServlet" method="post">

        <label>Proveedor:</label>

        <input type="text"
               name="proveedor"
               required>

        <br><br>

        <table>

            <tr>
                <th>Producto</th>
                <th>Presentacion</th>
                <th>Stock Actual</th>
                <th>Precio de venta</th>
                <th>Costo Unitario</th>
                <th>Cantidad Comprada</th>
            </tr>

            <%
                for(Producto p : productos){
            %>

            <tr>

                <td>
                    <%= p.getNombre() %>
                    <input type="hidden"
                           name="productoId"
                           value="<%= p.getId() %>">
                </td>
                
                <td>
                    <%=p.getPresentacion() %>
                </td>

                <td>
                    <%= p.getStock() %>
                </td>
                
                <td>
                    <%= p.getPrecio() %>
                </td>
                
                <td>
                    <input type="number"
                           step="0.01"
                           name="costo"
                           value="0">
                </td>

                <td>
                    <input type="number"
                           name="cantidad"
                           value="0">
                </td>

            </tr>

            <%
                }
            %>

        </table>
        <br>
        <button type="submit" class="btn">
            Registrar Compra
        </button>
    </form>
</body>
</html>