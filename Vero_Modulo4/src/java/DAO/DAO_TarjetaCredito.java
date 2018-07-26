/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Connection.DB_Manager;
import Model.Tarjeta_credito;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import Model.*;

/**
 *
 * @author juana
 */
public class DAO_TarjetaCredito {

    DB_Manager db_manager;
    Connection connection;

    public DAO_TarjetaCredito() {
        db_manager = new DB_Manager();
        connection = db_manager.getConnection();
    }

    public Tarjeta_credito getTarjeta(String codigo, String usuario) {
        Tarjeta_credito tarjeta = new Tarjeta_credito();
        try {
            String SQL = "select * from tarjeta_credito where codigo_tarjetacredito = '" + codigo + "' and id_usuario = '" + usuario + "';";
            PreparedStatement ps = connection.prepareCall(SQL);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                tarjeta.setCodigo_tarjetacredito(rs.getString(1));
                tarjeta.setEstado(rs.getString(2));
                tarjeta.setId_usuario(rs.getInt(3));
                tarjeta.setSaldo(rs.getFloat(4));
                tarjeta.setSaldo_credito(rs.getFloat(5));
            }
        } catch (SQLException e) {
            System.out.println("Error DAO_TarjetaCredito > getTarjeta : " + e);
        }
        return tarjeta;
    }

    /**
     * MODULO V3.1
     *
     * @param id_usuario
     */
    public Tarjeta_credito getTarjetaByUsuario(String id_usuario) {
        Tarjeta_credito tarjeta = new Tarjeta_credito();
        try {
            String SQL = "Select * from tarjeta_credito where id_usuario = '" + id_usuario + "'";
            PreparedStatement ps = connection.prepareCall(SQL);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                tarjeta.setCodigo_tarjetacredito(rs.getString(1));
                tarjeta.setEstado(rs.getString(2));
                tarjeta.setId_usuario(rs.getInt(3));
                tarjeta.setSaldo(rs.getFloat(4));
                tarjeta.setSaldo_credito(rs.getFloat(5));
            }
        } catch (Exception e) {
            System.out.println("Error DAO_TarjetaCredito: " + e);
        }
        return tarjeta;
    }

    /**
     * Insertar en Tarjeta de Credito
     * @param noTarjeta
     * @return 
     */
    public boolean insertCredito(String noTarjeta) {
        try {
            String SQL = "CALL insertCredito('" + noTarjeta + "')";
            PreparedStatement ps = connection.prepareCall(SQL);
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println("Error DAO_TarjetaCredito > insertCredito : " + e);
            return false;
        }
    }
}
