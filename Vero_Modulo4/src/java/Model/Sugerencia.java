/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author juana
 */
public class Sugerencia {

    private int id_sugerencia;
    private String nom_libro;
    private String editorial;
    private String autor;
    private String fecha;
    private String id_usario;

    public Sugerencia() {
    }

    public int getId_sugerencia() {
        return id_sugerencia;
    }

    public void setId_sugerencia(int id_sugerencia) {
        this.id_sugerencia = id_sugerencia;
    }

    public String getNom_libro() {
        return nom_libro;
    }

    public void setNom_libro(String nom_libro) {
        this.nom_libro = nom_libro;
    }

    public String getEditorial() {
        return editorial;
    }

    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getId_usario() {
        return id_usario;
    }

    public void setId_usario(String id_usario) {
        this.id_usario = id_usario;
    }

}
