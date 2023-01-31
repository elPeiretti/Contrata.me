package com.efp.contratame.ar.persistencia.retrofit.entity;

public class TipoServicioRF {

    private String idTipoServicio;
    private String nombre;
    private String icono;

    public TipoServicioRF() {}

    public TipoServicioRF(String idTipoServicio, String nombre, String icono){
        this.idTipoServicio = idTipoServicio;
        this.nombre = nombre;
        this.icono = icono;
    }

    public String getIdTipoServicio() {
        return idTipoServicio;
    }

    public String getNombre() {
        return nombre;
    }

    public String getIcono() {
        return icono;
    }
}
