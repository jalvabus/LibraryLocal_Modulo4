/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Connection.DB_Manager;
import Model.Tarjeta_credito;
import Model.Tarjeta_prepago;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author juana
 */
public class DAO_TarjetaPrepago {
    
    DB_Manager db_manager;
    Connection connection;
    
    public DAO_TarjetaPrepago() {
        db_manager = new DB_Manager();
        connection = db_manager.getConnection();
    }
    
    public Tarjeta_prepago getTarjeta(String codigo, String usuario) {
        Tarjeta_prepago tarjeta = new Tarjeta_prepago();
        try {
            String SQL = "select * from tarjeta_prepago where codigo_tarjeta = '" + codigo + "' and id_usuario = '" + usuario + "';";
            System.out.println(SQL);
            PreparedStatement ps = connection.prepareCall(SQL);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                tarjeta.setCodigo_tarjeta(rs.getString(1));
                tarjeta.setEstado(rs.getString(2));
                tarjeta.setId_usuario(rs.getInt(3));
                tarjeta.setSaldo(rs.getFloat(4));
                tarjeta.setSaldo_prepago(rs.getFloat(5));
                tarjeta.setPuntos(rs.getInt(6));
            }
        } catch (SQLException e) {
            System.out.println("Error DAO_TarjetaPrepago > getTarjeta : " + e);
        }
        return tarjeta;
    }

    /**
     * Modulo 3
     * @param id_usuario
     * @return 
     */
    public Tarjeta_prepago getTarjetaByUsuario(int id_usuario) {
        Tarjeta_prepago tarjeta = null;
        try {
            String SQL = "select * from tarjeta_prepago where id_usuario = '" + id_usuario + "';";
            System.out.println(SQL);
            PreparedStatement ps = connection.prepareCall(SQL);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                tarjeta = new Tarjeta_prepago();
                tarjeta.setCodigo_tarjeta(rs.getString(1));
                tarjeta.setEstado(rs.getString(2));
                tarjeta.setId_usuario(rs.getInt(3));
                tarjeta.setSaldo(rs.getFloat(4));
                tarjeta.setSaldo_prepago(rs.getFloat(5));
                tarjeta.setPuntos(rs.getInt(6));
            }
        } catch (SQLException e) {
            System.out.println("Error DAO_TarjetaPrepago > getTarjeta : " + e);
        }
        return tarjeta;
    }
    
    public boolean updateSaldo(double saldo, String codigo_tarjeta){
        final String SQL = "UPDATE tarjeta_prepago SET saldo = '"+saldo+"' WHERE codigo_tarjeta = '"+codigo_tarjeta+"'";
        System.out.println(SQL);
        try {
            CallableStatement cs = connection.prepareCall(SQL);
            cs.execute();
        } catch (Exception e) {
            System.err.print("Error al actualizar a el saldo de la tarjeta de prepago");
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
