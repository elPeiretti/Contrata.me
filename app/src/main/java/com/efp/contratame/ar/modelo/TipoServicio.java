package com.efp.contratame.ar.modelo;

public class TipoServicio {

    private String nombre;
    private String icono;

    public TipoServicio(){}

    public TipoServicio(String nombre, String icono){
        this.nombre = nombre;
        this.icono = icono;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getIcono() {
        return icono;
    }

    public void setIcono(String icono) {
        this.icono = icono;
    }
}
