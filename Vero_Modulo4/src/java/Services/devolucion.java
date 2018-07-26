/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import DAO.DAO_Devoluciones;
import DAO.DAO_Premio;
import Model.Compra;
import Model.Devoluciones;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author azul
 */
@WebServlet(name = "devolucion", urlPatterns = {"/devolucion"})
public class devolucion extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        String action = request.getParameter("action");

        DAO_Devoluciones $Devoluciones = new DAO_Devoluciones();
        DAO_Premio $Premios = new DAO_Premio();
        Gson gson = new Gson();

        if (action.equalsIgnoreCase("consultarCodigo")) {
            int codigo = Integer.parseInt(request.getParameter("codigo"));
            if ($Premios.getVentaByCode(codigo) == null) {
                out.println("El Codigo de Venta No existe");
            } else {
                ArrayList<Compra> lista = new ArrayList<Compra>($Devoluciones.getVenta(codigo));
                String tipo_compra = "";
                for (int i = 0; i < lista.size(); i++) {
                    tipo_compra = lista.get(i).getTipo_compra();
                }
                if (tipo_compra.equalsIgnoreCase("propia")) {
                    if ($Devoluciones.getDevolutionByCode(codigo) == null) {
                        getCodigo(codigo);
                    } else {
                        out.print("Ya se realizo la Devolucion Previamente");
                    }
                } else {
                    getCodigo(codigo);
                }
            }
        }

        if (action.equalsIgnoreCase("devolucionLibros")) {
            int codigo = this.codigo;
            System.out.println("Codigo: " + codigo);
            String tipoDevolucion = request.getParameter("tipoD");
            String motivo = request.getParameter("motivo");
            String fecha = request.getParameter("fecha");

            Devoluciones devoluciones = new Devoluciones();
            devoluciones.setCodigo_compra(codigo);
            devoluciones.setMotivo(motivo);
            devoluciones.setTipo_devolucion(tipoDevolucion);
            devoluciones.setFecha(fecha);
            devoluciones.setId_libro(0);

            ArrayList<Compra> lista = new ArrayList<Compra>($Devoluciones.getVenta(codigo));
            String tipo_pago = "";
            int monto = 0;
            String tipo_compra = "";
            int id_usuario = 0;
            for (int i = 0; i < lista.size(); i++) {
                tipo_pago = lista.get(i).getTipo_pago();
                monto = (int) lista.get(i).getMonto();
                tipo_compra = lista.get(i).getTipo_compra();
                id_usuario = lista.get(i).getId_usuario();
            }
            if (tipo_pago.equalsIgnoreCase("credito")) {
                System.out.println("Tipo de Pago...Credito");
                if (tipo_compra.equalsIgnoreCase("propia")) {
                    System.out.println("Tipo de Compra...Propia");
                    int saldo = $Devoluciones.getCreditCardAmount(id_usuario);
                    int reembolso = monto / 2;
                    int total = saldo + reembolso;
                    System.out.println("Saldo Actual: " + saldo + "\nMonto Compra: " + monto + "\nReembolso: " + reembolso + "\nTotal: " + total);
                    if ($Devoluciones.updateCreditCardAmount(total, id_usuario) && $Devoluciones.registerDevolution(devoluciones)) {
                        out.println("Devolucion Exitosa!");
                        out.println("Cantidad Reembolsada: " + reembolso);
                        out.println("El saldo de tu Tarjeta de Credito es: " + total);
                    } else {
                        out.print("Ocurrio un ERROR al realizar la Devolucion");
                    }

                } else {
                    System.out.println("Tipo de Compra...Wishlist");
                    getIdUser(id_usuario);
                    getTypePago(tipo_pago);
                    getDevoluciones(motivo, tipoDevolucion, fecha);
                }
            } else {
                System.out.println("Tipo de Pago...Debito");
                if (tipo_compra.equalsIgnoreCase("propia")) {
                    System.out.println("Tipo de Compra...Propia");
                    int saldo = $Devoluciones.getDebitCardAmount(id_usuario);
                    int reembolso = monto / 2;
                    int total = saldo + reembolso;
                    System.out.println("Saldo Actual: " + saldo + "\nMonto Compra: " + monto + "\nReembolso: " + reembolso + "\nTotal: " + total);
                    if ($Devoluciones.updateDebitCardAmount(total, id_usuario) && $Devoluciones.registerDevolution(devoluciones)) {
                        out.println("Devolucion Exitosa!");
                        out.println("Cantidad Reembolsada: " + reembolso);
                        out.println("El saldo de tu Tarjeta de Debito es: " + total);
                    } else {
                        out.print("Ocurrio un ERROR al realizar la Devolucion");
                    }
                } else {
                    System.out.println("Tipo de Compra...Wishlist");
                    getIdUser(id_usuario);
                    getTypePago(tipo_pago);
                    getDevoluciones(motivo, tipoDevolucion, fecha);
                }
            }
        }

        if (action.equalsIgnoreCase("getDetalleVenta")) {
            String detalles = gson.toJson($Devoluciones.getDetalleVenta(this.codigo, this.id_usuario));
            out.println(detalles);
        }

        if (action.equalsIgnoreCase("devolucionLibro")) {
            int idUser = this.id_usuario;
            System.out.println("IdUser: " + idUser);
            String tipoPago = this.tipo;
            int monto = Integer.parseInt(request.getParameter("monto"));

            Devoluciones devoluciones = new Devoluciones();
            devoluciones.setCodigo_compra(this.codigo);
            devoluciones.setMotivo(this.motivo);
            devoluciones.setTipo_devolucion(this.tipoD);
            devoluciones.setFecha(this.fecha);
            devoluciones.setId_libro(Integer.parseInt(request.getParameter("idLibro")));

            if ($Devoluciones.getDevolutionByCodeAndLibro(this.codigo, Integer.parseInt(request.getParameter("idLibro"))) == null) {
                if (tipoPago.equalsIgnoreCase("credito")) {
                    System.out.println("Tipo de Pago...Credito");
                    int saldo = $Devoluciones.getCreditCardAmount(idUser);
                    int reembolso = monto / 2;
                    int total = saldo + reembolso;
                    System.out.println("Saldo Actual: " + saldo + "\nMonto Compra: " + monto + "\nReembolso: " + reembolso + "\nTotal: " + total);

                    if ($Devoluciones.updateDebitCardAmount(total, idUser) && $Devoluciones.registerDevolution(devoluciones)) {
                        out.println("Devolucion Exitosa!");
                        out.println("Cantidad Reembolsada: " + reembolso);
                        out.println("El saldo de tu Tarjeta de Credito es: " + total);
                    } else {
                        out.print("");
                    }
                } else {
                    System.out.println("Tipo de Pago...Debito");
                    int saldo = $Devoluciones.getDebitCardAmount(idUser);
                    int reembolso = monto / 2;
                    int total = saldo + reembolso;
                    System.out.println("Saldo Actual: " + saldo + "\nMonto Compra: " + monto + "\nReembolso: " + reembolso + "\nTotal: " + total);

                    if ($Devoluciones.updateDebitCardAmount(total, idUser) && $Devoluciones.registerDevolution(devoluciones)) {
                        out.println("Devolucion Exitosa!");
                        out.println("Cantidad Reembolsada: " + reembolso);
                        out.println("El saldo de tu Tarjeta de Debito es: " + total);
                    } else {
                        out.print("");
                    }
                }
            } else {
                out.print("Ya se realizo la Devolucion Previamente");
            }
        }

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

            Devoluciones devoluciones = new Devoluciones();
            devoluciones.setCodigo_compra(codigoC);
            devoluciones.setMotivo(motivoD);
            devoluciones.setTipo_devolucion(tipoDevolucion);
            devoluciones.setFecha(fechaD);
            devoluciones.setId_libro(0);

            if (tipo_pago.equalsIgnoreCase("credito")) {
                System.out.println("Tipo de Pago...Credito");
                int saldo = $Devoluciones.getCreditCardAmount(id_Usuario);
                int reembolso = subtotal / 2;
                int total = saldo + reembolso;
                System.out.println("Saldo Actual: " + saldo + "\nMonto Compra: " + subtotal + "\nReembolso: " + reembolso + "\nTotal: " + total);

                if ($Devoluciones.updateCreditCardAmount(total, id_Usuario) && $Devoluciones.registerDevolution(devoluciones) && $Devoluciones.deleteDetail(id_detalleVenta)) {
                    out.println("Devolucion Exitosa!");
                    out.println("Cantidad Reembolsada: " + reembolso);
                    out.println("El saldo de tu Tarjeta de Credito es: " + total);
                } else {
                    out.print("1");
                }
            } else {
                System.out.println("Tipo de Pago...Debito");
                int saldo = $Devoluciones.getDebitCardAmount(id_Usuario);
                int reembolso = subtotal / 2;
                int total = saldo + reembolso;
                System.out.println("Saldo Actual: " + saldo + "\nMonto Compra: " + subtotal + "\nReembolso: " + reembolso + "\nTotal: " + total);

                if ($Devoluciones.updateDebitCardAmount(total, id_Usuario) && $Devoluciones.registerDevolution(devoluciones) && $Devoluciones.deleteDetail(id_detalleVenta)) {
                    out.println("Devolucion Exitosa!");
                    out.println("Cantidad Reembolsada: " + reembolso);
                    out.println("El saldo de tu Tarjeta de Debito es: " + total);
                } else {
                    out.print("1");
                }
            }
        }
    }

    private int codigo;
    private int id_usuario;
    private String tipo;
    private String motivo;
    private String tipoD;
    private String fecha;

    public void getCodigo(int codigo) {
        this.codigo = codigo;
    }

    public void getIdUser(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public void getTypePago(String tipo) {
        this.tipo = tipo;
    }

    public void getDevoluciones(String motivo, String tipoD, String fecha) {
        this.motivo = motivo;
        this.tipoD = tipoD;
        this.fecha = fecha;
    }

}
