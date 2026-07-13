<%@page import="java.util.*"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="dao.VentaDAO"%>
<%@page import="modelo.Venta"%>
<%@page import="modelo.DetalleVenta"%>

<%
    String fechaSeleccionada = request.getParameter("fecha");
    String orden = request.getParameter("orden");
    String periodo = request.getParameter("periodo");
    VentaDAO dao = new VentaDAO();
    List<Venta> ventas = new ArrayList<>();
    double mayorVenta = 0;
    double menorVenta = 0;
    double promedioVentas = 0;
    int cantidadVentas = 0;
    double totalDia = 0;
    int ventasEfectivo = 0;
    int ventasTarjeta = 0;
    
    if (fechaSeleccionada != null && !fechaSeleccionada.isEmpty()) {
        ventas = dao.listarVentasPorFecha(periodo,fechaSeleccionada, orden);
        mayorVenta = dao.obtenerMayorVenta(periodo, fechaSeleccionada);
        menorVenta = dao.obtenerMenorVenta(periodo, fechaSeleccionada);
        promedioVentas = dao.obtenerPromedioVentas(periodo, fechaSeleccionada);
        cantidadVentas = dao.obtenerCantidadVentas(periodo, fechaSeleccionada);
        for (Venta v : ventas) {
            totalDia += v.getTotal();
            if("EFECTIVO".equals(v.getMetodoPago())) {
                ventasEfectivo++;
            }
            if("TARJETA".equals(v.getMetodoPago())) {
                ventasTarjeta++;
            }
        }
    }
    SimpleDateFormat formatoHora =
            new SimpleDateFormat("HH:mm:ss");
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
    <title>Reportes</title>
    <style>
        body{
            font-family: Arial;
            margin:50px;
            background-color: #ffcc00;
        }
        .boleta{
            border:1px solid black;
            padding:15px;
            margin-top:20px;
            width:700px;
            background-color: #ffff33;
        }
        table{
            width:100%;
            border-collapse: collapse;
            margin-top:10px;
            background-color: #ffffff
        }
        th, td{
            border:1px solid #ccc;
            padding:8px;
            text-align:left;
        }
        th{
            background-color: #01033b;
            color: #ffffff;
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
        align-items:flex-start;
        gap:30px;
        margin-top:20px;
        }
        .reporte{
            width:75%;
        }
        .indicadores{
            width:25%;
            background:#ffffff;
            border:1px solid #ccc;
            border-radius:10px;
            padding:20px;
            position:sticky;
            top:20px;
        }
        .indicadores h2{
            text-align:center;
            color:#01033b;
        }
        .indicador{
            margin-bottom:20px;
            padding:10px;
            border-bottom:1px solid #ddd;
        }
        .indicador strong{
            display:block;
            color:#01033b;
            margin-bottom:5px;
            font-size:16px;
        }
        .valor{
            font-size:22px;
            font-weight:bold;
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
    <h2>Reporte de Ventas</h2>

    <form method="get">
        <label><b>Período:</b></label>
        <br>
        <input type="radio"
               name="periodo"
               value="dia"
               required>
        Día
        <br>
        <input type="radio"
               name="periodo"
               value="semana">
        Semana
        <br>
        <input type="radio"
               name="periodo"
               value="mes">
        Mes
        <br><br>
        <label>Fecha de referencia:</label>
        <input type="date"
               name="fecha"
               value="<%= fechaSeleccionada != null ? fechaSeleccionada : "" %>"
               required>
        <br><br>
        <label><b>Ordenar ventas:</b></label>
        <br>
        <input type="radio"
               name="orden"
               value="reciente"
               required>
        Más reciente
        <br>
        <input type="radio"
               name="orden"
               value="mayor">
        Mayor monto
        <br>
        <input type="radio"
               name="orden"
               value="menor">
        Menor monto
        <br><br>
        <button type="submit">
            Buscar
        </button>
    </form>
               
    <div class="contenedor">
        <div class="reporte">
            <%
                if(fechaSeleccionada != null){
            %>
            <h3>
                Fecha consultada:
                <%= fechaSeleccionada %>
            </h3>
            <%
                    if(ventas.isEmpty()){
            %>
            <p>No existen ventas para esa fecha.</p>
            <%
                    } else {
                        for(Venta venta : ventas){
                            List<DetalleVenta> detalles =
                                    dao.obtenerDetalle(venta.getId());
            %>
            <div class="boleta">
                <h3>
                    BOLETA N° <%= venta.getId() %>
                </h3>
                <p>
                    Hora:
                    <%= formatoHora.format(venta.getFecha()) %>
                </p>
                <p>
                    Estado:
                    <%= venta.getEstado() %>
                </p>
                <p>
                    Método de Pago:
                    <%= venta.getMetodoPago() %>
                </p>
                <table>
                    <tr>
                        <th>Producto</th>
                        <th>Cantidad</th>
                        <th>Precio</th>
                        <th>Subtotal</th>
                    </tr>
                    <%
                        for(DetalleVenta d : detalles){
                    %>

                    <tr>
                        <td><%= d.getNombreProducto() %></td>
                        <td><%= d.getCantidad() %></td>
                        <td>
                        S/
                        <%= String.format(java.util.Locale.US,
                                          "%.2f",
                                          d.getPrecioUnitario()) %>
                        </td>
                        <td>
                        S/
                        <%= String.format(java.util.Locale.US,
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
                    <%= String.format(java.util.Locale.US,
                                      "%.2f",
                                      venta.getTotal()) %>
                </h3>
                    <% if("ADMIN".equals(rol)){ %>
                        <% if("COMPLETADA".equals(venta.getEstado())){ %>
                        <form action="DevolucionServlet" method="post">
                            <input type="hidden"
                                   name="id"
                                   value="<%=venta.getId()%>">
                            <button type="submit"
                                    style="
                                        background:#dc3545;
                                        color:white;
                                        border:none;
                                        padding:8px 18px;
                                        border-radius:6px;
                                        cursor:pointer;
                                        margin-top:10px;"
                                    onclick="return confirm('¿Desea registrar la devolución de esta venta?');">
                                Registrar devolución
                            </button>
                        </form>
                        <% }else{ %>
                            <p style="
                                color:red;
                                font-weight:bold;
                                margin-top:10px;">

                                Venta devuelta
                            </p>
                        <% } %>
                    <% } %>
            </div>

                <%
                    }   // aquí termina el for(Venta venta : ventas)
                %>
            <hr>
            <h2>
                TOTAL VENDIDO DEL DÍA
            </h2>
            <h2 style="color:black;">
                S/
                <%= String.format(java.util.Locale.US,
                                  "%.2f",
                                  totalDia) %>
            </h2>
            <hr>
                <h3>Resumen de Pagos</h3>
            <p>
                Ventas en efectivo:
                <strong><%= ventasEfectivo %></strong>
            </p>

            <p>
                Ventas con tarjeta:
                <strong><%= ventasTarjeta %></strong>
            </p>

            <%
                    }
                }
            %>
            </div>
        <div class="indicadores">

        <h2>Indicadores</h2>

        <div class="indicador">
            <strong>Mayor venta</strong>
            <div class="valor">
                S/ <%= String.format(java.util.Locale.US,"%.2f",mayorVenta) %>
            </div>
        </div>
        <div class="indicador">
            <strong>Menor venta</strong>
            <div class="valor">
                S/ <%= String.format(java.util.Locale.US,"%.2f",menorVenta) %>
            </div>
        </div>
        <div class="indicador">
            <strong>Promedio</strong>
            <div class="valor">
                S/ <%= String.format(java.util.Locale.US,"%.2f",promedioVentas) %>
            </div>
        </div>
        <div class="indicador">
            <strong>Cantidad de ventas</strong>
            <div class="valor">
                <%= cantidadVentas %>
            </div>
        </div>
        </div>
    </div>
</body>
</html>