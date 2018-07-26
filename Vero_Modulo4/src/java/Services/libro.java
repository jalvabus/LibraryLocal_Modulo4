/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import DAO.DAO_Libro;
import Model.Libro;
import Model.Usuario;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author juana
 */
@WebServlet(name = "libro", urlPatterns = {"/libro"})
public class libro extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        String action = request.getParameter("action");

        DAO_Libro $libros = new DAO_Libro();
        Gson gson = new Gson();

        if (action.equalsIgnoreCase("getLibros")) {
            String libros = gson.toJson($libros.getAll());
            out.println(libros);
        }
        
        if (action.equalsIgnoreCase("getLibrosComprados")){
            HttpSession sesion = request.getSession();
            Usuario userSesion = (Usuario) sesion.getAttribute("user");
            String libros = gson.toJson($libros.getComprados(userSesion.getId_Usuario()));
            out.println(libros);
        }

        if (action.equalsIgnoreCase("busqueda")) {
            String nombre = request.getParameter("nombre");
            String libros = gson.toJson($libros.busqueda(nombre));
            out.println(libros);
        }

        if (action.equalsIgnoreCase("eliminar")) {
            String id_libro = request.getParameter("id");
            if ($libros.delele(Integer.parseInt(id_libro))) {
                out.println("Libro Eliminado !");
            } else {
                out.println("No Eliminado");
            }
        }

        if (action.equalsIgnoreCase("register")) {
            Libro book = new Libro();
            book.setNombre(request.getParameter("nombre"));
            book.setAutor(request.getParameter("autor"));
            book.setEditorial(request.getParameter("editorial"));
            book.setPrecio(Float.parseFloat(request.getParameter("precio")));
            book.setCategoria(request.getParameter("categoria"));
            book.setAno_publicacion(Integer.parseInt(request.getParameter("anio")));
            book.setDescripcion(request.getParameter("desc"));
            book.setStatus(request.getParameter("status"));
            book.setCantidad(Integer.parseInt(request.getParameter("cantidad")));
            book.setFoto(request.getParameter("foto"));

            if ($libros.getOneByName(request.getParameter("nombre")) == null) {
                if ($libros.register(book)) {
                    out.println("Libro registrado");
                } else {
                    out.println("No registrado");
                }
            } else {
                out.println("Libro repetido");
            }

        }

        if (action.equalsIgnoreCase("update")) {
            Libro book = new Libro();
            book.setId_libro(Integer.parseInt(request.getParameter("id")));
            book.setNombre(request.getParameter("nombre"));
            book.setAutor(request.getParameter("autor"));
            book.setEditorial(request.getParameter("editorial"));
            book.setPrecio(Float.parseFloat(request.getParameter("precio")));
            book.setCategoria(request.getParameter("categoria"));
            book.setAno_publicacion(Integer.parseInt(request.getParameter("anio")));
            book.setDescripcion(request.getParameter("desc"));
            book.setStatus(request.getParameter("status"));
            book.setCantidad(Integer.parseInt(request.getParameter("cantidad")));
            book.setFoto(request.getParameter("foto"));
            if ($libros.update(book)) {
                out.println("Actualizado");
            } else {
                out.println("No Actualizado");
            }
        }
    }

}
