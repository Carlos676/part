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
import javax.persistence.metamodel.SetAttribute;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "LoginControlador", urlPatterns = {"/LoginControlador"})
public class LoginControlador extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String op;
        op = (request.getParameter("op") != null) ? request.getParameter("op") : "list";

        ConexionDB canal = new ConexionDB();
        Connection conn = canal.conectar();
        PreparedStatement ps;
        ResultSet rs;

        String action = (request.getParameter("action") == null) ? "view" : request.getParameter("action");

        if (action.equals("view")) {
            response.sendRedirect("login.jsp");
        }
        if (action.equals("logout")) {
            HttpSession ses = request.getSession();
            ses.invalidate();
            response.sendRedirect("login.jsp");
        }
        
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String usuario = request.getParameter("usuario");
            String password = request.getParameter("password");
            ResultSet rs;

            ConexionDB canal = new ConexionDB();
            Connection cn = canal.conectar();
            String sql = "select * from usuarios where usuario= ? and password = ? limit 1";
            PreparedStatement ps = cn.prepareStatement(sql);
            ArrayList<Blog> lista = new ArrayList<Blog>();
            ps.setString(1, usuario);
            ps.setString(2, password);

            rs = ps.executeQuery();
            if (rs.next()) {
                HttpSession ses = request.getSession();
                ses.setAttribute("logueado", "ok");
                ses.setAttribute("usuario", usuario);

                try {
                    String sql2 = "select * from post";
                    ps = cn.prepareStatement(sql2);
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
                //response.sendRedirect("panel.jsp");
            }
            
            
            else {
                response.sendRedirect("login.jsp");
            }
        } catch (SQLException ex) {
            System.out.println("errro sale en esta parte " + ex.getMessage());
        }

        /*
        ///todo de la tutorial 11
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
            response.sendRedirect("LoginControlador");
        } else {
            try {

                String sql = "update post set fecha=?,titulo=?,contenido=?";
                ps = conn.prepareStatement(sql);
                ps.setString(1, l.getFecha());
                ps.setString(2, l.getTitulo());
                ps.setString(3, l.getContenido());
                ps.setInt(4, l.getId());

            } catch (Exception e) {
                System.out.println("Error al actualizar" + e.getMessage());
            }
            response.sendRedirect("LoginControlador");
        }
*/
    }

}
