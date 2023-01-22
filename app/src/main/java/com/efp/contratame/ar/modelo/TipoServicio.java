package com.efp.contratame.ar.modelo;

import java.util.UUID;

public class TipoServicio {

    private UUID idTipoServicio;
    private String nombre;
    private String icono;

    public TipoServicio(){}

    public TipoServicio(String nombre, String icono){
        this.idTipoServicio = UUID.randomUUID();
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

    public UUID getIdTipoServicio(){
        return idTipoServicio;
    }

    public void setIdTipoServicio(UUID id){
        this.idTipoServicio = id;
    }
}
