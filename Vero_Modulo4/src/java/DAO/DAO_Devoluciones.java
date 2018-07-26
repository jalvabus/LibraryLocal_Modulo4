/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Connection.DB_Manager;
import Model.Compra;
import Model.Detalle_Venta;
import Model.Devoluciones;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *José Luis González
 */
public class DAO_Devoluciones {
    
    DB_Manager db_manager;
    Connection connection;

    public DAO_Devoluciones() {
        db_manager = new DB_Manager();
        connection = db_manager.getConnection();
    }

    public ArrayList<Compra> getVenta(int codigo) {
        ArrayList<Compra> compras = new ArrayList<>();
        try {
            String SQL = "select * from Venta where codigo_compra = '" + codigo + "' ";
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
                compra.setId_usuario(rs.getInt(8));
                compra.setCantidadLibros(rs.getInt(9));
                compras.add(compra);
            }
        } catch (Exception e) {
            System.out.println("Error DAO_Devoluciones getVenta: " + e);
        }
        return compras;
    }
    
    public int getCreditCardAmount(int idUser) {
        int monto = 0;
        try {
            String SQL = "SELECT saldo FROM tarjeta_credito WHERE id_usuario = '" + idUser + "' ";
            PreparedStatement ps = connection.prepareCall(SQL);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                monto = rs.getInt(1);
            }
        } catch (Exception e) {
            System.out.println("Error DAO_Devoluciones getCreditCardAmount: " + e);
        }
        return monto;
    }
    
    public int getDebitCardAmount(int idUser) {
        int monto = 0;
        try {
            String SQL = "SELECT saldo FROM tarjeta_prepago WHERE id_usuario = '" + idUser + "' ";
            PreparedStatement ps = connection.prepareCall(SQL);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                monto = rs.getInt(1);
            }
        } catch (Exception e) {
            System.out.println("Error DAO_Devoluciones getDebitCardAmount: " + e);
        }
        return monto;
    }
    
    public boolean updateCreditCardAmount(int saldo, int id) {
        try {
            String SQL = "UPDATE tarjeta_credito SET saldo = '"
                    + saldo+ "' where id_usuario = '" + id + "'";
            
            System.out.println(SQL);
            PreparedStatement ps = connection.prepareCall(SQL);
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println("Error DAO_Devoluciones updateCreditCardAmount: " + e);
            return false;
        }
    }
    
    public boolean updateDebitCardAmount(int saldo, int id) {
        try {
            String SQL = "UPDATE tarjeta_prepago SET saldo = '"
                    + saldo+ "' where id_usuario = '" + id + "'";
            
            System.out.println(SQL);
            PreparedStatement ps = connection.prepareCall(SQL);
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println("Error DAO_Devoluciones updateDebitCardAmount: " + e);
            return false;
        }
    }
    
    public boolean registerDevolution(Devoluciones devoluciones) {
        try {
            String SQL = "INSERT INTO devoluciones VALUES (null, "
                    + devoluciones.getCodigo_compra() + ", '"
                    + devoluciones.getMotivo() + "', '"
                    + devoluciones.getTipo_devolucion() + "', '"
                    + devoluciones.getFecha() + "', '"
                    + devoluciones.getId_libro()+ "');";
            
            System.out.println(SQL);
            PreparedStatement ps = connection.prepareCall(SQL);
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println("Error DAO_Devoluciones registerDevolution : " + e);
            return false;
        }
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
            System.out.println("Error DAO_Devoluciones getDetalleVenta: " + e);
        }
        return detalles;
    }
    
    public Devoluciones getDevolutionByCode(int codigo) {
        Devoluciones devolucion = new Devoluciones();
        try {
            String SQL = "SELECT * FROM devoluciones WHERE codigo_compra = '" + codigo + "' ";
            PreparedStatement ps = connection.prepareCall(SQL);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return devolucion;
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }   
    }
    
    public Devoluciones getDevolutionByCodeAndLibro(int codigo, int libro) {
        Devoluciones devolucion = new Devoluciones();
        try {
            String SQL = "SELECT * FROM devoluciones WHERE codigo_compra = '" + codigo + "' AND id_libro = '" + libro +"' ";
            PreparedStatement ps = connection.prepareCall(SQL);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return devolucion;
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }   
    }
    
    public boolean deleteDetail(int id_detalle) {
        String SQL = "DELETE FROM detalle_venta WHERE id_detalleVenta = '" + id_detalle + "'";
        try {
            PreparedStatement ps = connection.prepareCall(SQL);
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println("Error DAO_Devoluciones deleteDetail : " + e);
            return false;
        }
    }
    
    public boolean updateCantBookOfVent(int cantidad, int codigo) {
        try {
            String SQL = "UPDATE venta SET cantidad_libros = '"
                    + cantidad+ "' where codigo_compra = '" + codigo + "'";
            
            System.out.println(SQL);
            PreparedStatement ps = connection.prepareCall(SQL);
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println("Error DAO_Devoluciones updateCantBookOfVent: " + e);
            return false;
        }
    }
    
    
}
