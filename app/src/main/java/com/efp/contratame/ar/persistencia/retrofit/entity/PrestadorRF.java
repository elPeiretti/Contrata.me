package com.efp.contratame.ar.persistencia.retrofit.entity;

import java.util.List;

public class PrestadorRF {

    private String idPrestador;
    private String nombre;
    private String imagenPerfil;
    private List<String> keysServiciosBrindados;

    public PrestadorRF(){}

    public PrestadorRF(String nombre, String imagenPerfil, List<String> keysServiciosBrindados) {
        this.nombre = nombre;
        this.imagenPerfil = imagenPerfil;
        this.keysServiciosBrindados = keysServiciosBrindados;
    }

    public String getIdPrestador() {
        return idPrestador;
    }

    public void setIdPrestador(String idPrestador) {
        this.idPrestador = idPrestador;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getImagenPerfil() {
        return imagenPerfil;
    }

    public void setImagenPerfil(String imagenPerfil) {
        this.imagenPerfil = imagenPerfil;
    }

    public List<String> getKeysServiciosBrindados() {
        return keysServiciosBrindados;
    }

    public void setKeysServiciosBrindados(List<String> keysServiciosBrindados) {
        this.keysServiciosBrindados = keysServiciosBrindados;
    }
}
