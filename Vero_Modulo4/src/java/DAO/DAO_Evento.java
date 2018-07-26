/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Connection.DB_Manager;
import java.sql.Connection;
import Model.Evento;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 *
 * @author juana
 */
public class DAO_Evento {

    DB_Manager db_manager;
    Connection connection;

    public DAO_Evento() {
        db_manager = new DB_Manager();
        connection = db_manager.getConnection();
    }

    public boolean create(Evento evento) {
        try {
            String SQL = "insert into evento values (null, '"
                    + evento.getNombre() + "', '"
                    + evento.getTipo() + "', '"
                    + evento.getCupo() + "', '"
                    + evento.getStatus() + "', '"
                    + evento.getCosto() + "', '"
                    + evento.getDescripcion() + "', now(), '"
                    + evento.getFecha_evento() + "', '"
                    + evento.getFoto() + "', '"
                    + evento.getCalificacion() + "');";
            System.out.println(SQL);
            PreparedStatement ps = connection.prepareCall(SQL);
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println("Error DAO_Evento > create : " + e);
            return false;
        }
    }

    public boolean eventoRepetido(Evento evento) {
        try {
            String SQL = "Select * from evento where nombre = '" + evento.getNombre() + "';";
            PreparedStatement ps = connection.prepareCall(SQL);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (Exception e) {
            System.out.println("Error DAO_Evento > eventoRepetido : " + e);
            return false;
        }
    }

    public ArrayList<Evento> getAll() {
        ArrayList<Evento> eventos = new ArrayList<>();
        try {
            String SQL = "select * from evento where fecha_evento >= (SELECT CURDATE());";
            PreparedStatement ps = connection.prepareCall(SQL);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Evento evento = new Evento();
                evento.setId_evento(rs.getInt(1));
                evento.setNombre(rs.getString(2));
                evento.setTipo(rs.getString(3));
                evento.setCupo(rs.getInt(4));
                evento.setStatus(rs.getString(5));
                evento.setCosto(rs.getFloat(6));
                evento.setDescripcion(rs.getString(7));
                evento.setFecha_registro(rs.getString(8));
                evento.setFecha_evento(rs.getString(9));
                evento.setFoto(rs.getString(10));
                evento.setCalificacion(rs.getFloat(11));
                eventos.add(evento);
            }
        } catch (Exception e) {
            System.out.println("Error DAO_Evento > getAll: " + e);
        }
        return eventos;
    }

    public boolean update(Evento evento) {
        try {
            String SQL = "update evento set nombre = '"
                    + evento.getNombre() + "', tipo = '"
                    + evento.getTipo() + "', cupo = '"
                    + evento.getCupo() + "', status = '"
                    + evento.getStatus() + "', costo = '"
                    + evento.getCosto() + "', descripcion = '"
                    + evento.getDescripcion() + "', fecha_evento = '"
                    + evento.getFecha_evento() + "', foto = '"
                    + evento.getFoto() + "', calificacion = '"
                    + evento.getCalificacion() + "' where id_evento = '"
                    + evento.getId_evento() + "';";

            System.out.println(SQL);
            PreparedStatement ps = connection.prepareCall(SQL);
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println("Error DAO_Evento > update : " + e);
            return false;
        }
    }

    /*
    * Modulo 3
     */
    public Evento getOne(int id) {
        Evento evento = null;
        try {
            String SQL = "select * from evento where id_evento = " + id;
            PreparedStatement ps = connection.prepareCall(SQL);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                evento = new Evento();
                evento.setId_evento(rs.getInt(1));
                evento.setNombre(rs.getString(2));
                evento.setTipo(rs.getString(3));
                evento.setCupo(rs.getInt(4));
                evento.setStatus(rs.getString(5));
                evento.setCosto(rs.getFloat(6));
                evento.setDescripcion(rs.getString(7));
                evento.setFecha_registro(rs.getString(8));
                evento.setFecha_evento(rs.getString(9));
                evento.setFoto(rs.getString(10));
                evento.setCalificacion(rs.getFloat(11));

            }
        } catch (Exception e) {
            System.out.println("Error DAO_Evento > getAll: " + e);
        }
        return evento;
    }

    public boolean updateEventoCupo(int id_evento, int cantidad) {
        try {
            String SQL = "update evento set cupo = '" + cantidad + "' where id_evento = '" + id_evento + "';";
            System.out.println(SQL);
            PreparedStatement ps = connection.prepareCall(SQL);
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println("Error DAO_Evento > updateEventoCupo: " + e);
            return false;
        }
    }

    /**
     * Get mis eventos
     *
     * @param idUsuario
     * @return
     */
    public ArrayList<Evento> getMisEventos(int idUsuario) {
        ArrayList<Evento> eventos = new ArrayList<>();
        try {
            String SQL = "select cp.folio as folios, e.nombre, e.tipo, cp.cantidad_boletos as cantidad, e.status, e.costo, e.descripcion, e.fecha_registro, e.fecha_evento, "
                    + "e.foto,e.calificacion from evento e "
                    + "inner join compra_boleto cp "
                    + "on cp.id_evento = e.id_evento "
                    + "where cp.id_usuario = " + idUsuario + " and e.fecha_evento >= (SELECT CURDATE());";
            System.out.println(SQL);
            PreparedStatement ps = connection.prepareCall(SQL);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Evento evento = new Evento();
                evento.setId_evento(rs.getInt(1));
                evento.setNombre(rs.getString(2));
                evento.setTipo(rs.getString(3));
                evento.setCupo(rs.getInt(4));
                evento.setStatus(rs.getString(5));
                evento.setCosto(rs.getFloat(6));
                evento.setDescripcion(rs.getString(7));
                evento.setFecha_registro(rs.getString(8));
                evento.setFecha_evento(rs.getString(9));
                evento.setFoto(rs.getString(10));
                evento.setCalificacion(rs.getFloat(11));
                eventos.add(evento);
            }
        } catch (SQLException e) {
            System.out.println("Erro DAO_Evento > getMisEventos : " + e);
        }
        return eventos;
    }

    public Evento getOneByName(String name) {
        Evento evento = null;
        try {
            String SQL = "select * from evento where nombre = '" + name + "'";
            System.out.println(SQL);
            PreparedStatement ps = connection.prepareCall(SQL);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                evento = new Evento();
                evento.setId_evento(rs.getInt(1));
                evento.setNombre(rs.getString(2));
                evento.setTipo(rs.getString(3));
                evento.setCupo(rs.getInt(4));
                evento.setStatus(rs.getString(5));
                evento.setCosto(rs.getFloat(6));
                evento.setDescripcion(rs.getString(7));
                evento.setFecha_registro(rs.getString(8));
                evento.setFecha_evento(rs.getString(9));
                evento.setFoto(rs.getString(10));
                evento.setCalificacion(rs.getFloat(11));

            }
        } catch (Exception e) {
            System.out.println("Error DAO_Evento > getAll: " + e);
        }
        return evento;
    }

    public boolean deleteBoletos(int id_boletos) {
        try {
            String SQL = "Delete from compra_boleto where folio = '" + id_boletos + "'";
            System.out.println(SQL);
            PreparedStatement ps = connection.prepareCall(SQL);
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println("Error DAO_Evento > updateEventoCupo: " + e);
            return false;
        }
    }

}
