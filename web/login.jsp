
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Login</h1>
        
        <form action="LoginControlador" method="POST">
            <label>Usuario </label>
            <input type="text" name="usuario" value="" />
            <br>
            <label>Password</label>
            <input type="password" name="password" value="" />
            <br>
            <input type="submit" value="Ingresar" />
        </form>
        
    </body>
</html>
