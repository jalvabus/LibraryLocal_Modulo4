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
public class Tarjeta_prepago {

    private String codigo_tarjeta;
    private String estado;
    private int id_usuario;
    private float saldo;
    private float saldo_prepago;
    private int puntos;

    public String getCodigo_tarjeta() {
        return codigo_tarjeta;
    }

    public void setCodigo_tarjeta(String codigo_tarjeta) {
        this.codigo_tarjeta = codigo_tarjeta;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public float getSaldo() {
        return saldo;
    }

    public void setSaldo(float saldo) {
        this.saldo = saldo;
    }

    public float getSaldo_prepago() {
        return saldo_prepago;
    }

    public void setSaldo_prepago(float saldo_prepago) {
        this.saldo_prepago = saldo_prepago;
    }

    public int getPuntos() {
        return puntos;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }

    public Tarjeta_prepago() {
    }
}