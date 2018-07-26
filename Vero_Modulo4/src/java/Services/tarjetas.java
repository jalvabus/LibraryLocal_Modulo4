/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import DAO.DAO_TarjetaCredito;
import DAO.DAO_TarjetaPrepago;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;

/**
 *
 * @author juana
 */
@WebServlet(name = "tarjetas", urlPatterns = {"/tarjetas"})
public class tarjetas extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        String action = request.getParameter("action");

        DAO_TarjetaCredito tcredito = new DAO_TarjetaCredito();
        DAO_TarjetaPrepago tprepago = new DAO_TarjetaPrepago();

        Gson gson = new Gson();

        if (action.equalsIgnoreCase("getSaldoCredito")) {
            String usuario = request.getParameter("id_usuario");
            String tarjeta = request.getParameter("no_tarjeta");

            String tajeta = gson.toJson(tcredito.getTarjeta(tarjeta, usuario));
            out.println(tajeta);
        }

        if (action.equalsIgnoreCase("getSaldoPrepago")) {
            String usuario = request.getParameter("id_usuario");
            String tarjeta = request.getParameter("no_tarjeta");

            String tajeta = gson.toJson(tprepago.getTarjeta(tarjeta, usuario));
            out.println(tajeta);
        }

        /**
         * MODULO V3.1
         */
        if (action.equalsIgnoreCase("getTarjeta")) {
            String tipo = request.getParameter("tipo");
            String usuario = request.getParameter("id_usuario");
            if (tipo.equalsIgnoreCase("credito")) {
                String tajeta = gson.toJson(tcredito.getTarjetaByUsuario(usuario));
                out.println(tajeta);
            }
            if (tipo.equalsIgnoreCase("prepago")) {
                String tajeta = gson.toJson(tprepago.getTarjetaByUsuario(Integer.parseInt(usuario)));
                out.println(tajeta);
            }
        }

    }

}
