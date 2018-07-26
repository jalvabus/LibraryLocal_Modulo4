/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import DAO.DAO_Premio;
import DAO.DAO_Tarjeta;
import Model.Tarjeta;
import Model.Tarjeta_credito;
import Model.Tarjeta_prepago;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author azul
 */
@WebServlet(name = "tarjeta", urlPatterns = {"/tarjeta"})
public class tarjeta extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        String action = request.getParameter("action");

        DAO_Tarjeta $Tarjetas = new DAO_Tarjeta();
        DAO_Premio $Premios = new DAO_Premio();
        Gson gson = new Gson();

        if (action.equalsIgnoreCase("getTarjetas")) {
            String correo = (String) request.getSession().getAttribute("correo");
            System.out.println("Correo del Usuario: " + correo);
            int idUser = $Premios.getIdUser(correo);
            System.out.println("Id del Usuario: " + idUser);
            if ($Tarjetas.getTarjetaPrepagoExist(idUser) == null) {
                String tarjetas = gson.toJson($Tarjetas.getAllByStatus());
                out.println(tarjetas);
            } else {

            }
        }

        if (action.equalsIgnoreCase("getTarjetaPrepago")) {
            String correo = (String) request.getSession().getAttribute("correo");
            System.out.println("Correo del Usuario: " + correo);
            int idUser = $Premios.getIdUser(correo);
            System.out.println("Id del Usuario: " + idUser);
            String tarjetasR = gson.toJson($Tarjetas.getTarjetaPrepago(idUser));
            out.println(tarjetasR);
        }

        if (action.equalsIgnoreCase("getTarjetaCredito")) {
            String correo = (String) request.getSession().getAttribute("correo");
            System.out.println("Correo del Usuario: " + correo);
            int idUser = $Premios.getIdUser(correo);
            System.out.println("Id del Usuario: " + idUser);
            String tarjetasR = gson.toJson($Tarjetas.getTarjetaCredito(idUser));
            out.println(tarjetasR);
        }

        if (action.equalsIgnoreCase("buyTarjeta")) {
            int id = Integer.parseInt(request.getParameter("id"));
            Tarjeta tarjeta = new Tarjeta();
            tarjeta.setIdTarjeta(Integer.parseInt(request.getParameter("idTarjeta")));
            float saldo = Float.parseFloat(request.getParameter("saldo"));

            Tarjeta_prepago tarjetaP = new Tarjeta_prepago();
            tarjetaP.setCodigo_tarjeta(request.getParameter("codigo_tarjeta"));
            tarjetaP.setEstado(request.getParameter("estado"));
            tarjetaP.setId_usuario(id);
            tarjetaP.setSaldo(Float.parseFloat(request.getParameter("saldo")));

            ArrayList<Tarjeta_credito> lista = new ArrayList<Tarjeta_credito>($Tarjetas.getTarjetaCredito(id));
            float saldoUsuario = 0;
            for (int i = 0; i < lista.size(); i++) {
                saldoUsuario = lista.get(i).getSaldo();
            }

            if (saldoUsuario >= saldo) {
                float total = saldoUsuario - saldo;
                if ($Tarjetas.updateTarjeta(id, tarjeta) && $Tarjetas.registerTarjeta(tarjetaP) && $Tarjetas.updateSaldoCredito(total, id)) {
                    out.println("Tarjeta Prepago Comprada!");
                } else {
                    out.println("");
                }
            } else {
                out.println("1");
            }

        }

        if (action.equalsIgnoreCase("recargarSaldoParaMi")) {
            int idUser = Integer.parseInt(request.getParameter("id"));
            float saldo = Float.parseFloat(request.getParameter("saldo"));
            int recarga = Integer.parseInt(request.getParameter("recarga"));

            ArrayList<Tarjeta_credito> lista = new ArrayList<Tarjeta_credito>($Tarjetas.getTarjetaCredito(idUser));
            float saldoCredito = 0;
            for (int i = 0; i < lista.size(); i++) {
                saldoCredito = lista.get(i).getSaldo();
            }

            if (saldoCredito >= recarga) {
                float totalCredito = saldoCredito - recarga;
                float totalPrepago = saldo + recarga;
                if ($Tarjetas.updateSaldoCredito(totalCredito, idUser) && $Tarjetas.updateSaldoPrepago(totalPrepago, idUser)) {
                    out.println("Recarga Satisfactoria!");
                    out.println("Saldo Prepago: $" + saldo);
                    out.println("Saldo Recargado: $" + recarga);
                    out.println("Saldo actualizado: $" + totalPrepago);
                } else {
                    out.println("2");
                }
            } else {
                out.println("1");
            }

        }

        if (action.equalsIgnoreCase("recargarSaldoRegaloP")) {
            int idUser = Integer.parseInt(request.getParameter("id"));
            float saldoP = Float.parseFloat(request.getParameter("saldo"));
            int recarga = Integer.parseInt(request.getParameter("recarga"));
            String correo = request.getParameter("correo");
            String nombre = request.getParameter("nombre");
            String fecha = request.getParameter("fecha");
            String correoUsuario = (String) request.getSession().getAttribute("correo");

            if (saldoP >= recarga) {
                if ($Tarjetas.getCorreoExist(correo) == null) {
                    out.println("4");
                } else {
                    if (correoUsuario.equalsIgnoreCase(correo)) {
                        out.println("6");
                    } else {
                        float totalPrepago = saldoP - recarga;
                        int idUserRecarga = $Premios.getIdUser(correo);
                        System.out.println("Usuario al q se le enviara el regalo: " + idUserRecarga);
                        ArrayList<Tarjeta_prepago> lista = new ArrayList<Tarjeta_prepago>($Tarjetas.getTarjetaPrepago(idUserRecarga));
                        float saldoPrepago = 0;
                        String idTarjeta = "";
                        int idUsuario = 0;
                        for (int i = 0; i < lista.size(); i++) {
                            saldoPrepago = lista.get(i).getSaldo();
                            idTarjeta = lista.get(i).getCodigo_tarjeta();
                            idUsuario = lista.get(i).getId_usuario();
                        }
                        System.out.println("saldoPrepado " + saldoPrepago);
                        System.out.println("idTarjeta " + idTarjeta);
                        System.out.println("idUsuario " + idUsuario);

                        if (idTarjeta.equalsIgnoreCase("") && idUsuario == 0) {
                            out.println("7");
                        } else {
                            float saldoRegalo = saldoPrepago + recarga;
                            if ($Tarjetas.updateSaldoPrepago(totalPrepago, idUser)) {
                                if ($Tarjetas.updateSaldoPrepago(saldoRegalo, idUserRecarga)) {
                                    out.println("Recarga Satisfactoria!");
                                    //Enviar Email
                                    String origen = "librarylocal991@gmail.com";
                                    String clave = "laverochavez123";
                                    String destino = correo;
                                    String asunto = "Tarjeta de Regalo | LibraryLocal";
                                    String tarjeta = "Tarjeta de Prepago";

                                    if (enviarCorreo(origen, clave, destino, asunto, tarjeta, nombre, fecha, recarga)) {
                                        out.println("Se ha notificado al Usuario de la tarjeta regalo");
                                    } else {
                                        out.println("NO se pudo notificar al Usuario");
                                    }
                                } else {
                                    out.println("5");
                                }
                            } else {
                                out.println("5");
                            }
                        }
                    }
                }
            } else {
                out.println("3");
            }

        }

        if (action.equalsIgnoreCase("recargarSaldoRegaloC")) {
            int idUser = Integer.parseInt(request.getParameter("id"));
            float saldoC = Float.parseFloat(request.getParameter("saldo"));
            int recarga = Integer.parseInt(request.getParameter("recarga"));
            String correo = request.getParameter("correo");
            String nombre = request.getParameter("nombre");
            String fecha = request.getParameter("fecha");
            String correoUsuario = (String) request.getSession().getAttribute("correo");

            if (saldoC >= recarga) {
                if ($Tarjetas.getCorreoExist(correo) == null) {
                    out.println("4");
                } else {
                    if (correoUsuario.equalsIgnoreCase(correo)) {
                        out.println("6");
                    } else {
                        float totalCredito = saldoC - recarga;
                        int idUserRecarga = $Premios.getIdUser(correo);
                        System.out.println("Usuario al q se le enviara el regalo: " + idUserRecarga);
                        ArrayList<Tarjeta_credito> lista = new ArrayList<Tarjeta_credito>($Tarjetas.getTarjetaCredito(idUserRecarga));
                        float saldoCredito = 0;
                        for (int i = 0; i < lista.size(); i++) {
                            saldoCredito = lista.get(i).getSaldo();
                        }
                        float saldoRegalo = saldoCredito + recarga;
                        if ($Tarjetas.updateSaldoCredito(totalCredito, idUser)) {
                            if ($Tarjetas.updateSaldoCredito(saldoRegalo, idUserRecarga)) {
                                out.println("Recarga Satisfactoria!");
                                //Enviar Email
                                String origen = "librarylocal991@gmail.com";
                                String clave = "laverochavez123";
                                String destino = correo;
                                String asunto = "Tarjeta de Regalo | LibraryLocal";
                                String tarjeta = "Tarjeta de Credito";

                                if (enviarCorreo(origen, clave, destino, asunto, tarjeta, nombre, fecha, recarga)) {
                                    out.println("Se ha notificado al Usuario de la tarjeta regalo");
                                } else {
                                    out.println("NO se pudo notificar al Usuario");
                                }
                            } else {
                                out.println("5");
                            }
                        } else {
                            out.println("5");
                        }
                    }
                }
            } else {
                out.println("3");
            }

        }

        /*
        Modulo 3
         */
        if (action.equalsIgnoreCase("getAll")) {
            String tarjetas = gson.toJson($Tarjetas.getAll());
            out.println(tarjetas);
        }
    }
    
    public boolean enviarCorreo(String origen, String clave, String para, String asunto, String tarjeta, String nombre, String fecha, int recarga) {
        boolean enviado = false;
        try {

            String host = "smtp.gmail.com";

            Properties prop = System.getProperties();
            prop.put("mail.smtp.ssl.trust", "smtp.gmail.com");
            prop.put("mail.smtp.starttls.enable", "true");
            prop.put("mail.smtp.host", host);
            prop.put("mail.smtp.user", origen);
            prop.put("mail.smtp.password", clave);
            prop.put("mail.smtp.port", 587);
            prop.put("mail.smtp.auth", "true");

            Session sesion = Session.getDefaultInstance(prop, null);

            MimeMessage message = new MimeMessage(sesion);

            message.setFrom(new InternetAddress(origen));

            message.setRecipient(Message.RecipientType.TO, new InternetAddress(para));

            message.setSubject(asunto);
            String mensaje = "<html>\n"
                    + "    <body>\n"
                    + "        <table class=\"table\">\n"
                    + "            <tbody>\n"
                    + "                <tr>\n"
                    + "                    <td colspan=\"6\"><h2>Acabas de recibir una TARJETA DE REGALO!</h2></td>\n"
                    + "                </tr>\n"
                    + "                <tr>\n"
                    + "                    <td>\n"
                    + "                        <div style=\"display: block;\">\n"
                    + "                            <div style=\"border: 1px solid #ccc; text-align: center; transition: all 0.4s ease 0s;background: #fff;\" active\">\n"
                    + "                                <span style=\"background: #3EC1D5 none repeat scroll 0 0;color: #fff;font-size: 13px;font-weight: 700;left: -26px;padding: 2px 25px;position: absolute;text-transform: uppercase;top: 16px;transform: rotate(-45deg);-webkit-transform: rotate(-45deg);-ms-transform: rotate(-45deg);-o-transform: rotate(-45deg);-moz-transform: rotate(-45deg)\">Tarjeta</span>"
                    + "                                <h3 style=\"background: #f5f5f5 none repeat scroll 0 0;color: #333;transition: all 0.4s ease 0s;\"><img src=\"https://png.icons8.com/cotton/2x/card-in-use.png\" width=\"50\"><br/> <span>" + tarjeta + "</span></h3>\n"
                    + "                                <ol style=\"list-style: outside none none;margin: 0;padding: 0 0 25px;display: block;-webkit-margin-before: 1em;-webkit-margin-after: 1em;-webkit-margin-start: 0px;-webkit-margin-end: 0px;-webkit-padding-start: 40px;\">"
                    + "                                    <li class=\"check\">De: " + nombre + "</li>\n"
                    + "                                    <li class=\"check\">Fecha: " + fecha + "</li>\n"
                    + "                                    <li class=\"check\">Cantidad Regalada:  $ " + recarga + "</li>\n"
                    + "                                </ol>\n"
                    + "                            </div>\n"
                    + "                        </div>\n"
                    + "                    </td> \n"
                    + "                </tr>\n"
                    + "            </tbody>\n"
                    + "        </table>\n"
                    + "    </body>\n"
                    + "</html>";
            message.setText(mensaje, "utf-8", "html");

            Transport transport = sesion.getTransport("smtp");

            transport.connect(host, origen, clave);

            transport.sendMessage(message, message.getAllRecipients());

            transport.close();

            enviado = true;

        } catch (Exception e) {
            System.out.println("Error registro : " + e);
        }

        return enviado;
    }

}
