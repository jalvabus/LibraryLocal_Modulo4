/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Connection.DB_Manager;
import Model.Libro;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author juana
 */
public class DAO_Libro {

    DB_Manager db_manager;
    Connection connection;

    public DAO_Libro() {
        db_manager = new DB_Manager();
        connection = db_manager.getConnection();
    }

    public Libro getOneByName(String nombre) {
        Libro libro = new Libro();
        try {
            String SQL = "select * from libro where nombre = '" + nombre + "' ";
            PreparedStatement ps = connection.prepareCall(SQL);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                libro.setId_libro(rs.getInt(1));
                libro.setNombre(rs.getString(2));
                libro.setPrecio(rs.getFloat(3));
                libro.setAutor(rs.getString(4));
                libro.setEditorial(rs.getString(5));
                libro.setCategoria(rs.getString(6));
                libro.setAno_publicacion(rs.getInt(7));
                libro.setDescripcion(rs.getString(8));
                libro.setStatus(rs.getString(9));
                libro.setCantidad(rs.getInt(10));
                libro.setFoto(rs.getString(11));
                return libro;
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public ArrayList<Libro> getAll() {
        ArrayList<Libro> libros = new ArrayList<>();
        try {
            String SQL = "select * from Libro";
            PreparedStatement ps = connection.prepareCall(SQL);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Libro libro = new Libro();
                libro.setId_libro(rs.getInt(1));
                libro.setNombre(rs.getString(2));
                libro.setPrecio(rs.getFloat(3));
                libro.setAutor(rs.getString(4));
                libro.setEditorial(rs.getString(5));
                libro.setCategoria(rs.getString(6));
                libro.setAno_publicacion(rs.getInt(7));
                libro.setDescripcion(rs.getString(8));
                libro.setStatus(rs.getString(9));
                libro.setCantidad(rs.getInt(10));
                libro.setFoto(rs.getString(11));
                libros.add(libro);
            }
        } catch (Exception e) {
            System.out.println("Error DAO_Libro getAll: " + e);
        }
        return libros;
    }

    public ArrayList<Libro> getComprados(int id_usuario) {
        ArrayList<Libro> libros = new ArrayList<>();
        try {
            String SQL = "select l.id_libro, l.nombre, l.precio, l.autor, l.editorial, l.categoria, l.ano_publicacion, "
                    + "l.descripcion, l.status, l.cantidad, l.foto from libro l "
                    + "inner join detalle_venta dv on l.id_libro = dv.id_libro "
                    + "inner join venta v on v.codigo_compra = dv.codigo_compra "
                    + "where v.id_usuario = '" + id_usuario + "';";
            System.out.println(SQL);
            PreparedStatement ps = connection.prepareCall(SQL);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Libro libro = new Libro();
                libro.setId_libro(rs.getInt(1));
                libro.setNombre(rs.getString(2));
                libro.setPrecio(rs.getFloat(3));
                libro.setAutor(rs.getString(4));
                libro.setEditorial(rs.getString(5));
                libro.setCategoria(rs.getString(6));
                libro.setAno_publicacion(rs.getInt(7));
                libro.setDescripcion(rs.getString(8));
                libro.setStatus(rs.getString(9));
                libro.setCantidad(rs.getInt(10));
                libro.setFoto(rs.getString(11));
                libros.add(libro);
            }
        } catch (Exception e) {
            System.out.println("Error DAO_Libro getComprados: " + e);
        }
        return libros;
    }

    public ArrayList<Libro> busqueda(String nombre) {
        ArrayList<Libro> libros = new ArrayList<>();
        try {
            String SQL = "select * from libro where nombre like '%" + nombre + "%';";
            PreparedStatement ps = connection.prepareCall(SQL);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Libro libro = new Libro();
                libro.setId_libro(rs.getInt(1));
                libro.setNombre(rs.getString(2));
                libro.setPrecio(rs.getFloat(3));
                libro.setAutor(rs.getString(4));
                libro.setEditorial(rs.getString(5));
                libro.setCategoria(rs.getString(6));
                libro.setAno_publicacion(rs.getInt(7));
                libro.setDescripcion(rs.getString(8));
                libro.setStatus(rs.getString(9));
                libro.setCantidad(rs.getInt(10));
                libro.setFoto(rs.getString(11));
                libros.add(libro);
            }
        } catch (Exception e) {
            System.out.println("Error DAO_Libro getAll: " + e);
        }
        return libros;
    }

    public boolean register(Libro libro) {
        try {
            String SQL = "Insert into libro values (null, '"
                    + libro.getNombre() + "', "
                    + libro.getPrecio() + ", '"
                    + libro.getAutor() + "', '"
                    + libro.getEditorial() + "', '"
                    + libro.getCategoria() + "', "
                    + libro.getAno_publicacion() + ", '"
                    + libro.getDescripcion() + "', '"
                    + libro.getStatus() + "', "
                    + libro.getCantidad() + ", '"
                    + libro.getFoto() + "');";
            System.out.println(SQL);
            PreparedStatement ps = connection.prepareCall(SQL);
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println("Error DAO_Libro register : " + e);
            return false;
        }
    }

    public boolean delele(int id_libro) {
        String SQL = "DELETE FROM Libro where id_libro = '" + id_libro + "'";
        try {
            PreparedStatement ps = connection.prepareCall(SQL);
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println("Erro $Usuarios > delete: " + e);
            return false;
        }
    }

    public boolean update(Libro libro) {
        try {

            String SQL = "update libro set nombre = '"
                    + libro.getNombre() + "', precio = "
                    + libro.getPrecio() + ", autor = '"
                    + libro.getAutor() + "', editorial = '"
                    + libro.getEditorial() + "', categoria = '"
                    + libro.getCategoria() + "', ano_publicacion = '"
                    + libro.getAno_publicacion() + "', descripcion = '"
                    + libro.getDescripcion() + "', status = '"
                    + libro.getStatus() + "', cantidad = "
                    + libro.getCantidad() + ", foto = '"
                    + libro.getFoto() + "' where id_libro = '"
                    + libro.getId_libro() + "';";

            System.out.println(SQL);
            PreparedStatement ps = connection.prepareCall(SQL);
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println("Error DAO_Libro update : " + e);
            return false;
        }
    }

    /*
    * Modulo 3
     */
    public Libro getOneById(int id) {
        Libro libro = new Libro();
        try {
            String SQL = "select * from libro where id_libro = '" + id + "' ";
            PreparedStatement ps = connection.prepareCall(SQL);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                libro.setId_libro(rs.getInt(1));
                libro.setNombre(rs.getString(2));
                libro.setPrecio(rs.getFloat(3));
                libro.setAutor(rs.getString(4));
                libro.setEditorial(rs.getString(5));
                libro.setCategoria(rs.getString(6));
                libro.setAno_publicacion(rs.getInt(7));
                libro.setDescripcion(rs.getString(8));
                libro.setStatus(rs.getString(9));
                libro.setCantidad(rs.getInt(10));
                libro.setFoto(rs.getString(11));
                return libro;
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }
}
