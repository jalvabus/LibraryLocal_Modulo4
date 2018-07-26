package DAO;

import Connection.DB_Manager;
import Model.Bitacora_tickets;
import Model.Ticket;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class DAO_Ticket {
    DB_Manager db_manager;
    Connection connection;

    public DAO_Ticket() {
        db_manager = new DB_Manager();
        connection = db_manager.getConnection();
    }
    
    public ArrayList<Bitacora_tickets> createByBitacora (ArrayList<Bitacora_tickets> bitacora){
        
        for(Bitacora_tickets ticket: bitacora){
            if(ticket.isStatus()){
                Ticket t = new Ticket();
                t.setStatus(ticket.getEstado());
                t.setMonto(ticket.getMonto());
                if(create(t)){
                    ticket.setStatus(true);
                }else{
                    ticket.setStatus(false);
                    ticket.setMensaje("Error al insertar ticket");
                }
            }
        }
        return bitacora;
    }
    
    public boolean create(Ticket t){
        final String SQL = "INSERT INTO ticket VALUES(null,'"+t.getStatus()+"','"+t.getMonto()+"');";
        System.out.println(SQL);
        try {
            CallableStatement cs = connection.prepareCall(SQL);
            cs.execute();
        } catch (Exception e) {
            System.err.print("Error al añadir ticket");
            e.printStackTrace();
            return false;
        }
        return true;
    }
    
    public ArrayList<Ticket> getAll() {
        ArrayList<Ticket> list = new ArrayList<>();
        try {
            String SQL = "select * from ticket";
            System.out.println(SQL);
            PreparedStatement ps = connection.prepareCall(SQL);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
               Ticket t = new Ticket();
               t.setId_ticket(rs.getInt(1));
               t.setStatus(rs.getString(2));
               t.setMonto(rs.getInt(3));
               list.add(t);
            }
        } catch (Exception e) {
           e.printStackTrace();
        }
        return list;
    }
}
