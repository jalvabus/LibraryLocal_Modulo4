package Services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import Connection.DB_Manager;
import DAO.DAO_Compra_boleto;
import DAO.DAO_TarjetaPrepago;
import Model.Compra_boleto;
import Model.Evento;
import Model.Tarjeta_prepago;
import Model.Usuario;
import com.google.gson.Gson;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BarcodeQRCode;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.List;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "compra_boleto", urlPatterns = {"/compra_boleto"})
public class compra_boleto extends HttpServlet {

    DAO_Compra_boleto dao_cb = new DAO_Compra_boleto();
    DAO_TarjetaPrepago dao_tp = new DAO_TarjetaPrepago();

    Usuario usuario = null;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        String action = request.getParameter("action");
        HttpSession sesion = request.getSession();
        usuario = (Usuario) sesion.getAttribute("user");

        Gson gson = new Gson();

        if (action.equals("getPagosRestantesByUsuario")) {
            List<Compra_boleto> list = dao_cb.getPagosRestantesByUsuario(usuario.getId_Usuario());
            String json = gson.toJson(list);
            out.print(json);
        }

        if (action.equals("pagar")) {
            Tarjeta_prepago tp = dao_tp.getTarjetaByUsuario(usuario.getId_Usuario());
            if (tp != null) {
                Compra_boleto pago = dao_cb.getPagoRestantesByFolio(Integer.parseInt(request.getParameter("folio")));
                if (pago != null) {

                    if (tp.getSaldo() > pago.getRestante()) {

                        double saldo = tp.getSaldo() - pago.getRestante();

                        if (dao_tp.updateSaldo(saldo, tp.getCodigo_tarjeta())) {
                            out.print("success");
                        } else {
                            out.print("Error al realizar pago con folio " + request.getParameter("folio") + ".");
                        }

                    } else {
                        out.print("No cuentas con el saldo suficiente en tu tarjeta prepago para hacer este pago.");
                    }

                } else {
                    out.print("No se encontro ninguna pago con el folio " + request.getParameter("folio") + ".");
                }
            } else {
                out.print("No se encontro ninguna tarjeta prepago activa.");
            }
        }

        if (action.equalsIgnoreCase("createCompra")) {
            DB_Manager db_manager;
            Connection connection;

            db_manager = new DB_Manager();
            connection = db_manager.getConnection();

            String boletos = request.getParameter("boletos");
            String pagos = request.getParameter("pagos");
            String total = request.getParameter("total");
            String restante = request.getParameter("restante");
            String status = request.getParameter("status");
            String id_evento = request.getParameter("id_evento");
            String id_usuario = request.getParameter("id_usuario");
            
            Evento evento = new DAO.DAO_Evento().getOne(Integer.parseInt(id_evento));
            int nuevaCantidad = evento.getCupo() - Integer.parseInt(boletos);
            new DAO.DAO_Evento().updateEventoCupo(Integer.parseInt(id_evento), nuevaCantidad);
            try {
                String SQL = "insert into compra_boleto values (null, '"
                        + boletos + "', '"
                        + pagos + "', '"
                        + total + "', '"
                        + restante + "', '"
                        + status + "', now(), '"
                        + id_evento + "', '" + id_usuario + "');";

                System.out.println(SQL);
                PreparedStatement ps = connection.prepareCall(SQL);
                ps.executeUpdate();

                String lastID = "SELECT LAST_INSERT_ID();";
                PreparedStatement id = connection.prepareCall(lastID);
                ResultSet rs = id.executeQuery();

                if (rs.next()) {
                    Compra_boleto pago = dao_cb.getPagoRestantesByFolio(rs.getInt(1));
                    createReporte(pago);

                    if (pago.getStatus().equalsIgnoreCase("PAGADO")) {
                        enviarCorreo();
                    }
                }

            } catch (Exception e) {
                System.out.println("Errir compra_boletos " + e);
            }

        }
    }

    public void createReporte(Compra_boleto pago) {
        String ruta = "C:\\reportes";
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(ruta + "\\qr_boleto.pdf"));
            document.open();

            //PdfWriter.getInstance(document, outs);
            Rectangle size = new Rectangle(250, 400);
            document.setPageSize(size);
            document.open();

            Paragraph title = new Paragraph("COMPROBANTE DE PAGO");
            title.setAlignment(Paragraph.ALIGN_CENTER);
            document.add(title);

            document.add(new Paragraph(" "));

            document.add(new Paragraph("Folio: " + pago.getFolio()));
            document.add(new Paragraph("Evento: " + pago.getEvento().getNombre()));
            document.add(new Paragraph("Fecha evento: " + pago.getEvento().getFecha_evento()));
            document.add(new Paragraph("Boletos: " + pago.getCantidad_boletos()));
            document.add(new Paragraph("Pagos: " + pago.getCantidad_pagos()));
            document.add(new Paragraph("Costo Total: $" + pago.getCosto_total()));
            document.add(new Paragraph("Restante: $" + pago.getRestante()));
            document.add(new Paragraph("Estado: " + pago.getStatus()));

            document.add(new Paragraph(" "));

            BarcodeQRCode barcodeQRCode = new BarcodeQRCode("Folio: " + pago.getFolio()
                    + "Evento: " + pago.getEvento().getNombre()
                    + "Fecha evento: " + pago.getEvento().getFecha_evento()
                    + "Boletos: " + pago.getCantidad_boletos()
                    + "Pagos: " + pago.getCantidad_pagos()
                    + "Costo Total: " + pago.getCosto_total()
                    + "Restante: " + pago.getRestante(), 1000, 1000, null);
            Image codeQrImage = barcodeQRCode.getImage();
            codeQrImage.scaleAbsolute(100, 100);

            if (pago.getStatus().equalsIgnoreCase("PAGADO")) {
                document.add(codeQrImage);
            }

            document.close();

            File abrir = new File(ruta + "\\qr_boleto.pdf");
            Desktop.getDesktop().open(abrir);

        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException fnte) {

        } catch (IOException ioex) {

        }
    }

    public boolean enviarCorreo() {
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

            message.setRecipient(Message.RecipientType.TO, new InternetAddress(usuario.getCorreo()));

            message.setSubject("Comprobante de Compra de Boleto || LibraryLocal");
            message.setText("Aqui esta tu comprobante de compra de boletos", "utf-8", "html");

            /**
             * Agregar archivo PDF
             */
            // JavaMail 1.3
            Multipart multipart = new MimeMultipart();
            // JavaMail 1.4
            MimeBodyPart attachPart = new MimeBodyPart();
            String attachFile = "C:/reportes/qr_boleto.pdf";
            attachPart.attachFile(attachFile);
            multipart.addBodyPart(attachPart);
            message.setContent(multipart);

            Transport transport = sesion.getTransport("smtp");

            transport.connect(host, "librarylocal991@gmail.com", "laverochavez123");

            transport.sendMessage(message, message.getAllRecipients());

            transport.close();

            enviado = true;
            System.out.println("Correo enviado a : " + usuario.getCorreo());
        } catch (Exception e) {
            System.out.println("Error enviando correo : " + e);
        }

        return enviado;
    }
}
