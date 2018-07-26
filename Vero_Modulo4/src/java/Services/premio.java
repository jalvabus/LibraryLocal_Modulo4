/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import DAO.DAO_Evento;
import DAO.DAO_Premio;
import Model.Premio;
import com.google.gson.Gson;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author José Luis González
 */
@WebServlet(name = "premio", urlPatterns = {"/premio"})
public class premio extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        String action = request.getParameter("action");

        DAO_Premio $Premios = new DAO_Premio();
        Gson gson = new Gson();

        if (action.equalsIgnoreCase("getPremios")) {
            String premios = gson.toJson($Premios.getAll());
            out.println(premios);
        }

        if (action.equalsIgnoreCase("register")) {
            Premio premio = new Premio();
            premio.setNombre(request.getParameter("nombre"));
            premio.setPuntos(Integer.parseInt(request.getParameter("puntos")));
            premio.setStatus(request.getParameter("status"));
            premio.setCantidad(Integer.parseInt(request.getParameter("cantidad")));
            premio.setDescripcion(request.getParameter("descripcion"));
            premio.setFotopremio(request.getParameter("fotopremio"));

            if ($Premios.getOneByName(request.getParameter("nombre")) == null) {
                if ($Premios.register(premio)) {
                    out.println("Premio registrado");
                } else {
                    out.println("No registrado");
                }
            } else {
                out.println("Premio repetido");
            }

        }

        if (action.equalsIgnoreCase("update")) {
            Premio premio = new Premio();
            premio.setId_premio(Integer.parseInt(request.getParameter("id")));
            premio.setNombre(request.getParameter("nombre"));
            premio.setPuntos(Integer.parseInt(request.getParameter("puntos")));
            premio.setStatus(request.getParameter("status"));
            premio.setCantidad(Integer.parseInt(request.getParameter("cantidad")));
            premio.setDescripcion(request.getParameter("descripcion"));
            premio.setFotopremio(request.getParameter("fotopremio"));
            if ($Premios.getOneByName(request.getParameter("nombre")) == null) {
                if ($Premios.update(premio)) {
                    out.println("Premio Actualizado");
                } else {
                    out.println("Premio No Actualizado");
                }
            } else {
                out.println("Premio repetido");
            }

        }

        if (action.equalsIgnoreCase("updateStatus")) {
            Premio premio = new Premio();
            premio.setId_premio(Integer.parseInt(request.getParameter("id")));
            premio.setStatus(request.getParameter("status"));
            if ($Premios.changeStatus(premio)) {
                out.println("Status Actualizado");
            } else {
                out.println("Status No Actualizado");
            }

        }

        if (action.equalsIgnoreCase("updateCantidad")) {
            Premio premio = new Premio();
            premio.setId_premio(Integer.parseInt(request.getParameter("id")));
            premio.setCantidad(Integer.parseInt(request.getParameter("cantidad")));
            if ($Premios.changeCantidad(premio)) {
                out.println("Cantidad Actualizada");
            } else {
                out.println("Cantidad No Actualizada");
            }

        }

        if (action.equalsIgnoreCase("getPremiosByStatus")) {
            String premios = gson.toJson($Premios.getAllByStatus());
            out.println(premios);
        }

        if (action.equalsIgnoreCase("changePrize")) {
            Premio premio = new Premio();
            premio.setId_premio(Integer.parseInt(request.getParameter("id")));
            premio.setCantidad(Integer.parseInt(request.getParameter("cantidad")));
            int id = Integer.parseInt(request.getParameter("id"));
            String correo = request.getParameter("correo");
            int costoPuntos = Integer.parseInt(request.getParameter("puntos"));
            String fecha = request.getParameter("fecha");
            String nombreP = request.getParameter("nombreP");
            String descripcion = request.getParameter("desc");

            System.out.println("Correo: " + correo);

            if ($Premios.getCantByPrize(id) > 0) {
                int idUser = $Premios.getIdUser(correo);
                if (idUser == 0) {
                    System.out.println("No se pudo obtener el ID");
                } else {
                    System.out.println("Id del Usuario: " + idUser);
                    int saldoPuntos = $Premios.getPuntos(idUser);
                    System.out.println("Puntos: " + saldoPuntos);
                    if (saldoPuntos >= costoPuntos) {
                        int totPuntos = saldoPuntos - costoPuntos;
                        if ($Premios.updatePuntos(totPuntos, idUser) && $Premios.changeCantidad(premio) && $Premios.registerCanjear(id, idUser, fecha)) {
                            out.println("Premio Canjeado");
                            out.println("Tus puntos Actuales son: " + totPuntos);
                            String info = $Premios.getNomUser(idUser);
                            generarComprobante(idUser, info, fecha, nombreP, descripcion, totPuntos, id, costoPuntos);
                            System.out.println("Datos enviados: " + idUser + " " + info + " " + fecha + " " + id + " " + nombreP + " " + costoPuntos + " " + descripcion + " " + totPuntos);
                        } else {
                            out.println("Ocurrio un error al canjear el premio");
                        }
                    } else {
                        out.println("Puntos Insuficientes");
                    }
                }
            } else {
                out.println("El premio se ha AGOTADO");
            }
        }

        if (action.equalsIgnoreCase("getMyPremios")) {
            //String correo = request.getParameter("correo");
            String correo = (String) request.getSession().getAttribute("correo");
            System.out.println("Correo del Usuario: " + correo);
            int idUser = $Premios.getIdUser(correo);
            System.out.println("Id del Usuario: " + idUser);
            String myPremios = gson.toJson($Premios.getMyPrize(idUser));
            out.println(myPremios);
            System.out.println(myPremios);
        }

        if (action.equalsIgnoreCase("getMontoPuntos")) {
            int codigo = Integer.parseInt(request.getParameter("codigo"));
            System.out.println("Codigo_compra: " + codigo);
            int monto = $Premios.getMonto(codigo);
            System.out.println("Monto: " + monto);
            String correo = request.getParameter("correo");
            System.out.println("Correo: " + correo);
            int idUser = $Premios.getIdUser(correo);
            int saldoPuntos = $Premios.getPuntos(idUser);
            System.out.println("Puntos: " + saldoPuntos);
            int calcular = (monto * 1) / 10;
            System.out.println("Calculo de puntos: " + calcular);
            int totalPuntos = saldoPuntos + calcular;
            System.out.println("Puntos Totales: " + totalPuntos);

            if ($Premios.getTicketByCode(codigo) == null) {
                if ($Premios.getVentaByCode(codigo) == null) {
                    out.println("El Tickek No existe");
                } else {
                    if ($Premios.updatePuntos(totalPuntos, idUser) && $Premios.registerTicket(codigo, monto, calcular)) {
                        out.println("Ticket Registrado");
                        out.println("Puntos Obtenidos: " + calcular);
                        out.println("Saldo de Puntos: " + totalPuntos);
                    } else {
                        out.println("Ticket No Registrado");
                    }
                }
            } else {
                out.println("El Ticket ya fue registrado");
            }
        }

    }

    public void generarComprobante(int idUser, String nombre, String fecha, String nombreP, String descripcion, int puntosT, int idPremio, int puntos) {
        String ruta = "C:\\comprobantesCanje";
        try {
            Document documento = new Document(PageSize.A4.rotate());
            PdfWriter.getInstance(documento, new FileOutputStream(ruta + "\\" + idUser + nombre + ".pdf"));
            documento.open();

            Paragraph p0 = new Paragraph("____________________________________________________");
            Paragraph p1 = new Paragraph("LibraryLocal S.A DE C.V República Pte. 2855.");
            Paragraph p3 = new Paragraph("fecha: " + fecha);
            Paragraph p4 = new Paragraph("\nUsuario: " + idUser);
            Paragraph p5 = new Paragraph("Nombre: " + nombre);
            Paragraph p6 = new Paragraph("____________________________________________________");
            Paragraph p7 = new Paragraph("");
            Paragraph p8 = new Paragraph("");

            p0.setAlignment(Element.ALIGN_RIGHT);
            p1.setAlignment(Element.ALIGN_RIGHT);
            p3.setAlignment(Element.ALIGN_RIGHT);
            p4.setAlignment(Element.ALIGN_RIGHT);
            p5.setAlignment(Element.ALIGN_RIGHT);
            p6.setAlignment(Element.ALIGN_RIGHT);

            documento.add(p0);
            documento.add(p1);
            documento.add(p3);
            documento.add(p4);
            documento.add(p5);
            documento.add(p6);
            documento.add(p7);
            documento.add(p8);

            PdfPTable tabla = new PdfPTable(4);
            tabla.setWidthPercentage(100);

            PdfPCell celda1 = new PdfPCell(new Paragraph("Identificador", FontFactory.getFont("Arial", 12, Font.BOLD, BaseColor.RED)));
            PdfPCell celda2 = new PdfPCell(new Paragraph("Nombre", FontFactory.getFont("Arial", 12, Font.BOLD, BaseColor.RED)));
            PdfPCell celda3 = new PdfPCell(new Paragraph("Puntos", FontFactory.getFont("Arial", 12, Font.BOLD, BaseColor.RED)));
            PdfPCell celda4 = new PdfPCell(new Paragraph("Descripción", FontFactory.getFont("Arial", 12, Font.BOLD, BaseColor.RED)));

            celda1.setBorder(0);
            celda2.setBorder(0);
            celda3.setBorder(0);
            celda4.setBorder(0);

            tabla.addCell(celda1);
            tabla.addCell(celda2);
            tabla.addCell(celda3);
            tabla.addCell(celda4);

            PdfPCell c1 = new PdfPCell(new Paragraph("" + idPremio));
            PdfPCell c2 = new PdfPCell(new Paragraph(nombreP));
            PdfPCell c3 = new PdfPCell(new Paragraph("" + puntos));
            PdfPCell c4 = new PdfPCell(new Paragraph(descripcion));

            c1.setBorder(0);
            c2.setBorder(0);
            c3.setBorder(0);
            c4.setBorder(0);

            tabla.addCell(c1);
            tabla.addCell(c2);
            tabla.addCell(c3);
            tabla.addCell(c4);

            PdfPCell total = new PdfPCell(new Paragraph("Puntos Restantes:  " + puntosT));
            total.setColspan(4);
            total.setBorderWidthBottom(0);
            total.setBorderWidthLeft(0);
            total.setBorderWidthRight(0);
            tabla.addCell(total);

            documento.add(tabla);
            documento.setPageSize(PageSize.A4);
            documento.close();

            mostrarComprobante(ruta, nombre, idUser);
        } catch (DocumentException ex) {
            System.out.println("Excepcion SQL: " + ex.getMessage());
        } catch (FileNotFoundException ex) {
            System.out.println("Excepcion SQL: " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("Excepcion SQL: " + ex.getMessage());
        }
    }

    public void mostrarComprobante(String ruta, String nombre, int idUser) {
        try {
            File abrir = new File(ruta + "\\" + idUser + nombre + ".pdf");
            Desktop.getDesktop().open(abrir);
        } catch (Exception e) {
        }
    }

}
