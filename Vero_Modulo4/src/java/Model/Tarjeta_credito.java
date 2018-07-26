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
public class Tarjeta_credito {

    private String codigo_tarjetacredito;
    private String estado;
    private int id_usuario;
    private float saldo;
    private float saldo_credito;

    public Tarjeta_credito() {
    }

    public String getCodigo_tarjetacredito() {
        return codigo_tarjetacredito;
    }

    public void setCodigo_tarjetacredito(String codigo_tarjetacredito) {
        this.codigo_tarjetacredito = codigo_tarjetacredito;
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

    public float getSaldo_credito() {
        return saldo_credito;
    }

    public void setSaldo_credito(float saldo_credito) {
        this.saldo_credito = saldo_credito;
    }
}
