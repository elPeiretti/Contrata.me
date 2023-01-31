package com.efp.contratame.ar.persistencia.retrofit.entity;

import java.util.List;

public class ServicioRF {

    private String idServicio;
    private String keyTipoServicio;
    private String keyPrestador;
    private String descripcion;
    private String puntuacion;
    private List<String> galeriaImagenes;

    public ServicioRF(String idServicio, String keyTipoServicio, String keyPrestador, String descripcion, String puntuacion, List<String> galeriaImagenes) {
        this.idServicio = idServicio;
        this.keyTipoServicio = keyTipoServicio;
        this.keyPrestador = keyPrestador;
        this.descripcion = descripcion;
        this.puntuacion = puntuacion;
        this.galeriaImagenes = galeriaImagenes;
    }

    public String getIdServicio() {
        return idServicio;
    }

    public void setIdServicio(String idServicio) {
        this.idServicio = idServicio;
    }

    public String getKeyTipoServicio() {
        return keyTipoServicio;
    }

    public void setKeyTipoServicio(String keyTipoServicio) {
        this.keyTipoServicio = keyTipoServicio;
    }

    public String getKeyPrestador() {
        return keyPrestador;
    }

    public void setKeyPrestador(String keyPrestador) {
        this.keyPrestador = keyPrestador;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(String puntuacion) {
        this.puntuacion = puntuacion;
    }

    public List<String> getGaleriaImagenes() {
        return galeriaImagenes;
    }

    public void setGaleriaImagenes(List<String> galeriaImagenes) {
        this.galeriaImagenes = galeriaImagenes;
    }
}
