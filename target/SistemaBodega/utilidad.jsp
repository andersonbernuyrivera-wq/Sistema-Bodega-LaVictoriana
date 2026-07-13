<%@page import="dao.VentaDAO"%>
<%
    VentaDAO dao = new VentaDAO();
    String fecha = request.getParameter("fecha");
    String periodo = request.getParameter("periodo");
    double ventas = 0;
    double costo = 0;
    double utilidad = 0;
    double margen = 0;

    if(fecha != null && periodo != null){
        ventas = dao.obtenerVentasTotales(periodo, fecha);
        costo = dao.obtenerCostoTotal(periodo, fecha);
        utilidad = dao.obtenerUtilidad(periodo, fecha);
        if(ventas > 0){
            margen = (utilidad / ventas) * 100;
        }
    }
%>

<%if(session.getAttribute("usuario")==null){
    response.sendRedirect("login.jsp");
    return;
}%>
<!DOCTYPE html>

<html>
<head>
    <title>Reporte de Utilidad</title>

    <style>
        body{
            font-family:Arial;
            margin:50px;
            background:#ffcc00;
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
        .contenedor{
            display:flex;
            gap:20px;
            margin-top:25px;
            flex-wrap:wrap;
        }
        .tarjeta{
            width:220px;
            background:white;
            border-radius:10px;
            padding:20px;
            box-shadow:0px 0px 8px gray;
            text-align:center;
        }
        .tarjeta h3{
            margin:0;
            color:#01033b;
        }
        .valor{
            font-size:28px;
            font-weight:bold;
            margin-top:15px;
            color:green;
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
    <h2>Reporte de Utilidad</h2>
    <form method="get">
        <label>Periodo:</label>
        <select name="periodo" required>
            <option value="">Seleccione</option>
            <option value="dia"
                <%= "dia".equals(periodo) ? "selected" : "" %>>
                Día
            </option>
            
            <option value="semana"
                <%= "semana".equals(periodo) ? "selected" : "" %>>
                Semana
            </option>
            
            <option value="mes"
                <%= "mes".equals(periodo) ? "selected" : "" %>>
                Mes
            </option>
        </select>
        <label style="margin-left:20px;">Fecha:</label>
        <input type="date"
               name="fecha"
               value="<%= fecha!=null ? fecha : "" %>"
               required>
        <button type="submit">
            Buscar
        </button>
    </form>

    <%
    if(fecha!=null && periodo!=null){
    %>

    <div class="contenedor">
        <div class="tarjeta">
            <h3>Ventas Totales</h3>
            <div class="valor">
                S/
                <%= String.format("%.2f",ventas) %>
            </div>
        </div>

        <div class="tarjeta">
            <h3>Costo Total</h3>
            <div class="valor">
                S/
                <%= String.format("%.2f",costo) %>
            </div>
        </div>

        <div class="tarjeta">
            <h3>Utilidad</h3>
            <div class="valor">
                S/
                <%= String.format("%.2f",utilidad) %>
            </div>
        </div>

        <div class="tarjeta">
            <h3>Margen</h3>
            <div class="valor">
                <%= String.format("%.2f",margen) %> %
            </div>
        </div>
    </div>

    <%
    }
    %>
</body>
</html>