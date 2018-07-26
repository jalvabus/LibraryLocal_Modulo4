/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Connection.DB_Manager;
import Model.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.CallableStatement;

/**
 *
 * @author juana
 */
public class DAO_Usuario {

    DB_Manager db_manager;
    Connection connection;

    public DAO_Usuario() {
        db_manager = new DB_Manager();
        connection = db_manager.getConnection();
    }

    public Usuario login(String nombre, String pss) {
        Usuario usuario = new Usuario();
        String SQL = "select * from usuario where correo = '" + nombre + "' and password = '" + pss + "'";
        System.out.println(SQL);
        try {
            PreparedStatement ps = connection.prepareCall(SQL);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                usuario.setId_Usuario(rs.getInt(1));
                usuario.setCorreo(rs.getString(2));
                usuario.setNombre(rs.getString(3));
                usuario.setAmaterno(rs.getString(5));
                usuario.setApaterno(rs.getString(4));
                usuario.setEdad(rs.getInt(6));
                usuario.setSexo(rs.getString(7));
                usuario.setTelefono(rs.getString(8));
                usuario.setCalle(rs.getString(9));
                usuario.setColonia(rs.getString(10));
                usuario.setMunicipio(rs.getString(11));
                usuario.setEstado(rs.getString(12));
                usuario.setTipo(rs.getString(13));
                usuario.setPassword(rs.getString(14));
                return usuario;
            } else {
                System.out.println("NO Encontrado");
                return null;
            }
        } catch (Exception e) {
            System.out.println("Error $Usuarios > login : " + e);
            return null;
        }
    }

    /*
    * MODULO 3
     */
    public Usuario getOneByCorreo(String correo) {
        Usuario usuario = null;
        String SQL = "select * from usuario where correo = '" + correo + "'";
        System.out.println(SQL);
        try {
            PreparedStatement ps = connection.prepareCall(SQL);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                usuario = new Usuario();
                usuario.setId_Usuario(rs.getInt(1));
                usuario.setCorreo(rs.getString(2));
                usuario.setNombre(rs.getString(3));
                usuario.setAmaterno(rs.getString(5));
                usuario.setApaterno(rs.getString(4));
                usuario.setEdad(rs.getInt(6));
                usuario.setSexo(rs.getString(7));
                usuario.setTelefono(rs.getString(8));
                usuario.setCalle(rs.getString(9));
                usuario.setColonia(rs.getString(10));
                usuario.setMunicipio(rs.getString(11));
                usuario.setEstado(rs.getString(12));
                usuario.setTipo(rs.getString(13));
                usuario.setPassword(rs.getString(14));
                return usuario;
            } else {
                System.out.println("NO Encontrado");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return usuario;
    }

    public Usuario getOneById(int id_usuario) {
        Usuario usuario = new Usuario();
        String SQL = "select * from usuario where id_usuario = " + id_usuario;
        System.out.println(SQL);
        try {
            PreparedStatement ps = connection.prepareCall(SQL);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                usuario.setId_Usuario(rs.getInt(1));
                usuario.setCorreo(rs.getString(2));
                usuario.setNombre(rs.getString(3));
                usuario.setAmaterno(rs.getString(5));
                usuario.setApaterno(rs.getString(4));
                usuario.setEdad(rs.getInt(6));
                usuario.setSexo(rs.getString(7));
                usuario.setTelefono(rs.getString(8));
                usuario.setCalle(rs.getString(9));
                usuario.setColonia(rs.getString(10));
                usuario.setMunicipio(rs.getString(11));
                usuario.setEstado(rs.getString(12));
                usuario.setTipo(rs.getString(13));
                usuario.setPassword(rs.getString(14));
                return usuario;
            } else {
                System.out.println("NO Encontrado");
                return null;
            }
        } catch (Exception e) {
            System.out.println("Error $Usuarios > login : " + e);
            return null;
        }
    }

    /*
    * Metodo generico para insertar un usuario
     */
    public boolean createRegistro(Usuario u) {
        final String SQL = "INSERT INTO registro VALUES('" + u.getCorreo() + "','" + u.getPassword() + "','" + u.getCorreo() + "','" + u.getTipo() + "','000','Confirmado');";
        System.out.println(SQL);
        try {
            CallableStatement cs = connection.prepareCall(SQL);
            cs.execute();
        } catch (Exception e) {
            System.err.print("Error al añadir usuario en registro");
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean createUsuario(Usuario u) {
        if (createRegistro(u)) {
            final String SQL = "INSERT INTO usuario VALUES(null,'" + u.getCorreo() + "','" + u.getNombre() + "','" + u.getApaterno() + "','" + u.getAmaterno() + "','" + u.getEdad() + "','" + u.getSexo() + "','" + u.getTelefono() + "',"
                    + "'" + u.getCalle() + "','" + u.getColonia() + "','" + u.getMunicipio() + "','" + u.getEstado() + "','" + u.getTipo() + "','" + u.getPassword() + "')";
            System.out.println(SQL);
            try {
                CallableStatement cs = connection.prepareCall(SQL);
                cs.execute();
            } catch (Exception e) {
                System.err.print("Error al añadir usuario");
                e.printStackTrace();
                return false;
            }
            return true;
        } else {
            return false;
        }

    }

    /*
    * Metodo generico para modificar un usuario
     */
    public boolean update(Usuario u) {
        final String SQL = "UPDATE usuario SET correo = '" + u.getCorreo() + "', nombre = '" + u.getNombre() + "', apaterno = '" + u.getApaterno() + "', amaterno = '" + u.getAmaterno() + "', edad = '" + u.getEdad() + "', sexo = '" + u.getSexo() + "', telefono = '" + u.getTelefono() + "',"
                + "calle = '" + u.getCalle() + "', colonia = '" + u.getColonia() + "', municipio = '" + u.getMunicipio() + "', estado = '" + u.getEstado() + "', tipo = '" + u.getTipo() + "' WHERE id_usuario = '" + u.getId_Usuario() + "'";
        System.out.println(SQL);
        try {
            CallableStatement cs = connection.prepareCall(SQL);
            cs.execute();
        } catch (Exception e) {
            System.err.print("Error al modificar usuario");
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /*
    * Metodo para recuperar la contrasenia de un usuario
     */
    public String getPassword(String correo) {
        Usuario usuario = new Usuario();
        String SQL = "SELECT * from usuario where correo = '" + correo + "'";
        System.out.println(SQL);
        try {
            PreparedStatement ps = connection.prepareCall(SQL);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                usuario.setPassword(rs.getString(14));
                return usuario.getPassword();
            } else {
                System.out.println("NO Encontrado");
                return "";
            }
        } catch (Exception e) {
            System.out.println("Error al recuperar la contrasenia de un usuario" + e);
            return "";
        }
    }
}
