/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

public class Compra_boleto {
    
    private int folio;
    private int cantidad_boletos;
    private int cantidad_pagos;
    private double costo_total;
    private double restante;
    private String status;
    private String fecha_compra;
    private int id_evento;
    private Evento evento;
    private int id_usuario;
    private Usuario usuario;

    public Compra_boleto() {
    }

    public int getFolio() {
        return folio;
    }

    public void setFolio(int folio) {
        this.folio = folio;
    }

    public int getCantidad_boletos() {
        return cantidad_boletos;
    }

    public void setCantidad_boletos(int cantidad_boletos) {
        this.cantidad_boletos = cantidad_boletos;
    }

    public int getCantidad_pagos() {
        return cantidad_pagos;
    }

    public void setCantidad_pagos(int cantidad_pagos) {
        this.cantidad_pagos = cantidad_pagos;
    }

    public double getCosto_total() {
        return costo_total;
    }

    public void setCosto_total(double costo_total) {
        this.costo_total = costo_total;
    }

    public double getRestante() {
        return restante;
    }

    public void setRestante(double restante) {
        this.restante = restante;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFecha_compra() {
        return fecha_compra;
    }

    public void setFecha_compra(String fecha_compra) {
        this.fecha_compra = fecha_compra;
    }

    public int getId_evento() {
        return id_evento;
    }

    public void setId_evento(int id_evento) {
        this.id_evento = id_evento;
    }

    public Evento getEvento() {
        return evento;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
    
}
