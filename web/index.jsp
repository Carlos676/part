<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<%@page import="com.emergentes.modelo.Blog"%>
<%@page import="java.util.List"%>
<%
    List<Blog> lista = (List<Blog>) request.getAttribute("lista");
%>
<%
    if (session.getAttribute("logueado") != "ok") {
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
        <p align="right">
            usuario:${sessionScope.usuario}
            <a href="LoginControlador?action=logout">Salir [x]</a>      
        </p>
    <center><h1>Blogs de salud</h1></center>
        <p><a href="Blogs?op=nuevo">Nuevo Blog</a></p>



        <c:forEach var="item" items="${lista}">
            ${item.fecha}
            <br>
            <h3>${item.titulo}</h3>
            <p>${item.contenido}</p>
            <p>Autor:${sessionScope.usuario}</p>
            <p align="right">
                <a href="Blogs?op=editar&id=${item.id}">Editar</a>
                <a href="Blogs?op=eliminar&id=${item.id}" onclick="return (confirm('Esta seguro de eliminar'))">Eliminar</a>
            </p>
            <hr>

        </c:forEach>



    </body>
</html>
