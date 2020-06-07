package com.emergentes.controlador;

import com.emergentes.modelo.Blog;
import com.emergentes.modelo.Posts;
import com.emergentes.utiles.ConexionDB;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "Blogs", urlPatterns = {"/Blogs"})
public class Blogs extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String op;
        op = (request.getParameter("op") != null) ? request.getParameter("op") : "list";

        ArrayList<Blog> lista = new ArrayList<Blog>();

        ConexionDB canal = new ConexionDB();
        Connection conn = canal.conectar();

        PreparedStatement ps;
        ResultSet rs;

        if (op.equals("list")) {
            try {
                String sql = "select * from post";
                ps = conn.prepareStatement(sql);
                rs = ps.executeQuery();

                while (rs.next()) {
                    Blog lib = new Blog();
                    lib.setId(rs.getInt("id"));
                    lib.setFecha(rs.getString("fecha"));
                    lib.setTitulo(rs.getString("titulo"));
                    lib.setContenido(rs.getString("contenido"));

                    lista.add(lib);
                }
                request.setAttribute("lista", lista);
                request.getRequestDispatcher("index.jsp").forward(request, response);
            } catch (SQLException ex) {
                System.out.println("Error en SQl " + ex.getMessage());
            } finally {
                canal.desconectar();
            }
        }
        if (op.equals("nuevo")) {
            Blog li = new Blog();
            request.setAttribute("blog", li);
            request.getRequestDispatcher("editar.jsp").forward(request, response);
            //response.sendRedirect("editar.jsp");

        }
        if (op.equals("editar")) {
            try {
                int id = Integer.parseInt(request.getParameter("id"));
                String sql = "select * from post where id = ?";
                ps = conn.prepareStatement(sql);
                ps.setInt(1, id);
                rs = ps.executeQuery();

                Blog li = new Blog();
                while (rs.next()) {
                    li.setId(rs.getInt("id"));
                    li.setFecha(rs.getString("fecha"));
                    li.setTitulo(rs.getString("titulo"));
                    li.setContenido(rs.getString("contenido"));
                }

                request.setAttribute("blog", li);
                request.getRequestDispatcher("editar.jsp").forward(request, response);

            } catch (SQLException ex) {
                System.out.println("Errror de sql" + ex.getMessage());
            }
        }

        if (op.equals("eliminar")) {
            try {
                int id = Integer.parseInt(request.getParameter(("id")));

                String sql = "delete from post where id = ?";
                ps = conn.prepareStatement(sql);
                ps.setInt(1, id);

                ps.executeUpdate();
            } catch (SQLException ex) {
                System.out.println("Error de SQL " + ex.getMessage());
            } finally {
                canal.desconectar();
            }
            response.sendRedirect("Blogs");
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String fecha = request.getParameter("fecha");
        String titulo = request.getParameter("titulo");
        String contenido = request.getParameter("contenido");

        Blog l = new Blog();
        l.setId(id);
        l.setFecha(fecha);
        l.setTitulo(titulo);
        l.setContenido(contenido);

        ConexionDB canal = new ConexionDB();
        Connection conn = canal.conectar();
        PreparedStatement ps;
        ResultSet rs;

        if (id == 0) {
            String sql = "insert into post (fecha,titulo,contenido) values (?,?,?)";
            try {
                ps = conn.prepareStatement(sql);
                ps.setString(1, l.getFecha());
                ps.setString(2, l.getTitulo());
                ps.setString(3, l.getContenido());

                ps.executeUpdate();

            } catch (SQLException ex) {
                System.out.println("Error de SQL no se cual es el errror digan " + ex.getMessage());
            } finally {
                canal.desconectar();
            }
            response.sendRedirect("Blogs");
        } else {
            try {
                String sql = "update post set fecha = ?,titulo = ?,contenido = ? where id =? ";
                ps = conn.prepareStatement(sql);
                ps.setString(1, l.getFecha());
                ps.setString(2, l.getTitulo());
                ps.setString(3, l.getContenido());
                ps.setInt(4, l.getId());
                ps.executeUpdate();
            } catch (Exception e) {
                System.out.println("Error al actualizar" + e.getMessage());
            }
            response.sendRedirect("Blogs");
        }

    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
