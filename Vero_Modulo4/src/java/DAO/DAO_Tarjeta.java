/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Connection.DB_Manager;
import Model.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author José Luis González Ortega
 */
public class DAO_Tarjeta {

    DB_Manager db_manager;
    Connection connection;

    public DAO_Tarjeta() {
        db_manager = new DB_Manager();
        connection = db_manager.getConnection();
    }

    public ArrayList<Tarjeta> getAllByStatus() {
        ArrayList<Tarjeta> tarjetas = new ArrayList<>();
        try {
            String SQL = "SELECT * FROM tarjeta WHERE status = 'Disponible' AND DATE(vigencia)>=CURDATE()";
            PreparedStatement ps = connection.prepareCall(SQL);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Tarjeta tarjeta = new Tarjeta();
                tarjeta.setIdTarjeta(rs.getInt(1));
                tarjeta.setCosto(rs.getFloat(2));
                tarjeta.setNoTarjeta(rs.getString(3));
                tarjeta.setStatus(rs.getString(4));
                tarjeta.setIdUsuario(rs.getInt(5));
                tarjeta.setVigencia(rs.getString(6));
                tarjetas.add(tarjeta);
            }
        } catch (Exception e) {
            System.out.println("Error DAO_Tarjeta getAllByStatus: " + e);
        }
        return tarjetas;
    }

    public boolean updateTarjeta(int id, Tarjeta tarjeta) {
        try {
            String SQL = "UPDATE tarjeta SET status = 'Ocupado', idUsuario = "
                    + id + " where idTarjeta = '"
                    + tarjeta.getIdTarjeta() + "';";

            System.out.println(SQL);
            PreparedStatement ps = connection.prepareCall(SQL);
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println("Error DAO_Tarjeta updateTarjeta: " + e);
            return false;
        }
    }

    public boolean updateSaldoCredito(float saldo, int id) {
        try {
            String SQL = "UPDATE tarjeta_credito SET saldo = '"
                    + saldo + "' where id_usuario = '" + id + "'";

            System.out.println(SQL);
            PreparedStatement ps = connection.prepareCall(SQL);
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println("Error DAO_Tarjeta updateSaldoCredito: " + e);
            return false;
        }
    }
    
    public boolean updateSaldoPrepago(float saldo, int id) {
        try {
            String SQL = "UPDATE tarjeta_prepago SET saldo = '"
                    + saldo + "' where id_usuario = '" + id + "'";

            System.out.println(SQL);
            PreparedStatement ps = connection.prepareCall(SQL);
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println("Error DAO_Tarjeta updateSaldoPrepago: " + e);
            return false;
        }
    }

    public boolean registerTarjeta(Tarjeta_prepago tarjeta) {
        try {
            String SQL = "INSERT INTO tarjeta_prepago VALUES (" + tarjeta.getCodigo_tarjeta() + ",'"
                    + "Activada" + "', "
                    + tarjeta.getId_usuario() + ", '"
                    + tarjeta.getSaldo() + "', "
                    + "null" + ", "
                    + "null" + ");";

            System.out.println(SQL);
            PreparedStatement ps = connection.prepareCall(SQL);
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println("Error DAO_Tarjeta registerTarjeta : " + e);
            return false;
        }
    }
    
    public Tarjeta_prepago getTarjetaPrepagoExist(int idUser) {
        Tarjeta_prepago tarjeta = new Tarjeta_prepago();
        try {
            String SQL = "SELECT * FROM tarjeta_prepago WHERE id_usuario = '" + idUser + "' ";
            PreparedStatement ps = connection.prepareCall(SQL);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return tarjeta;
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }   
    }

    public ArrayList<Tarjeta_prepago> getTarjetaPrepago(int idUser) {
        ArrayList<Tarjeta_prepago> tarjetas = new ArrayList<>();
        try {
            String SQL = "SELECT * FROM tarjeta_prepago WHERE id_usuario = '" + idUser + "' ";
            PreparedStatement ps = connection.prepareCall(SQL);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Tarjeta_prepago tarjeta = new Tarjeta_prepago();
                tarjeta.setCodigo_tarjeta(rs.getString(1));
                tarjeta.setEstado(rs.getString(2));
                tarjeta.setId_usuario(rs.getInt(3));
                tarjeta.setSaldo(rs.getFloat(4));
                tarjetas.add(tarjeta);

            }
        } catch (Exception e) {
            System.out.println("Error DAO_Tarjeta getTarjetaPrepago: " + e);
        }
        return tarjetas;
    }

    public ArrayList<Tarjeta_credito> getTarjetaCredito(int idUser) {
        ArrayList<Tarjeta_credito> tarjetas = new ArrayList<>();
        try {
            String SQL = "SELECT * FROM tarjeta_credito WHERE id_usuario = '" + idUser + "' ";
            PreparedStatement ps = connection.prepareCall(SQL);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Tarjeta_credito tarjeta = new Tarjeta_credito();
                tarjeta.setCodigo_tarjetacredito(rs.getString(1));
                tarjeta.setEstado(rs.getString(2));
                tarjeta.setId_usuario(rs.getInt(3));
                tarjeta.setSaldo(rs.getFloat(4));
                tarjetas.add(tarjeta);
            }
        } catch (Exception e) {
            System.out.println("Error DAO_Tarjeta getTarjetaCredito: " + e);
        }
        return tarjetas;
    }
    
    public Usuario getCorreoExist(String correo) {
        Usuario usuario = new Usuario();
        try {
            String SQL = "SELECT * FROM usuario WHERE correo = '" + correo + "' ";
            PreparedStatement ps = connection.prepareCall(SQL);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return usuario;
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }   
    }

    /*
    MODULO 3
     */
    public ArrayList<Bitacora_tarjeta> createByBitacora(ArrayList<Bitacora_tarjeta> listBitacora) {

        for (Bitacora_tarjeta bitacora : listBitacora) {
            if (bitacora.isStatus()) {

                if (create(bitacora.getTarjeta())) {
                    bitacora.setStatus(true);
                } else {
                    bitacora.setStatus(false);
                }

            }
        }

        return listBitacora;
    }

    public ArrayList<Tarjeta> getAll() {
        ArrayList<Tarjeta> tarjetas = new ArrayList<>();
        try {
            String SQL = "SELECT * FROM tarjeta";
            PreparedStatement ps = connection.prepareCall(SQL);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Tarjeta tarjeta = new Tarjeta();
                tarjeta.setIdTarjeta(rs.getInt(1));
                tarjeta.setCosto(rs.getFloat(2));
                tarjeta.setNoTarjeta(rs.getString(3));
                tarjeta.setStatus(rs.getString(4));
                tarjeta.setIdUsuario(rs.getInt(5));
                tarjeta.setVigencia(rs.getString(6));
                tarjetas.add(tarjeta);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tarjetas;
    }

    //| idTarjeta | costos | noTarjeta | status  | puntos | idUsuario | vigencia
    public boolean create(Tarjeta t) {
        try {
            String SQL = "INSERT INTO tarjeta VALUES(null,'" + t.getCosto() + "','" + t.getNoTarjeta() + "','" + t.getStatus() + "', '0','" + t.getVigencia() + "')";
            System.out.println(SQL);
            PreparedStatement ps = connection.prepareCall(SQL);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

}
