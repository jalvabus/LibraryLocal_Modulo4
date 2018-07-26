/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import DAO.DAO_Devoluciones;
import Model.Devoluciones;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
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
 * @author Jose_Gonzalez
 */
@WebServlet(name = "devoluciones", urlPatterns = {"/devoluciones"})
public class devoluciones extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        String action = request.getParameter("action");

        DAO_Devoluciones $Devoluciones = new DAO_Devoluciones();
        Gson gson = new Gson();

        if (action.equalsIgnoreCase("finalizarDevolucion")) {
            System.out.println("Esta entrando al servicio para devolver");
            String tipoDevolucion = request.getParameter("tipoD");
            String motivoD = request.getParameter("motivo");
            String fechaD = request.getParameter("fecha");
            String tipo_pago = request.getParameter("tipo_pago");
            int subtotal = Integer.parseInt(request.getParameter("subtotal"));
            int codigoC = Integer.parseInt(request.getParameter("codigo"));
            int id_Usuario = Integer.parseInt(request.getParameter("id_usuario"));
            int id_detalleVenta = Integer.parseInt(request.getParameter("id_detalle"));
            String correo = request.getParameter("correo");
            int id_libro = Integer.parseInt(request.getParameter("id_libro"));
            String nombre = request.getParameter("nombre");
            int cantidadLibros = Integer.parseInt(request.getParameter("cantidad"));
            int cantidadDetalle = Integer.parseInt(request.getParameter("cantidadC"));

            Devoluciones devoluciones = new Devoluciones();
            devoluciones.setCodigo_compra(codigoC);
            devoluciones.setMotivo(motivoD);
            devoluciones.setTipo_devolucion(tipoDevolucion);
            devoluciones.setFecha(fechaD);
            devoluciones.setId_libro(id_libro);

            if (tipo_pago.equalsIgnoreCase("credito")) {
                System.out.println("Tipo de Pago...Credito");
                int saldo = $Devoluciones.getCreditCardAmount(id_Usuario);
                int reembolso = subtotal / 2;
                int total = saldo + reembolso;
                System.out.println("Saldo Actual: " + saldo + "\nMonto Compra: " + subtotal + "\nReembolso: " + reembolso + "\nTotal: " + total);
                
                int cantidadTotal = cantidadLibros - cantidadDetalle;

                if ($Devoluciones.updateCreditCardAmount(total, id_Usuario) && $Devoluciones.registerDevolution(devoluciones) 
                        && $Devoluciones.deleteDetail(id_detalleVenta) && $Devoluciones.updateCantBookOfVent(cantidadTotal, codigoC)) {
                    out.println("Devolucion Exitosa!");
                    out.println("Cantidad Reembolsada: " + reembolso);
                    out.println("El saldo de tu Tarjeta de Credito es: " + total);
                    //Enviar Email
                    String origen = "librarylocal991@gmail.com";
                    String clave = "laverochavez123";
                    String destino = correo;
                    String asunto = "Devolucion Libro | LibraryLocal";

                    if (enviarCorreo(origen, clave, destino, asunto, saldo, fechaD, reembolso, total, nombre)) {
                        out.println("Se ha notificado de su devolucion");
                    } else {
                        out.println("NO se pudo notificar");
                    }
                } else {
                    out.print("1");
                }
            } else {
                System.out.println("Tipo de Pago...Debito");
                int saldo = $Devoluciones.getDebitCardAmount(id_Usuario);
                int reembolso = subtotal / 2;
                int total = saldo + reembolso;
                System.out.println("Saldo Actual: " + saldo + "\nMonto Compra: " + subtotal + "\nReembolso: " + reembolso + "\nTotal: " + total);

                int cantidadTotal = cantidadLibros - cantidadDetalle;
                
                if ($Devoluciones.updateDebitCardAmount(total, id_Usuario) && $Devoluciones.registerDevolution(devoluciones) 
                        && $Devoluciones.deleteDetail(id_detalleVenta) && $Devoluciones.updateCantBookOfVent(cantidadTotal, codigoC)) {
                    out.println("Devolucion Exitosa!");
                    out.println("Cantidad Reembolsada: " + reembolso);
                    out.println("El saldo de tu Tarjeta de Debito es: " + total);
                    //Enviar Email
                    String origen = "librarylocal991@gmail.com";
                    String clave = "laverochavez123";
                    String destino = correo;
                    String asunto = "Devolucion Libro | LibraryLocal";

                    if (enviarCorreo(origen, clave, destino, asunto, saldo, fechaD, reembolso, total, nombre)) {
                        out.println("Se ha notificado de su devolucion");
                    } else {
                        out.println("NO se pudo notificar");
                    }
                } else {
                    out.print("1");
                }
            }
        }
    }

    public boolean enviarCorreo(String origen, String clave, String para, String asunto, int saldo, String fechaD, int reembolso, int total, String nombre) {
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
                    + "                    <td colspan=\"6\"><h2>Acabas de realizar una Devolucion!</h2></td>\n"
                    + "                </tr>\n"
                    + "                <tr>\n"
                    + "                    <td>\n"
                    + "                        <div style=\"display: block;\">\n"
                    + "                            <div style=\"border: 1px solid #ccc; text-align: center; transition: all 0.4s ease 0s;background: #fff;\" active\">\n"
                    + "                                <span style=\"background: #3EC1D5 none repeat scroll 0 0;color: #fff;font-size: 13px;font-weight: 700;left: -26px;padding: 2px 25px;position: absolute;text-transform: uppercase;top: 16px;transform: rotate(-45deg);-webkit-transform: rotate(-45deg);-ms-transform: rotate(-45deg);-o-transform: rotate(-45deg);-moz-transform: rotate(-45deg)\">Devolucion de Libro</span>"
                    + "                                <h3 style=\"background: #f5f5f5 none repeat scroll 0 0;color: #333;transition: all 0.4s ease 0s;\"><img src=\"https://png.icons8.com/nolan/2x/literature.png\" width=\"50\"><br/> <span>" + nombre + "</span></h3>\n"
                    + "                                <ol style=\"list-style: outside none none;margin: 0;padding: 0 0 25px;display: block;-webkit-margin-before: 1em;-webkit-margin-after: 1em;-webkit-margin-start: 0px;-webkit-margin-end: 0px;-webkit-padding-start: 40px;\">"
                    + "                                    <li class=\"check\">Fecha: " + fechaD + "</li>\n"
                    + "                                    <li class=\"check\">Saldo:  $ " + saldo + "</li>\n"
                    + "                                    <li class=\"check\">Cantidad Reembolsada:  $ " + reembolso + "</li>\n"
                    + "                                    <li class=\"check\">Saldo actualizado:  $ " + total + "</li>\n"
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
