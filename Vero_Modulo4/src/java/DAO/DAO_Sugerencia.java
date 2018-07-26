/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Connection.DB_Manager;
import java.sql.*;
import Model.Sugerencia;

/**
 *
 * @author juana
 */
public class DAO_Sugerencia {

    DB_Manager db_manager;
    Connection connection;

    public DAO_Sugerencia() {
        db_manager = new DB_Manager();
        connection = db_manager.getConnection();
    }

    public boolean create(Sugerencia libro) {
        try {
            String SQL = "insert into sugerencia values (null, '" + libro.getNom_libro() + "', '" + libro.getEditorial() + "','" + libro.getAutor() + "', now(), '" + libro.getId_usario() + "');";
            PreparedStatement ps = connection.prepareCall(SQL);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error DAO_Sugerencia > create : " + e);
            return false;
        }
    }
}
