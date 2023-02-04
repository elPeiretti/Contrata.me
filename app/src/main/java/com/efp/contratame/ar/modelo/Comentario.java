package com.efp.contratame.ar.modelo;

import java.time.LocalDate;

public class Comentario {

    private String usuario;
    private LocalDate fecha;
    private String contenido;

    public Comentario(String usuario, String contenido) {
        this.usuario = usuario;
        this.contenido = contenido;
        this.fecha= LocalDate.now();
    }

    public Comentario(String usuario, LocalDate fecha, String contenido){
        this.usuario = usuario;
        this.fecha = fecha;
        this.contenido = contenido;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }
}
