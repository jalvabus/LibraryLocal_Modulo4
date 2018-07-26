/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Connection.DB_Manager;
import DAO.*;
import Model.*;
import com.google.gson.Gson;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.BaseColor;

import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.*;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import java.sql.CallableStatement;
import javax.servlet.http.HttpSession;

/**
 *
 * @author juana
 */
@WebServlet(name = "usuario", urlPatterns = {"/usuario"})
public class usuario extends HttpServlet {

    DAO_Usuario dao_usuario = new DAO_Usuario();

    Usuario usuario;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        String action = request.getParameter("action");

        HttpSession sesion = request.getSession();
        usuario = (Usuario) sesion.getAttribute("user");

        Gson gson = new Gson();

        DAO_Compra $compra = new DAO_Compra();
        DAO_Usuario $usuario = new DAO_Usuario();

        if (action.equalsIgnoreCase("getPassword")) {
            String correo = request.getParameter("correo");
            String pass = $usuario.getPassword(correo);
            System.out.println(pass);
            sendPassword(correo, pass);
            out.println(pass);
        }
        if (action.equalsIgnoreCase("verMisCompras")) {
            String usuario = request.getParameter("id_usuario");
            String ventas = gson.toJson($compra.getVenta(Integer.parseInt(usuario)));
            out.println(ventas);
        }

        if (action.equalsIgnoreCase("verDetalleCompra")) {
            String usuario = request.getParameter("id_usuario");
            String id_venta = request.getParameter("id_venta");

            String detalles = gson.toJson($compra.getDetalleVenta(Integer.parseInt(id_venta), Integer.parseInt(usuario)));
            out.println(detalles);
        }

        if (action.equalsIgnoreCase("compraWishList")) {
            String direccionCompra = usuario.getCalle() + ", " + usuario.getColonia() + " , " + usuario.getMunicipio() + " , " + usuario.getEstado();
            String id_libro = request.getParameter("id_libro");
            DAO_Libro $libro = new DAO_Libro();
            Libro libro = $libro.getOneById(Integer.parseInt(id_libro));

            String noTarjeta = request.getParameter("notarjeta");
            String tipoTarjeta = request.getParameter("tipotarjeta");
            Float nuevoSaldo = Float.parseFloat(request.getParameter("nuevoSaldo"));

            $compra.actualizarSaldoTarjeta(noTarjeta, tipoTarjeta, nuevoSaldo);

            String tipoCompra = request.getParameter("tipoCompra");

            if (tipoCompra.equals("regalo")) {
                System.out.println("Es un regalo");
                String tarjetaFelicitaciones = request.getParameter("tarjetaFelicitacion");
                String envoltura = request.getParameter("envoltura");
                String correo = request.getParameter("correo");
                String direccion = request.getParameter("direccion");

                int idVenta = $compra.createVenta(tipoTarjeta, libro.getPrecio(), direccion, "wishlist", String.valueOf(usuario.getId_Usuario()), 1);
                $compra.createDetalleVenta(idVenta, Integer.parseInt(id_libro), 1);
                reporteCompra(idVenta, usuario.getCorreo(), "\n \t \t Hola " + usuario.getNombre() + " LibraryLocal agradece tu preferencia", direccionCompra, usuario.getTelefono(), libro.getPrecio());

                if (tarjetaFelicitaciones.equalsIgnoreCase("SI")) {
                    System.out.println("Enviando Tarjeta");
                    String mensaje = request.getParameter("mensaje");
                    reporteRegalo(idVenta, usuario.getCorreo(), "\n \t \t Hola, esto es LibraryLocal, uno de tus amigos te regalo esto esperamos que lo disfrutes \n \n", direccion, null, libro.getPrecio());
                    enviarTarjeta(correo, envoltura, mensaje);
                }
                enviarCorreoDetalle(usuario.getCorreo(), null);
                enviarRegalo(correo, null);
            } else {
                int idVenta = $compra.createVenta(tipoTarjeta, libro.getPrecio(), direccionCompra, "wishlist", String.valueOf(usuario.getId_Usuario()), 1);
                $compra.createDetalleVenta(idVenta, Integer.parseInt(id_libro), 1);
                reporteCompra(idVenta, usuario.getCorreo(), "\n \t \t Hola " + usuario.getNombre() + " LibraryLocal agradece tu preferencia", direccionCompra, usuario.getTelefono(), libro.getPrecio());
            }

            out.println("Libro Comprado");
        }

        if (action.equalsIgnoreCase("finalizarCompra")) {

            String id_usuario = request.getParameter("id_usuario");
            String tipoCompra = request.getParameter("tipoCompra");
            String tipoTarjeta = request.getParameter("tipotarjeta");
            String noTarjeta = request.getParameter("notarjeta");
            Float total = Float.parseFloat(request.getParameter("total"));
            Float nuevoSaldo = Float.parseFloat(request.getParameter("nuevoSaldo"));
            int cantidadLibros = Integer.parseInt(request.getParameter("cantidadLibros"));

            int idVenta = 0;
            Usuario user = $usuario.getOneById(Integer.parseInt(id_usuario));

            if (tipoCompra.equalsIgnoreCase("regalo")) {
                System.out.println("Es un regalo");
                String tarjetaFelicitaciones = request.getParameter("tarjetaFelicitacion");
                String envoltura = request.getParameter("envoltura");
                String correo = request.getParameter("correo");
                String direccion = request.getParameter("direccion");

                idVenta = $compra.createVenta(tipoTarjeta, total, direccion, tipoCompra, id_usuario, cantidadLibros);

                for (int i = 0; i < cantidadLibros; i++) {
                    String paramID = "idBook" + String.valueOf(i);
                    System.out.println("Id_libro: " + request.getParameter(paramID));
                    String paramCant = "cantidad" + String.valueOf(i);
                    System.out.println("Cantidad: " + request.getParameter(paramCant));
                    $compra.createDetalleVenta(idVenta, Integer.parseInt(request.getParameter(paramID)), Integer.parseInt(request.getParameter(paramCant)));
                }

                String direccionCompra = user.getCalle() + ", " + user.getColonia() + " , " + user.getMunicipio() + " , " + user.getEstado();
                reporteCompra(idVenta, user.getCorreo(), "\n \t \t Hola " + user.getNombre() + " LibraryLocal agradece tu preferencia", direccionCompra, user.getTelefono(), total);

                if (tarjetaFelicitaciones.equalsIgnoreCase("SI")) {
                    System.out.println("Enviando Tarjeta");
                    String mensaje = request.getParameter("mensaje");
                    reporteRegalo(idVenta, user.getCorreo(), "\n \t \t Hola, esto es LibraryLocal, uno de tus amigos te regalo esto esperamos que lo disfrutes \n \n", direccion, null, total);
                    enviarTarjeta(correo, envoltura, mensaje);
                }
                enviarCorreoDetalle(usuario.getCorreo(), null);
                enviarRegalo(correo, null);
            } else {
                System.out.println("Insertando en Venta");

                String direccionCompra = user.getCalle() + ", " + user.getColonia() + " , " + user.getMunicipio() + " , " + user.getEstado();
                idVenta = $compra.createVenta(tipoTarjeta, total, direccionCompra, tipoCompra, id_usuario, cantidadLibros);
                for (int i = 0; i < cantidadLibros; i++) {
                    String paramID = "idBook" + String.valueOf(i);
                    System.out.println("Id_libro: " + request.getParameter(paramID));
                    String paramCant = "cantidad" + String.valueOf(i);
                    System.out.println("Cantidad: " + request.getParameter(paramCant));
                    $compra.createDetalleVenta(idVenta, Integer.parseInt(request.getParameter(paramID)), Integer.parseInt(request.getParameter(paramCant)));
                }
                reporteCompra(idVenta, user.getCorreo(), "\n \t \t Hola " + user.getNombre() + " LibraryLocal agradece tu preferencia", direccionCompra, user.getTelefono(), total);
                enviarCorreoDetalle(usuario.getCorreo(), null);
            }

            System.out.println("Insertando en DetalleVentas");

            $compra.actualizarSaldoTarjeta(noTarjeta, tipoTarjeta, nuevoSaldo);

        }

        /*
        * MODULO 3 
        * Servicio para registrar un usuario Cliente
         */
        if (action.equals("getUsuarioData")) {
            Usuario usuario = dao_usuario.getOneById(Integer.parseInt(request.getParameter("id_usuario")));
            Gson json = new Gson();
            String user = json.toJson(usuario);
            out.print(user);
        }

        if (action.equals("createCliente")) {

            Usuario u = new Usuario();
            u.setCorreo(request.getParameter("correo"));
            u.setNombre(request.getParameter("nombre"));
            u.setApaterno(request.getParameter("apaterno"));
            u.setAmaterno(request.getParameter("amaterno"));
            u.setEdad(Integer.parseInt(request.getParameter("edad")));
            u.setSexo(request.getParameter("sexo"));
            u.setTelefono(request.getParameter("telefono"));
            u.setCalle(request.getParameter("calle"));
            u.setColonia(request.getParameter("colonia"));
            u.setMunicipio(request.getParameter("municipio"));
            u.setEstado(request.getParameter("estado"));
            u.setTipo("Cliente");
            u.setPassword(request.getParameter("password"));

            enviarEmailRegistro(u.getCorreo(), u.getNombre() + ' ' + u.getApaterno() + ' ' + u.getAmaterno());

            if (dao_usuario.createUsuario(u)) {
                DAO_TarjetaCredito $tcredito = new DAO_TarjetaCredito();
                String noTarjeta = request.getParameter("credito");
                $tcredito.insertCredito(noTarjeta);
                out.print("success");
            } else {
                out.print("error");
            }
        }
        /*
        * Servicio para modificar un usuario
         */
        if (action.equals("update")) {

            Usuario u = new Usuario();
            u.setId_Usuario(Integer.parseInt(request.getParameter("id_usuario")));
            u.setCorreo(request.getParameter("correo"));
            u.setNombre(request.getParameter("nombre"));
            u.setApaterno(request.getParameter("apaterno"));
            u.setAmaterno(request.getParameter("amaterno"));
            u.setEdad(Integer.parseInt(request.getParameter("edad")));
            u.setSexo(request.getParameter("sexo"));
            u.setTelefono(request.getParameter("telefono"));
            u.setCalle(request.getParameter("calle"));
            u.setColonia(request.getParameter("colonia"));
            u.setMunicipio(request.getParameter("municipio"));
            u.setEstado(request.getParameter("estado"));
            u.setTipo("Cliente");
            u.setPassword(request.getParameter("password"));

            if (dao_usuario.update(u)) {

                Usuario usuario = dao_usuario.getOneById(Integer.parseInt(request.getParameter("id_usuario")));

                if (usuario != null) {
                    sesion.setAttribute("user", usuario);
                }

                out.print("success");
            } else {
                out.print("error");
            }
        }

        if (action.equalsIgnoreCase("sendRecordatorio")) {
            String evento = request.getParameter("evento");
            sendRecordatorio(usuario.getCorreo(), evento);
        }

    }

    public void reporteCompra(int idVenta, String destinatario, String mensaje, String direccionEnvio, String contact, float total) {
        try {
            Document documento = new Document();

            String ruta = "C:\\reportes";
            String nombre = "detalleCompra";

            PdfWriter writer = PdfWriter.getInstance(documento, new FileOutputStream(ruta + "\\" + nombre + ".pdf"));

            documento.open();

            //Este es el nombre de la cabecera
            documento.add(new Paragraph("\n \n \n \t \t TICKET DE VENTA " + idVenta, FontFactory.getFont("arial", 15, Font.BOLD, BaseColor.BLACK)));
            documento.add(new Paragraph(mensaje, FontFactory.getFont("arial", 15, Font.BOLD, BaseColor.BLACK)));
            documento.add(new Paragraph("\t \t \n Gracias por comprar con nostros, aqui tienes el detalle de tu compra \n \n", FontFactory.getFont("arial", 10, Font.NORMAL, BaseColor.BLACK)));

            String IMAGE = "C:/reportes/favicon.png";

            PdfContentByte canvas = writer.getDirectContentUnder();
            Image image = Image.getInstance(IMAGE);
            image.setAbsolutePosition(50, 750);
            image.scaleAbsoluteHeight(50);
            image.scaleAbsoluteWidth(100);
            canvas.saveState();
            PdfGState state = new PdfGState();
            state.setFillOpacity(0.6f);
            canvas.setGState(state);
            canvas.addImage(image);
            canvas.restoreState();

            PdfPTable tabla;
            tabla = new PdfPTable(5);
            tabla.setWidthPercentage(100);

            PdfPCell celda1 = new PdfPCell(new Paragraph("Libro", FontFactory.getFont("arial", 10, Font.BOLD, BaseColor.BLUE)));
            PdfPCell celda2 = new PdfPCell(new Paragraph("Autor", FontFactory.getFont("arial", 10, Font.BOLD, BaseColor.BLUE)));
            PdfPCell celda3 = new PdfPCell(new Paragraph("Editorial", FontFactory.getFont("arial", 10, Font.BOLD, BaseColor.BLUE)));
            PdfPCell celda4 = new PdfPCell(new Paragraph("Cantidad", FontFactory.getFont("arial", 10, Font.BOLD, BaseColor.BLUE)));
            PdfPCell celda5 = new PdfPCell(new Paragraph("Subtotal", FontFactory.getFont("arial", 10, Font.BOLD, BaseColor.BLUE)));

            tabla.addCell(celda1);
            tabla.addCell(celda2);
            tabla.addCell(celda3);
            tabla.addCell(celda4);
            tabla.addCell(celda5);

            String SQL = "select nombre, autor, editorial, dv.canti_libros, precio, (dv.canti_libros * precio) as subtotal\n"
                    + "from libro l\n"
                    + "inner join detalle_venta dv \n"
                    + "on l.id_libro = dv.id_libro\n"
                    + "inner join venta v\n"
                    + "on dv.codigo_compra = v.codigo_compra\n"
                    + "where v.codigo_compra = '" + idVenta + "';";

            float tot = 0;
            float subtotal = 0;
            try {
                DB_Manager db_manager = new DB_Manager();
                Connection connection = db_manager.getConnection();
                PreparedStatement ps = connection.prepareCall(SQL);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    tabla.addCell(new PdfPCell(new Paragraph(rs.getString(1), FontFactory.getFont("arial", 10, Font.NORMAL, BaseColor.BLACK))));
                    tabla.addCell(new PdfPCell(new Paragraph(rs.getString(2), FontFactory.getFont("arial", 10, Font.NORMAL, BaseColor.BLACK))));
                    tabla.addCell(new PdfPCell(new Paragraph(rs.getString(3), FontFactory.getFont("arial", 10, Font.NORMAL, BaseColor.BLACK))));
                    tabla.addCell(new PdfPCell(new Paragraph(rs.getString(4), FontFactory.getFont("arial", 10, Font.NORMAL, BaseColor.BLACK))));
                    tabla.addCell(new PdfPCell(new Paragraph(rs.getString(5), FontFactory.getFont("arial", 10, Font.NORMAL, BaseColor.BLACK))));
                    subtotal += rs.getFloat(5);
                }
            } catch (Exception e) {
            }

            // Adding Table to document        
            documento.add(tabla);

            documento.add(new Paragraph("\n \n", FontFactory.getFont("arial", 10, Font.NORMAL, BaseColor.BLACK)));
            int envio = 0;
            if (subtotal < 600) {
                envio = 100;
            }
            tot = subtotal + envio;
            float iva = (float) (tot * 0.16);
            documento.add(new Paragraph("\t \t Subtotal: $ " + String.valueOf(subtotal), FontFactory.getFont("arial", 10, Font.NORMAL, BaseColor.BLACK)));
            documento.add(new Paragraph("\t \t Envio: $ " + String.valueOf(envio), FontFactory.getFont("arial", 10, Font.NORMAL, BaseColor.BLACK)));
            documento.add(new Paragraph("\t \t IVA: $ " + String.valueOf(iva), FontFactory.getFont("arial", 10, Font.NORMAL, BaseColor.BLACK)));
            documento.add(new Paragraph("\t \t Total: $ " + total, FontFactory.getFont("arial", 10, Font.NORMAL, BaseColor.BLACK)));

            documento.add(new Paragraph("\t \t \n \n Direccion de envio: " + direccionEnvio, FontFactory.getFont("arial", 10, Font.NORMAL, BaseColor.BLACK)));
            documento.add(new Paragraph("\t \tTelefono de contacto: " + contact, FontFactory.getFont("arial", 10, Font.NORMAL, BaseColor.BLACK)));

            // Closing the document       
            documento.close();

            mostrarReporte(ruta, nombre);
        } catch (DocumentException ex) {
            System.out.println("Error Document Exception " + ex);
        } catch (FileNotFoundException exs) {
            System.out.println("Error File not found " + exs);
        } catch (MalformedURLException bs) {

        } catch (IOException io) {

        }
    }

    public void reporteRegalo(int idVenta, String destinatario, String mensaje, String direccionEnvio, String contact, float total) {
        try {
            Document documento = new Document();

            String ruta = "C:\\reportes";
            String nombre = "detalleRegalo";

            PdfWriter writer = PdfWriter.getInstance(documento, new FileOutputStream(ruta + "\\" + nombre + ".pdf"));

            documento.open();

            //Este es el nombre de la cabecera
            documento.add(new Paragraph("\n \n \n \t \t LSTA DE REGALOS " + idVenta, FontFactory.getFont("arial", 15, Font.BOLD, BaseColor.BLACK)));
            documento.add(new Paragraph(mensaje, FontFactory.getFont("arial", 15, Font.BOLD, BaseColor.BLACK)));

            String IMAGE = "C:/reportes/favicon.png";

            PdfContentByte canvas = writer.getDirectContentUnder();
            Image image = Image.getInstance(IMAGE);
            image.setAbsolutePosition(50, 750);
            image.scaleAbsoluteHeight(50);
            image.scaleAbsoluteWidth(100);
            canvas.saveState();
            PdfGState state = new PdfGState();
            state.setFillOpacity(0.6f);
            canvas.setGState(state);
            canvas.addImage(image);
            canvas.restoreState();

            PdfPTable tabla;
            tabla = new PdfPTable(4);
            tabla.setWidthPercentage(100);

            PdfPCell celda1 = new PdfPCell(new Paragraph("Libro", FontFactory.getFont("arial", 10, Font.BOLD, BaseColor.BLUE)));
            PdfPCell celda2 = new PdfPCell(new Paragraph("Autor", FontFactory.getFont("arial", 10, Font.BOLD, BaseColor.BLUE)));
            PdfPCell celda3 = new PdfPCell(new Paragraph("Editorial", FontFactory.getFont("arial", 10, Font.BOLD, BaseColor.BLUE)));
            PdfPCell celda4 = new PdfPCell(new Paragraph("Cantidad", FontFactory.getFont("arial", 10, Font.BOLD, BaseColor.BLUE)));

            tabla.addCell(celda1);
            tabla.addCell(celda2);
            tabla.addCell(celda3);
            tabla.addCell(celda4);

            String SQL = "select nombre, autor, editorial, dv.canti_libros, precio, (dv.canti_libros * precio) as subtotal\n"
                    + "from libro l\n"
                    + "inner join detalle_venta dv \n"
                    + "on l.id_libro = dv.id_libro\n"
                    + "inner join venta v\n"
                    + "on dv.codigo_compra = v.codigo_compra\n"
                    + "where v.codigo_compra = '" + idVenta + "';";

            try {
                DB_Manager db_manager = new DB_Manager();
                Connection connection = db_manager.getConnection();
                PreparedStatement ps = connection.prepareCall(SQL);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    tabla.addCell(new PdfPCell(new Paragraph(rs.getString(1), FontFactory.getFont("arial", 10, Font.NORMAL, BaseColor.BLACK))));
                    tabla.addCell(new PdfPCell(new Paragraph(rs.getString(2), FontFactory.getFont("arial", 10, Font.NORMAL, BaseColor.BLACK))));
                    tabla.addCell(new PdfPCell(new Paragraph(rs.getString(3), FontFactory.getFont("arial", 10, Font.NORMAL, BaseColor.BLACK))));
                    tabla.addCell(new PdfPCell(new Paragraph(rs.getString(4), FontFactory.getFont("arial", 10, Font.NORMAL, BaseColor.BLACK))));
                }
            } catch (Exception e) {
            }

            // Adding Table to document        
            documento.add(tabla);

            documento.add(new Paragraph("\n \n", FontFactory.getFont("arial", 10, Font.NORMAL, BaseColor.BLACK)));

            documento.add(new Paragraph("\t \t \n \n Direccion de envio: " + direccionEnvio, FontFactory.getFont("arial", 10, Font.NORMAL, BaseColor.BLACK)));
            documento.add(new Paragraph("\t \tTelefono de contacto: " + contact, FontFactory.getFont("arial", 10, Font.NORMAL, BaseColor.BLACK)));

            // Closing the document       
            documento.close();

            mostrarReporte(ruta, nombre);
        } catch (DocumentException ex) {
            System.out.println("Error Document Exception " + ex);
        } catch (FileNotFoundException exs) {
            System.out.println("Error File not found " + exs);
        } catch (MalformedURLException bs) {

        } catch (IOException io) {

        }
    }

    public boolean enviarCorreoDetalle(String para, String mensaje) {
        boolean enviado = false;
        try {

            String host = "smtp.gmail.com";

            Properties prop = System.getProperties();
            prop.put("mail.smtp.ssl.trust", "smtp.gmail.com");
            prop.put("mail.smtp.starttls.enable", "true");
            prop.put("mail.smtp.host", host);
            prop.put("mail.smtp.user", "librarylocal991@gmail.com");
            prop.put("mail.smtp.password", "laverochavez123");
            prop.put("mail.smtp.port", 587);
            prop.put("mail.smtp.auth", "true");

            Session sesion = Session.getDefaultInstance(prop, null);

            MimeMessage message = new MimeMessage(sesion);

            message.setFrom(new InternetAddress("librarylocal991@gmail.com"));

            message.setRecipient(Message.RecipientType.TO, new InternetAddress(para));

            message.setSubject("Detale Compra Libros || LibraryLocal");
            message.setText(mensaje, "utf-8", "html");

            /**
             * Agregar archivo PDF
             */
            // JavaMail 1.3
            Multipart multipart = new MimeMultipart();
            // JavaMail 1.4
            MimeBodyPart attachPart = new MimeBodyPart();
            String attachFile = "C:/reportes/detalleCompra.pdf";
            attachPart.attachFile(attachFile);
            multipart.addBodyPart(attachPart);
            message.setContent(multipart);

            Transport transport = sesion.getTransport("smtp");

            transport.connect(host, "librarylocal991@gmail.com", "laverochavez123");

            transport.sendMessage(message, message.getAllRecipients());

            transport.close();

            enviado = true;

        } catch (Exception e) {
            System.out.println("Error registro : " + e);
        }

        return enviado;
    }

    public boolean enviarTarjeta(String para, String envoltura, String mensaje) {
        boolean enviado = false;
        try {

            String host = "smtp.gmail.com";

            Properties prop = System.getProperties();
            prop.put("mail.smtp.ssl.trust", "smtp.gmail.com");
            prop.put("mail.smtp.starttls.enable", "true");
            prop.put("mail.smtp.host", host);
            prop.put("mail.smtp.user", "librarylocal991@gmail.com");
            prop.put("mail.smtp.password", "laverochavez123");
            prop.put("mail.smtp.port", 587);
            prop.put("mail.smtp.auth", "true");

            Session sesion = Session.getDefaultInstance(prop, null);

            MimeMessage message = new MimeMessage(sesion);

            message.setFrom(new InternetAddress("librarylocal991@gmail.com"));

            message.setRecipient(Message.RecipientType.TO, new InternetAddress(para));

            message.setSubject("Tarjeta de Felicitaciones");

            String msg = "<html>\n"
                    + "    <body>\n"
                    + "        <table class=\"table\">\n"
                    + "            <tbody>\n"
                    + "                <tr>\n"
                    + "                    <td colspan=\"6\"><h2>Acabas de recibir una TARJETA DE FELICITACIONES!</h2></td>\n"
                    + "                </tr>\n"
                    + "                <tr>\n"
                    + "                    <td>\n"
                    + "                        <div style=\"display: block;\">\n"
                    + "                            <div style=\"border: 1px solid #ccc; text-align: center; transition: all 0.4s ease 0s;background: #fff;\" active\">\n"
                    + "                                <span style=\"background: #3EC1D5 none repeat scroll 0 0;color: #fff;font-size: 13px;font-weight: 700;left: -26px;padding: 2px 25px;position: absolute;text-transform: uppercase;top: 16px;transform: rotate(-45deg);-webkit-transform: rotate(-45deg);-ms-transform: rotate(-45deg);-o-transform: rotate(-45deg);-moz-transform: rotate(-45deg)\">Tarjeta</span>"
                    + "                                <h3 style=\"background: #f5f5f5 none repeat scroll 0 0;color: #333;transition: all 0.4s ease 0s;\"><img src=\"https://png.icons8.com/dusk/50/000000/gift.png\" width=\"50\"><br/> <span>Felicidades</span></h3>\n"
                    + "                                <ol style=\"list-style: outside none none;margin: 0;padding: 0 0 25px;display: block;-webkit-margin-before: 1em;-webkit-margin-after: 1em;-webkit-margin-start: 0px;-webkit-margin-end: 0px;-webkit-padding-start: 40px;\">"
                    + "                                    <li class=\"check\">De: " + usuario.getNombre() + " " + usuario.getApaterno() + "</li>\n"
                    + "                                    <li class=\"check\">Mensaje: " + mensaje + "</li>\n"
                    + "                                    <li class=\"check\">Tu paquete llegara con una envoltura: " + envoltura + "</li>\n"
                    + "                                </ol>\n"
                    + "                            </div>\n"
                    + "                        </div>\n"
                    + "                    </td> \n"
                    + "                </tr>\n"
                    + "            </tbody>\n"
                    + "        </table>\n"
                    + "    </body>\n"
                    + "</html>";

            message.setText(msg, "utf-8", "html");

            Transport transport = sesion.getTransport("smtp");

            transport.connect(host, "librarylocal991@gmail.com", "laverochavez123");

            transport.sendMessage(message, message.getAllRecipients());

            transport.close();

            enviado = true;

        } catch (Exception e) {
            System.out.println("Error registro : " + e);
        }

        return enviado;
    }

    public boolean enviarRegalo(String para, String mensaje) {
        boolean enviado = false;
        try {

            String host = "smtp.gmail.com";

            Properties prop = System.getProperties();
            prop.put("mail.smtp.ssl.trust", "smtp.gmail.com");
            prop.put("mail.smtp.starttls.enable", "true");
            prop.put("mail.smtp.host", host);
            prop.put("mail.smtp.user", "librarylocal991@gmail.com");
            prop.put("mail.smtp.password", "laverochavez123");
            prop.put("mail.smtp.port", 587);
            prop.put("mail.smtp.auth", "true");

            Session sesion = Session.getDefaultInstance(prop, null);

            MimeMessage message = new MimeMessage(sesion);

            message.setFrom(new InternetAddress("librarylocal991@gmail.com"));

            message.setRecipient(Message.RecipientType.TO, new InternetAddress(para));

            message.setSubject("Regalo Libros || LibraryLocal");
            message.setText(mensaje, "utf-8", "html");

            /**
             * Agregar archivo PDF
             */
            // JavaMail 1.3
            Multipart multipart = new MimeMultipart();
            // JavaMail 1.4
            MimeBodyPart attachPart = new MimeBodyPart();
            String attachFile = "C:/reportes/detalleRegalo.pdf";
            attachPart.attachFile(attachFile);
            multipart.addBodyPart(attachPart);
            message.setContent(multipart);

            Transport transport = sesion.getTransport("smtp");

            transport.connect(host, "librarylocal991@gmail.com", "laverochavez123");

            transport.sendMessage(message, message.getAllRecipients());

            transport.close();

            enviado = true;

        } catch (Exception e) {
            System.out.println("Error registro : " + e);
        }

        return enviado;
    }

    public void mostrarReporte(String ruta, String nombre) {
        try {
            File abrir = new File(ruta + "\\" + nombre + ".pdf");
            Desktop.getDesktop().open(abrir);
        } catch (IOException e) {
        }
    }

    public boolean enviarEmailRegistro(String para, String nombre) {
        boolean enviado = false;
        try {

            String host = "smtp.gmail.com";

            Properties prop = System.getProperties();
            prop.put("mail.smtp.ssl.trust", "smtp.gmail.com");
            prop.put("mail.smtp.starttls.enable", "true");
            prop.put("mail.smtp.host", host);
            prop.put("mail.smtp.user", "librarylocal991@gmail.com");
            prop.put("mail.smtp.password", "laverochavez123");
            prop.put("mail.smtp.port", 587);
            prop.put("mail.smtp.auth", "true");

            Session sesion = Session.getDefaultInstance(prop, null);

            MimeMessage message = new MimeMessage(sesion);

            message.setFrom(new InternetAddress("librarylocal991@gmail.com"));

            message.setRecipient(Message.RecipientType.TO, new InternetAddress(para));

            message.setSubject("Nuevo Registro | LibraryLocal");

            String msg = "<html>\n"
                    + "    <body>\n"
                    + "        <table class=\"table\">\n"
                    + "            <tbody>\n"
                    + "                <tr>\n"
                    + "                    <td colspan=\"6\"><h2>BIENVENIDO A LIBRARYLOCAL !</h2></td>\n"
                    + "                </tr>\n"
                    + "                <tr>\n"
                    + "                    <td>\n"
                    + "                        <div style=\"display: block;\">\n"
                    + "                            <div style=\"border: 1px solid #ccc; text-align: center; transition: all 0.4s ease 0s;background: #fff;\" active\">\n"
                    + "                                <span style=\"background: #3EC1D5 none repeat scroll 0 0;color: #fff;font-size: 13px;font-weight: 700;left: -26px;padding: 2px 25px;position: absolute;text-transform: uppercase;top: 16px;transform: rotate(-45deg);-webkit-transform: rotate(-45deg);-ms-transform: rotate(-45deg);-o-transform: rotate(-45deg);-moz-transform: rotate(-45deg)\">Biemvenido</span>"
                    + "                                <h3 style=\"background: #f5f5f5 none repeat scroll 0 0;color: #333;transition: all 0.4s ease 0s;\"><img src=\"https://png.icons8.com/color/50/000000/gender-neutral-user.png\" width=\"50\"><br/> <span>Registro Exitoso !!</span></h3>\n"
                    + "                                <ol style=\"list-style: outside none none;margin: 0;padding: 0 0 25px;display: block;-webkit-margin-before: 1em;-webkit-margin-after: 1em;-webkit-margin-start: 0px;-webkit-margin-end: 0px;-webkit-padding-start: 40px;\">"
                    + "                                    <li class=\"check\">Bienvenido " + nombre + " !!</li>\n"
                    + "                                    <li class=\"check\">Comienza a disfrutar de los servicios que pfrecemos en LibraryLocal</li>\n"
                    + "                                    <li class=\"check\"></li>\n"
                    + "                                </ol>\n"
                    + "                            </div>\n"
                    + "                        </div>\n"
                    + "                    </td> \n"
                    + "                </tr>\n"
                    + "            </tbody>\n"
                    + "        </table>\n"
                    + "    </body>\n"
                    + "</html>";

            message.setText(msg, "utf-8", "html");

            Transport transport = sesion.getTransport("smtp");

            transport.connect(host, "librarylocal991@gmail.com", "laverochavez123");

            transport.sendMessage(message, message.getAllRecipients());

            transport.close();

            enviado = true;

        } catch (Exception e) {
            System.out.println("Error registro : " + e);
        }

        return enviado;
    }

    public boolean sendPassword(String para, String pass) {
        boolean enviado = false;
        try {

            String host = "smtp.gmail.com";

            Properties prop = System.getProperties();
            prop.put("mail.smtp.ssl.trust", "smtp.gmail.com");
            prop.put("mail.smtp.starttls.enable", "true");
            prop.put("mail.smtp.host", host);
            prop.put("mail.smtp.user", "librarylocal991@gmail.com");
            prop.put("mail.smtp.password", "laverochavez123");
            prop.put("mail.smtp.port", 587);
            prop.put("mail.smtp.auth", "true");

            Session sesion = Session.getDefaultInstance(prop, null);

            MimeMessage message = new MimeMessage(sesion);

            message.setFrom(new InternetAddress("librarylocal991@gmail.com"));

            message.setRecipient(Message.RecipientType.TO, new InternetAddress(para));

            message.setSubject("Contraseña | LibraryLocal");

            String msg = "<html>\n"
                    + "    <body>\n"
                    + "        <table class=\"table\">\n"
                    + "            <tbody>\n"
                    + "                <tr>\n"
                    + "                    <td colspan=\"6\"><h2>RECUPERAR CONRASEÑA !</h2></td>\n"
                    + "                </tr>\n"
                    + "                <tr>\n"
                    + "                    <td>\n"
                    + "                        <div style=\"display: block;\">\n"
                    + "                            <div style=\"border: 1px solid #ccc; text-align: center; transition: all 0.4s ease 0s;background: #fff;\" active\">\n"
                    + "                                <span style=\"background: #3EC1D5 none repeat scroll 0 0;color: #fff;font-size: 13px;font-weight: 700;left: -26px;padding: 2px 25px;position: absolute;text-transform: uppercase;top: 16px;transform: rotate(-45deg);-webkit-transform: rotate(-45deg);-ms-transform: rotate(-45deg);-o-transform: rotate(-45deg);-moz-transform: rotate(-45deg)\">Recuperar</span>"
                    + "                                <h3 style=\"background: #f5f5f5 none repeat scroll 0 0;color: #333;transition: all 0.4s ease 0s;\"><img src=\"https://png.icons8.com/nolan/50/000000/lock.png\" width=\"50\"><br/> <span>Esta es tu contraseña!</span></h3>\n"
                    + "                                <ol style=\"list-style: outside none none;margin: 0;padding: 0 0 25px;display: block;-webkit-margin-before: 1em;-webkit-margin-after: 1em;-webkit-margin-start: 0px;-webkit-margin-end: 0px;-webkit-padding-start: 40px;\">"
                    + "                                    <li class=\"check\">Esta es tu conraseña " + pass + " No olvides cambiarla!!</li>\n"
                    + "                                    <li class=\"check\">Comienza a disfrutar de los servicios que pfrecemos en LibraryLocal</li>\n"
                    + "                                    <li class=\"check\"></li>\n"
                    + "                                </ol>\n"
                    + "                            </div>\n"
                    + "                        </div>\n"
                    + "                    </td> \n"
                    + "                </tr>\n"
                    + "            </tbody>\n"
                    + "        </table>\n"
                    + "    </body>\n"
                    + "</html>";

            message.setText(msg, "utf-8", "html");

            Transport transport = sesion.getTransport("smtp");

            transport.connect(host, "librarylocal991@gmail.com", "laverochavez123");

            transport.sendMessage(message, message.getAllRecipients());

            transport.close();

            enviado = true;

        } catch (Exception e) {
            System.out.println("Error registro : " + e);
        }

        return enviado;
    }

    public boolean sendRecordatorio(String para, String event) {
        boolean enviado = false;
        try {

            String host = "smtp.gmail.com";

            Properties prop = System.getProperties();
            prop.put("mail.smtp.ssl.trust", "smtp.gmail.com");
            prop.put("mail.smtp.starttls.enable", "true");
            prop.put("mail.smtp.host", host);
            prop.put("mail.smtp.user", "librarylocal991@gmail.com");
            prop.put("mail.smtp.password", "laverochavez123");
            prop.put("mail.smtp.port", 587);
            prop.put("mail.smtp.auth", "true");

            Session sesion = Session.getDefaultInstance(prop, null);

            MimeMessage message = new MimeMessage(sesion);

            message.setFrom(new InternetAddress("librarylocal991@gmail.com"));

            message.setRecipient(Message.RecipientType.TO, new InternetAddress(para));

            message.setSubject("Recordatorio Evento | LibraryLocal");

            String msg = "<html>\n"
                    + "    <body>\n"
                    + "        <table class=\"table\">\n"
                    + "            <tbody>\n"
                    + "                <tr>\n"
                    + "                    <td colspan=\"6\"><h2>RECORDATORIO EVENTO!</h2></td>\n"
                    + "                </tr>\n"
                    + "                <tr>\n"
                    + "                    <td>\n"
                    + "                        <div style=\"display: block;\">\n"
                    + "                            <div style=\"border: 1px solid #ccc; text-align: center; transition: all 0.4s ease 0s;background: #fff;\" active\">\n"
                    + "                                <span style=\"background: #3EC1D5 none repeat scroll 0 0;color: #fff;font-size: 13px;font-weight: 700;left: -26px;padding: 2px 25px;position: absolute;text-transform: uppercase;top: 16px;transform: rotate(-45deg);-webkit-transform: rotate(-45deg);-ms-transform: rotate(-45deg);-o-transform: rotate(-45deg);-moz-transform: rotate(-45deg)\">Recordatorio</span>"
                    + "                                <h3 style=\"background: #f5f5f5 none repeat scroll 0 0;color: #333;transition: all 0.4s ease 0s;\"><img src=\"https://png.icons8.com/nolan/50/000000/lock.png\" width=\"50\"><br/> <span>Te recordamos que tienes un evento mañana!</span></h3>\n"
                    + "                                <ol style=\"list-style: outside none none;margin: 0;padding: 0 0 25px;display: block;-webkit-margin-before: 1em;-webkit-margin-after: 1em;-webkit-margin-start: 0px;-webkit-margin-end: 0px;-webkit-padding-start: 40px;\">"
                    + "                                    <li class=\"check\">Ele evento es " + event + " No olvides asistir!!</li>\n"
                    + "                                    <li class=\"check\"></li>\n"
                    + "                                    <li class=\"check\"></li>\n"
                    + "                                </ol>\n"
                    + "                            </div>\n"
                    + "                        </div>\n"
                    + "                    </td> \n"
                    + "                </tr>\n"
                    + "            </tbody>\n"
                    + "        </table>\n"
                    + "    </body>\n"
                    + "</html>";

            message.setText(msg, "utf-8", "html");

            Transport transport = sesion.getTransport("smtp");

            transport.connect(host, "librarylocal991@gmail.com", "laverochavez123");

            transport.sendMessage(message, message.getAllRecipients());

            transport.close();

            enviado = true;

        } catch (Exception e) {
            System.out.println("Error registro : " + e);
        }

        return enviado;
    }
}
