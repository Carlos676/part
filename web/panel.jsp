<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%-- 
    Document   : panel
    Created on : 19-05-2020, 01:56:48 AM
    Author     : Carlos
--%>

<%
    if(session.getAttribute("logueado")!= "ok"){
        response.sendRedirect("login.jsp");
    }
%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Blog de Salud</h1>
        
        <p>usuario:${sessionScope.usuario}</p>
        <p>Bien benido al panel de adminstracion</p>
        <br>
        
        <c:forEach var="item" items="${lista}">
            ${item.fecha}
            <br>
            ${item.titulo}
            <br>
            ${item.contenido}
            <br>
            
        </c:forEach>
        
        
        <a href="Blogs?op=list">Ver Blogs</a>
        <br>
        <a href="LoginControlador?action=logout">Salir [x]</a>
    </body>
</html>
