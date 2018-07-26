/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import DAO.*;
import Model.*;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author juana
 */
@WebServlet(name = "evento", urlPatterns = {"/evento"})
public class evento extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        String action = request.getParameter("action");

        DAO_Evento $evento = new DAO_Evento();
        Gson gson = new Gson();

        if (action.equalsIgnoreCase("getEventos")) {
            String evento = gson.toJson($evento.getAll());
            out.println(evento);
        }

        if (action.equalsIgnoreCase("create")) {
            Evento evento = new Evento();
            evento.setCalificacion(Float.parseFloat(request.getParameter("calificacion")));
            evento.setCosto(Float.parseFloat(request.getParameter("costo")));
            evento.setCupo(Integer.parseInt(request.getParameter("cupo")));
            evento.setDescripcion(request.getParameter("descripcion"));
            evento.setFecha_evento(request.getParameter("fecha_evento"));
            evento.setFoto(request.getParameter("foto"));
            evento.setNombre(request.getParameter("nombre"));
            evento.setStatus(request.getParameter("status"));
            evento.setTipo(request.getParameter("tipo"));

            if (!$evento.eventoRepetido(evento)) {
                $evento.create(evento);
                out.println("Evento creado");
            } else {
                out.println("Evento repetido");
            }

        }

        if (action.equalsIgnoreCase("update")) {
            Evento evento = new Evento();
            evento.setCalificacion(Float.parseFloat(request.getParameter("calificacion")));
            evento.setCosto(Float.parseFloat(request.getParameter("costo")));
            evento.setCupo(Integer.parseInt(request.getParameter("cupo")));
            evento.setDescripcion(request.getParameter("descripcion"));
            evento.setFecha_evento(request.getParameter("fecha_evento"));
            evento.setFoto(request.getParameter("foto"));
            evento.setNombre(request.getParameter("nombre"));
            evento.setStatus(request.getParameter("status"));
            evento.setTipo(request.getParameter("tipo"));
            evento.setId_evento(Integer.parseInt(request.getParameter("id_evento")));

            $evento.update(evento);
            out.println("Evento Modificado");
        }

        if (action.equalsIgnoreCase("getMisEventos")) {
            int idUser = Integer.parseInt(request.getParameter("id_usuario"));
            String myEvents = gson.toJson($evento.getMisEventos(idUser));
            out.println(myEvents);
        }

        if (action.equalsIgnoreCase("cancelarEvento")) {
            int idUser = Integer.parseInt(request.getParameter("id_usuario"));
            int idVentaBoletos = Integer.parseInt(request.getParameter("id_ventaBoleto"));
            String nombreEvento = request.getParameter("nombreEvento");
            int cantidadDevuelta = Integer.parseInt(request.getParameter("cantidadDevuelta"));
            
            Evento evento;
            
            evento = $evento.getOneByName(nombreEvento);
            int cupo = evento.getCupo() + cantidadDevuelta;
            
            $evento.updateEventoCupo(evento.getId_evento(), cupo);
            
            $evento.deleteBoletos(idVentaBoletos);
            
            DAO_TarjetaPrepago $tarjeta = new DAO_TarjetaPrepago();
            Tarjeta_prepago tarjetaP = $tarjeta.getTarjetaByUsuario(idUser);
            
            Float saldo = Float.parseFloat(request.getParameter("totalDevuelto")) + tarjetaP.getSaldo();
            
            $tarjeta.updateSaldo(saldo, tarjetaP.getCodigo_tarjeta());
            
            out.println("Todo actualizado");
        }
    }

}
