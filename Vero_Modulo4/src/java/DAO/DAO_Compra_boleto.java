/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Connection.DB_Manager;
import Model.Compra_boleto;
import Model.Evento;
import Model.Usuario;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author Eduardo
 */
public class DAO_Compra_boleto {
    DB_Manager db_manager;
    Connection connection;

    public DAO_Compra_boleto() {
        db_manager = new DB_Manager();
        connection = db_manager.getConnection();
    }
    
    public boolean deletePago(int folio){
        final String SQL = "DELETE FROM compra_boleto WHERE folio = " + folio;
        System.out.println(SQL);
        try {
            CallableStatement cs = connection.prepareCall(SQL);
            cs.execute();
        } catch (Exception e) {
            System.err.print("Error al eliminar a el pago");
            e.printStackTrace();
            return false;
        }
        return true;
    }
    
    public ArrayList<Compra_boleto> getPagosRestantesByUsuario(int id_usuario){
        ArrayList<Compra_boleto> list = new ArrayList<>();
        try {
            String SQL = "SELECT * FROM compra_boleto WHERE id_usuario = '"+id_usuario+"' AND restante > 0 ";
            System.out.println(SQL);
            PreparedStatement ps = connection.prepareCall(SQL);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Compra_boleto cp = new Compra_boleto();
                cp.setFolio(rs.getInt(1));
                cp.setCantidad_boletos(rs.getInt(2));
                cp.setCantidad_pagos(rs.getInt(3));
                cp.setCosto_total(rs.getDouble(4));
                cp.setRestante(rs.getDouble(5));
                cp.setStatus(rs.getString(6));
                cp.setFecha_compra(rs.getString(7));
                
                cp.setId_evento(rs.getInt(8));
                DAO_Evento dao_evento = new DAO_Evento();
                Evento evento = dao_evento.getOne(rs.getInt(8));
                cp.setEvento(evento);
                
                cp.setId_usuario(id_usuario);
                DAO_Usuario dao_usuario = new DAO_Usuario();
                Usuario usuario = dao_usuario.getOneById(id_usuario);
                cp.setUsuario(usuario);
                
                list.add(cp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public Compra_boleto getPagoRestantesByFolio(int folio){
        Compra_boleto cp = null;
        try {
            String SQL = "SELECT * FROM compra_boleto WHERE folio = '"+folio+"'";
            System.out.println(SQL);
            PreparedStatement ps = connection.prepareCall(SQL);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                cp = new Compra_boleto();
                cp.setFolio(rs.getInt(1));
                cp.setCantidad_boletos(rs.getInt(2));
                cp.setCantidad_pagos(rs.getInt(3));
                cp.setCosto_total(rs.getDouble(4));
                cp.setRestante(rs.getDouble(5));
                cp.setStatus(rs.getString(6));
                cp.setFecha_compra(rs.getString(7));
                
                cp.setId_evento(rs.getInt(8));
                DAO_Evento dao_evento = new DAO_Evento();
                Evento evento = dao_evento.getOne(rs.getInt(8));
                cp.setEvento(evento);
                
                cp.setId_usuario(rs.getInt(9));
                DAO_Usuario dao_usuario = new DAO_Usuario();
                Usuario usuario = dao_usuario.getOneById(rs.getInt(9));
                cp.setUsuario(usuario);
                

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cp;
    }
}
