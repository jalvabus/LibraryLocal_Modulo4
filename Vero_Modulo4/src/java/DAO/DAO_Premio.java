/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Connection.DB_Manager;
import Model.Premio;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author Jose Luis Gonzalez
 */
public class DAO_Premio {
    
    DB_Manager db_manager;
    Connection connection;
    
    public DAO_Premio() {
        db_manager = new DB_Manager();
        connection = db_manager.getConnection();
    }
    
    public boolean register (Premio premio) {
        try {
            String SQL = "INSERT INTO premio VALUES (null, '"
                    + premio.getNombre() + "', "
                    + premio.getPuntos() + ", 'Activo', '"
                    + premio.getCantidad() + "', '"
                    + premio.getDescripcion() + "', '"
                    + premio.getFotopremio() + "');";
            
            System.out.println(SQL);
            PreparedStatement ps = connection.prepareCall(SQL);
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println("Error DAO_Premio register : " + e);
            return false;
        }
    }
    
    public boolean update(Premio premio) {
        try {
            String SQL = "UPDATE premio SET nombre = '"
                    + premio.getNombre() + "', puntos = "
                    + premio.getPuntos() + ", status = '"
                    + premio.getStatus() + "', cantidad = "
                    + premio.getCantidad() + ", descripcion = '"
                    + premio.getDescripcion() + "', fotopremio = '"
                    + premio.getFotopremio() + "' where id_premio = '"
                    + premio.getId_premio() + "';";

            System.out.println(SQL);
            PreparedStatement ps = connection.prepareCall(SQL);
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println("Error DAO_Premio update : " + e);
            return false;
        }
    }
    
    public boolean changeStatus(Premio premio) {
        try {
            String SQL = "";
            String status = premio.getStatus();
            if (status.equalsIgnoreCase("Inactivo")) {
                SQL = "UPDATE premio SET status = '"
                    + "Activo" + "' where id_premio = '" + premio.getId_premio() + "'";
            } else {
                SQL = "UPDATE premio SET status = '"
                    + "Inactivo" + "' where id_premio = '" + premio.getId_premio() + "'";
            }
            System.out.println(SQL);
            PreparedStatement ps = connection.prepareCall(SQL);
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println("Error DAO_Premio changeStatus: " + e);
            return false;
        }
    }
    
    public boolean changeCantidad(Premio premio) {
        try {
            String SQL = "UPDATE premio SET cantidad = '"
                    + premio.getCantidad() + "' where id_premio = '" + premio.getId_premio() + "'";
            
            System.out.println(SQL);
            PreparedStatement ps = connection.prepareCall(SQL);
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println("Error DAO_Premio changeCantidad: " + e);
            return false;
        }
    }
    
    public ArrayList<Premio> getAll() {
        ArrayList<Premio> premios = new ArrayList<>();
        try {
            String SQL = "SELECT * FROM premio";
            PreparedStatement ps = connection.prepareCall(SQL);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Premio premio = new Premio();
                premio.setId_premio(rs.getInt(1));
                premio.setNombre(rs.getString(2));
                premio.setPuntos(rs.getInt(3));
                premio.setStatus(rs.getString(4));
                premio.setCantidad(rs.getInt(5));
                premio.setDescripcion(rs.getString(6));
                premio.setFotopremio(rs.getString(7));
                premios.add(premio);
            }
        } catch (Exception e) {
            System.out.println("Error DAO_Premio getAll: " + e);
        }
        return premios;
    }
    
    public Premio getOneByName(String nombre) {
        Premio premio = new Premio();
        try {
            String SQL = "SELECT * FROM premio WHERE nombre = '" + nombre + "' ";
            PreparedStatement ps = connection.prepareCall(SQL);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                premio.setId_premio(rs.getInt(1));
                premio.setNombre(rs.getString(2));
                premio.setPuntos(rs.getInt(3));
                premio.setStatus(rs.getString(4));
                premio.setCantidad(rs.getInt(5));
                premio.setDescripcion(rs.getString(6));
                premio.setFotopremio(rs.getString(7));
                return premio;
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }   
    }
     
    public ArrayList<Premio> getAllByStatus() {
        ArrayList<Premio> premios = new ArrayList<>();
        try {
            String SQL = "SELECT * FROM premio WHERE status = '" + "Activo" + "' ";
            PreparedStatement ps = connection.prepareCall(SQL);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Premio premio = new Premio();
                premio.setId_premio(rs.getInt(1));
                premio.setNombre(rs.getString(2));
                premio.setPuntos(rs.getInt(3));
                premio.setStatus(rs.getString(4));
                premio.setCantidad(rs.getInt(5));
                premio.setDescripcion(rs.getString(6));
                premio.setFotopremio(rs.getString(7));
                premios.add(premio);
            }
        } catch (Exception e) {
            System.out.println("Error DAO_Premio getAllByStatus: " + e);
        }
        return premios;
    }
    
    public int getCantByPrize(int id) {
        //Premio premio = new Premio();
        int cantidad = 0;
        try {
            String SQL = "SELECT * FROM premio WHERE id_premio = '" + id + "' ";
            PreparedStatement ps = connection.prepareCall(SQL);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                cantidad = rs.getInt(5);
                
            }
        } catch (Exception e) {
            System.out.println("Error DAO_Premio getCantByPrize: " + e);
        }   
        return cantidad;
    }
    
    public int getIdUser(String correo) {
        int id = 0;
        try {
            String SQL = "SELECT * FROM usuario WHERE correo = '" + correo + "' ";
            PreparedStatement ps = connection.prepareCall(SQL);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                id = rs.getInt(1);
            }
        } catch (Exception e) {
            System.out.println("Error DAO_Premio getIdUser: " + e);
        }   
        return id;
    }
    
    public int getPuntos(int id) {
        //Premio premio = new Premio();
        int puntos = 0;
        try {
            String SQL = "SELECT * FROM tarjeta_prepago WHERE id_usuario = '" + id + "' ";
            PreparedStatement ps = connection.prepareCall(SQL);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                puntos = rs.getInt(6);
            }
        } catch (Exception e) {
            System.out.println("Error DAO_Premio getPuntos: " + e);
        }   
        return puntos;
    }
    
    public boolean updatePuntos(int puntos, int id) {
        try {
            String SQL = "UPDATE tarjeta_prepago SET puntos = '"
                    + puntos+ "' where id_usuario = '" + id + "'";
            
            System.out.println(SQL);
            PreparedStatement ps = connection.prepareCall(SQL);
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println("Error DAO_Premio updatePuntos: " + e);
            return false;
        }
    }
    
    public boolean registerCanjear (int idPremio,int idUsuario, String fecha) {
        try {
            String SQL = "INSERT INTO canjear_premio VALUES (null, '"
                    + idPremio + "', "
                    + idUsuario + ", '"
                    + fecha + "');";
            
            System.out.println(SQL);
            PreparedStatement ps = connection.prepareCall(SQL);
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println("Error DAO_Premio registerCanjear : " + e);
            return false;
        }
    }
     
    public ArrayList<Premio> getMyPrize(int id) {
        ArrayList<Premio> premios = new ArrayList<>();
        try {
            String SQL = "SELECT fecha_canjear,(SELECT nombre FROM premio p WHERE p.id_premio=c.id_premio) AS nombre,"
                    + "(SELECT descripcion FROM premio p WHERE p.id_premio=c.id_premio) AS descripcion,"
                    + "(SELECT fotopremio FROM premio p WHERE p.id_premio=c.id_premio) AS foto FROM canjear_premio c "
                    + "WHERE id_usuario = '" + id + "' ";
            PreparedStatement ps = connection.prepareCall(SQL);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Premio premio = new Premio();
                premio.setFecha(rs.getString(1));
                premio.setNombre(rs.getString(2));
                premio.setDescripcion(rs.getString(3));
                premio.setFotopremio(rs.getString(4));
                premios.add(premio);
            }
        } catch (Exception e) {
            System.out.println("Error DAO_Premio getMyPrize: " + e);
        }
        return premios;
    }
    
    public String getNomUser(int id) {
        String nombre = "";
        try {
            String SQL = "SELECT * FROM usuario WHERE id_usuario = '" + id + "' ";
            PreparedStatement ps = connection.prepareCall(SQL);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                nombre = rs.getString(3);
            }
        } catch (Exception e) {
            System.out.println("Error DAO_Premio getInfUser: " + e);
        }
        return nombre;
    }
    
    public int getMonto(int codigo) {
        int monto = 0;
        try {
            String SQL = "SELECT * FROM venta WHERE codigo_compra = '" + codigo + "' ";
            PreparedStatement ps = connection.prepareCall(SQL);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                monto = rs.getInt("monto");
            }
        } catch (Exception e) {
            System.out.println("Error DAO_Premio getMonto: " + e);
        }
        return monto;
    }
    
    public boolean registerTicket (int codigo, int monto, int puntos) {
        try {
            String SQL = "INSERT INTO registrar_ticket VALUES ("+ codigo +", '"
                    + "Registrado" + "', "
                    + monto + ", "
                    + puntos + ");";
            
            System.out.println(SQL);
            PreparedStatement ps = connection.prepareCall(SQL);
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println("Error DAO_Premio registerTicket : " + e);
            return false;
        }
    }
    
    public Premio getTicketByCode(int codigo) {
        Premio premio = new Premio();
        try {
            String SQL = "SELECT * FROM registrar_ticket WHERE codigo_compra = '" + codigo + "' ";
            PreparedStatement ps = connection.prepareCall(SQL);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return premio;
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }   
    }

    public Premio getVentaByCode(int codigo) {
        Premio premio = new Premio();
        try {
            String SQL = "SELECT * FROM venta WHERE codigo_compra = '" + codigo + "' ";
            PreparedStatement ps = connection.prepareCall(SQL);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return premio;
            } else {
                return null;
            }
        } catch (Exception e) {
            System.out.println("Error getVentaByCode getMonto: " + e);
            return null;
        }
    }
    
}