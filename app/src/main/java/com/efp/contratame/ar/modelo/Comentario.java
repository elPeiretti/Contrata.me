package com.efp.contratame.ar.modelo;

import java.time.LocalDate;

public class Comentario {

    private Usuario usuario;
    private LocalDate fecha;
    private String contenido;

    public Comentario(Usuario usuario, String contenido) {
        this.usuario = usuario;
        this.contenido = contenido;
        this.fecha= LocalDate.now();
    }
}
