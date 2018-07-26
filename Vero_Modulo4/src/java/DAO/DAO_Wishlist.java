/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Connection.DB_Manager;
import Model.Libro;
import Model.Sharedwishlist;
import Model.Usuario;
import java.sql.CallableStatement;
import java.sql.Connection;
import Model.Wishlist;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author Eduardo
 */
public class DAO_Wishlist {

    DB_Manager db_manager;
    Connection connection;

    public DAO_Wishlist() {
        db_manager = new DB_Manager();
        connection = db_manager.getConnection();
    }

    public boolean addToWishlist(Wishlist wl) {
        final String SQL = "INSERT INTO wishlist VALUES(null,'" + wl.getId_usuario() + "','" + wl.getId_libro() + "','" + wl.getEstado() + "')";
        System.out.println(SQL);
        try {
            CallableStatement cs = connection.prepareCall(SQL);
            cs.execute();
        } catch (Exception e) {
            System.err.print("Error al añadir a la lista de deseos");
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean removeToWishList(Wishlist wl) {
        final String SQL = "delete from wishlist where id_usuario = '" + wl.getId_usuario() + "' and id_libro = '" + wl.getId_libro() + "';";
        System.out.println(SQL);
        try {
            CallableStatement cs = connection.prepareCall(SQL);
            cs.execute();
        } catch (Exception e) {
            System.err.print("Error al remover a la lista de deseos " + e);
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public ArrayList<Wishlist> getWishlist(int id_usuario) {
        ArrayList<Wishlist> list = new ArrayList<>();
        try {
            String SQL = "select * from wishlist where id_usuario = " + id_usuario;
            PreparedStatement ps = connection.prepareCall(SQL);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Wishlist wl = new Wishlist();
                wl.setId(rs.getInt(1));

                wl.setId_usuario(rs.getInt(2));
                DAO_Usuario dao_usuario = new DAO_Usuario();
                Usuario usuario = dao_usuario.getOneById(rs.getInt(2));
                wl.setUsuario(usuario);

                wl.setId_libro(rs.getInt(3));
                DAO_Libro dao_libro = new DAO_Libro();
                Libro libro = dao_libro.getOneById(rs.getInt(3));
                wl.setLibro(libro);

                wl.setEstado(rs.getString(4));
                list.add(wl);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public Wishlist getWishlistById(int id) {
        Wishlist wl = null;
        try {
            String SQL = "select * from wishlist where id = " + id;
            PreparedStatement ps = connection.prepareCall(SQL);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                wl = new Wishlist();
                wl.setId(rs.getInt(1));

                wl.setId_usuario(rs.getInt(2));
                DAO_Usuario dao_usuario = new DAO_Usuario();
                Usuario usuario = dao_usuario.getOneById(rs.getInt(2));
                wl.setUsuario(usuario);

                wl.setId_libro(rs.getInt(3));
                DAO_Libro dao_libro = new DAO_Libro();
                Libro libro = dao_libro.getOneById(rs.getInt(3));
                wl.setLibro(libro);

                wl.setEstado(rs.getString(4));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return wl;
    }

    /**
     * Shared Wish List
     */
    public boolean shareWishlist(ArrayList<Wishlist> wishlist, int id_usuario_shared) {
        for (Wishlist wl : wishlist) {
            final String SQL = "INSERT INTO sharedwishlist VALUES(null,'" + wl.getId() + "', '" + wl.getId_usuario() + "', '" + id_usuario_shared + "')";
            System.out.println(SQL);
            try {
                CallableStatement cs = connection.prepareCall(SQL);
                cs.execute();
            } catch (Exception e) {
                System.err.print("Error al compartir la lista de deseos");
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    public ArrayList<Sharedwishlist> getSharedWishlist(int id_usuario_shared) {
        ArrayList<Sharedwishlist> list = new ArrayList<>();
        try {
            String SQL = "select * from sharedwishlist where id_usuario_shared = " + id_usuario_shared + " group by id_usuario";
            System.out.println(SQL);
            PreparedStatement ps = connection.prepareCall(SQL);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Sharedwishlist swl = new Sharedwishlist();
                swl.setId(rs.getInt(1));

                swl.setId_wishlist(rs.getInt(2));
                swl.setWishlit(getWishlistById(rs.getInt(2)));

                swl.setId_usuario(rs.getInt(3));
                DAO_Usuario dao_usuario = new DAO_Usuario();
                Usuario usuario = dao_usuario.getOneById(rs.getInt(3));
                swl.setUsuario(usuario);

                swl.setId_usuario_shared(rs.getInt(4));
                Usuario usuario_shared = dao_usuario.getOneById(rs.getInt(4));
                swl.setUsuario_shared(usuario_shared);

                list.add(swl);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

}
