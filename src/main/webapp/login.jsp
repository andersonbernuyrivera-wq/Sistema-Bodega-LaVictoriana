<%@page contentType="text/html"
pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>

<head>
    <title>Login</title>
    <style>
        body{
            font-family: Arial;
            margin:50px;
            background-color: #ffcc00;
        }
    </style>
</head>
<body>
    <h1>Iniciar Sesión</h1>
    <form action="LoginServlet"
          method="post">
        Usuario:
        <input type="text"
               name="usuario"
               required>
        <br><br>
        Contraseña:
        <input type="password"
               name="contrasena"
               required>
        <br><br>
        <button type="submit">
        Ingresar
        </button>
    </form>
    <br>
    <a href="registro.jsp">
    Registrar Usuario
    </a>
</body>

</html>