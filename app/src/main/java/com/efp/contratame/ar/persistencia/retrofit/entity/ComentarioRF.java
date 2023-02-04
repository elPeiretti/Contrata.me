package com.efp.contratame.ar.persistencia.retrofit.entity;

public class ComentarioRF {

    private String usuario;
    private String timestamp;
    private String contenido;

    public ComentarioRF(String usuario, String timestamp, String contenido) {
        this.usuario = usuario;
        this.timestamp = timestamp;
        this.contenido = contenido;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getContenido() {
        return contenido;
    }
}
