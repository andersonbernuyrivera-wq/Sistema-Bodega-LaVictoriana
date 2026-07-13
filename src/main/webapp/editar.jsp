<%@page import="modelo.Producto"%>

<%
    Producto p =
        (Producto) request.getAttribute("producto");
%>

<!DOCTYPE html>
<html>

<head>

    <title>Editar Producto</title>
    <style> body {background-color:#ffcc00 }</style>

</head>

<body>

<h1>Editar Producto</h1>

<form action="ProductoServlet" method="post">

    <input type="hidden"
           name="accion"
           value="actualizar">

    <input type="hidden"
           name="id"
           value="<%= p.getId() %>">

    <input type="text"
           name="nombre"
           value="<%= p.getNombre() %>">

    <br><br>

    <input type="number"
           step="0.01"
           name="precio"
           value="<%= p.getPrecio() %>">

    <br><br>

    <input type="number"
           name="stock"
           value="<%= p.getStock() %>">

    <br><br>

    <input type="text"
           name="presentacion"
           value="<%= p.getPresentacion() %>">

    <br><br>

    <button type="submit">

        Guardar Cambios

    </button>

</form>

<br>

<a href="index.jsp">
    Volver
</a>
</body>
</html>