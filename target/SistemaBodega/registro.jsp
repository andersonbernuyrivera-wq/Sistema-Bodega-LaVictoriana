<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
    <title>Registrar Usuario</title>
</head>
<body>
    <h1>Registrar Usuario</h1>
    <%
    String mensajeError =
    (String) session.getAttribute("mensajeError");

    if(mensajeError != null){
    %>

    <p style="color:red; font-weight:bold;">
        <%= mensajeError %>
    </p>

    <%
    session.removeAttribute("mensajeError");
    }
    %>

    <form action="UsuarioServlet" method="post">
        Nombre:
        <input type="text" name="nombre" required>
        <br><br>
        Usuario:
        <input type="text" name="usuario" required>
        <br><br>
        Contraseña:
        <input type="password" name="contrasena" required>
        <br><br>
        Rol:
        <select name="rol">
            <option value="ADMIN">Administrador</option>
            <option value="CAJERO">Cajero</option>
        </select>
        <br><br>
        <button type="submit">
            Registrar
        </button>
    </form>

    <br>

    <a href="login.jsp">
        Volver al Login
    </a>

</body>
</html>