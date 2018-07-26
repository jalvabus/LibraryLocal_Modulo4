/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Connection.DB_Manager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import Model.Compra;
import Model.Detalle_Venta;

/**
 *
 * @author juana
 */
public class DAO_Compra {
    
    DB_Manager db_manager;
    Connection connection;
    
    public DAO_Compra() {
        db_manager = new DB_Manager();
        connection = db_manager.getConnection();
    }
    
    public int createVenta(String tipoPago, float monto, String direccion, String tipoCompra, String id_usuario, int cantidadlibros) {
        float puntos = monto / 10;
        try {
            String SQL = "insert into venta values (null, '" + tipoPago + "', " + monto + ", '" + direccion + "', " + puntos + ", '" + tipoCompra + "', now(), '" + id_usuario + "', '" + cantidadlibros + "');";
            PreparedStatement ps = connection.prepareCall(SQL);
            ps.executeUpdate();
            String query = "select MAX(codigo_compra) from venta";
            PreparedStatement id = connection.prepareCall(query);
            ResultSet cdr = id.executeQuery();
            if (cdr.next()) {
                return cdr.getInt(1);
            } else {
                return 0;
            }
        } catch (Exception e) {
            System.out.println("Error DAO_Compra > createVenta : " + e);
            return 0;
        }
    }
    
    public boolean createDetalleVenta(int idVenta, int id_libro, int cantidad) {
        try {
            String SQL = "insert into detalle_venta values (null, '" + id_libro + "', '" + cantidad + "', '" + idVenta + "');";
            PreparedStatement ps = connection.prepareCall(SQL);
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println("Error DAO_Compra > creteDetalleVenta : " + e);
            return false;
        }
    }
    
    public boolean createRegalo(int idVenta, String envoltura, String tarjeta) {
        try {
            String SQL = "insert into regalo values (null, 'codigo_compra', 'envoltura', 'tarjeta', 'costo_envoltura');";
            PreparedStatement ps = connection.prepareCall(SQL);
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println("Error DAO_Compra > createRegalo : " + e);
            return false;
        }
    }
    
    public boolean actualizarSaldoTarjeta(String noTarjeta, String tipo, float saldo) {
        try {
            String SQL = null;
            if (tipo.equalsIgnoreCase("credito")) {
                SQL = "update tarjeta_credito set saldo = '" + saldo + "' where codigo_tarjetacredito = '" + noTarjeta + "';";
            } else {
                SQL = "update tarjeta_prepago set saldo = '" + saldo + "' where codigo_tarjeta = '" + noTarjeta + "';";
            }
            
            System.out.println(SQL);
            PreparedStatement ps = connection.prepareCall(SQL);
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println("Error DAO_Compra > actualizarSaldoTarjeta : " + e);
            return false;
        }
    }
    
    public ArrayList<Compra> getVenta(int id_usuario) {
        ArrayList<Compra> compras = new ArrayList<>();
        try {
            String SQL = "select * from Venta where id_usuario = '" + id_usuario + "' ";
            PreparedStatement ps = connection.prepareCall(SQL);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Compra compra = new Compra();
                compra.setCodigo_compra(rs.getInt(1));
                compra.setTipo_pago(rs.getString(2));
                compra.setMonto(rs.getFloat(3));
                compra.setDireccion(rs.getString(4));
                compra.setPuntos_compra(rs.getInt(5));
                compra.setTipo_compra(rs.getString(6));
                compra.setFecha(rs.getString(7));
                compra.setId_usuario(id_usuario);
                compra.setCantidadLibros(rs.getInt(9));
                compras.add(compra);
            }
        } catch (Exception e) {
            System.out.println("Error DAO_Libro getAll: " + e);
        }
        return compras;
    }
    
    public ArrayList<Detalle_Venta> getDetalleVenta(int idVenta, int id_usuario) {
        ArrayList<Detalle_Venta> detalles = new ArrayList<>();
        try {
            String SQL = "select v.codigo_compra, dv.id_detalleVenta, l.id_libro, nombre, autor, editorial, dv.canti_libros, precio, (dv.canti_libros * precio) as subtotal, l.foto\n"
                    + "from libro l\n"
                    + "inner join detalle_venta dv \n"
                    + "on l.id_libro = dv.id_libro\n"
                    + "inner join venta v\n"
                    + "on dv.codigo_compra = v.codigo_compra\n"
                    + "where v.codigo_compra = " + idVenta + " and v.id_usuario = " + id_usuario + ";";
            
            PreparedStatement ps = connection.prepareCall(SQL);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Detalle_Venta compra = new Detalle_Venta();
                compra.setCodigo_compra(rs.getInt(1));
                compra.setId_detalleVenta(rs.getInt(2));
                compra.setId_libro(rs.getInt(3));
                compra.setNombre(rs.getString(4));
                compra.setAutor(rs.getString(5));
                compra.setEditorial(rs.getString(6));
                compra.setCantidad(rs.getInt(7));
                compra.setPrecio(rs.getFloat(8));
                compra.setSubtotal(rs.getFloat(9));
                compra.setFoto(rs.getString(10));
                detalles.add(compra);
            }
        } catch (Exception e) {
            System.out.println("Error DAO_Libro getAll: " + e);
        }
        return detalles;
    }
}
