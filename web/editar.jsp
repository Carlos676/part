<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@page import="com.emergentes.modelo.Blog"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    Blog blog = (Blog) request.getAttribute("blog");
%>
<%
    if (session.getAttribute("logueado") != "ok") {
        response.sendRedirect("login.jsp");
    }
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>
            <c:if test="${blog.id == 0}">Nuevo blog</c:if>
            <c:if test="${blog.id != 0}">Editar blog</c:if>

            </h1>
            <form action="Blogs" method="POST">

                <input type="hidden" name="id" value="${blog.id}">
                <label>Fecha: </label> 
                <input type="date" name="fecha" value="${blog.fecha}" />
                <br>
                <label>Titulo: </label> 
                <input type="text" name="titulo" value="${blog.titulo}" />
                <br>
                <label>Contenido: </label> 
                <textarea type="text" name="contenido" rows="5" cols="40" value="${blog.contenido}" ></textarea>
                <br>

            <input type="submit" value="enviar" />

        </form>

    </body>
</html>
