package Services;

import DAO.DAO_Compra_boleto;
import DAO.DAO_TarjetaPrepago;
import Model.Compra_boleto;
import Model.Tarjeta_prepago;
import Model.Usuario;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

@WebServlet(name = "comprobantepagoboleto", urlPatterns = {"/comprobantepagoboleto"})
public class comprobantepagoboleto extends HttpServlet {

    DAO_Compra_boleto dao_cb = new DAO_Compra_boleto();
    DAO_TarjetaPrepago dao_tp = new DAO_TarjetaPrepago();
    Usuario usuario;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession sesion = request.getSession();
        usuario = (Usuario) sesion.getAttribute("user");

        ServletOutputStream outs = response.getOutputStream();
        response.setContentType("application/pdf");
        Tarjeta_prepago tp = dao_tp.getTarjetaByUsuario(usuario.getId_Usuario());
        Compra_boleto pago = dao_cb.getPagoRestantesByFolio(Integer.parseInt(request.getParameter("folio")));

        double saldo = tp.getSaldo() - pago.getRestante();

        createReporte(pago);
        enviarCorreo();
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
            document.add(new Paragraph("Restante: $ 0"));
            document.add(new Paragraph("Estado: PAGADO"));

            document.add(new Paragraph(" "));

            BarcodeQRCode barcodeQRCode = new BarcodeQRCode("Folio: " + pago.getFolio()
                    + "Evento: " + pago.getEvento().getNombre()
                    + "Fecha evento: " + pago.getEvento().getFecha_evento()
                    + "Boletos: " + pago.getCantidad_boletos()
                    + "Pagos: " + pago.getCantidad_pagos()
                    + "Costo Total: " + pago.getCosto_total()
                    + "Restante: 0", 1000, 1000, null);
            Image codeQrImage = barcodeQRCode.getImage();
            codeQrImage.scaleAbsolute(100, 100);
            document.add(codeQrImage);

            document.close();
            System.out.println("Eliminado");
            dao_cb.deletePago(pago.getFolio());

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

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
