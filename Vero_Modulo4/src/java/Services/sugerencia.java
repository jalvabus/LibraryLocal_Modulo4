/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DAO.DAO_Sugerencia;
import Model.Sugerencia;


/**
 *
 * @author juana
 */
@WebServlet(name = "sugerencia", urlPatterns = {"/sugerencia"})
public class sugerencia extends HttpServlet {
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        String action = request.getParameter("action");
        
        Gson gson = new Gson();
        
        if (action.equalsIgnoreCase("create")) {
            DAO_Sugerencia $sugerencia = new DAO_Sugerencia();
            Sugerencia libro = new Sugerencia();
            libro.setNom_libro(request.getParameter("libro"));
            libro.setAutor(request.getParameter("autor"));
            libro.setEditorial(request.getParameter("editorial"));
            libro.setId_usario(request.getParameter("id_usuario"));
            
            $sugerencia.create(libro);
            
            out.println("Sugerencia registrada");
        }
    }
    
}
