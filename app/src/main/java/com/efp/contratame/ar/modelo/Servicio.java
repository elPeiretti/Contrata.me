package com.efp.contratame.ar.modelo;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Servicio{

    private UUID idServicio;
    private Prestador prestador;
    private TipoServicio tipo;
    private String descripcion;
    private float puntuacion;
    private List<String> galeriaImagenes;

    public Servicio(Prestador prestador, TipoServicio tipo, String descripcion, float puntuacion, List<String> galeriaImagenes) {
        this.prestador = prestador;
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.puntuacion = puntuacion;
        this.galeriaImagenes= galeriaImagenes;
    }

    public Servicio(UUID idServicio, Prestador prestador, TipoServicio tipo, String descripcion, float puntuacion, List<String> galeriaImagenes) {
        this.idServicio = idServicio;
        this.prestador = prestador;
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.puntuacion = puntuacion;
        this.galeriaImagenes = galeriaImagenes;
    }

    public UUID getIdServicio() {
        return idServicio;
    }

    public void setIdServicio(UUID idServicio) {
        this.idServicio = idServicio;
    }

    public float getPuntuacion() {
        return puntuacion;
    }

    public List<String> getGaleriaImagenes() {
        return galeriaImagenes;
    }

    public void setGaleriaImagenes(List<String> galeriaImagenes) {
        this.galeriaImagenes = galeriaImagenes;
    }

    public void setPuntuacion(float puntuacion) {
        this.puntuacion = puntuacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Prestador getPrestador() {
        return prestador;
    }

    public void setPrestador(Prestador prestador) {
        this.prestador = prestador;
    }

    public TipoServicio getTipo() {
        return tipo;
    }

    public void setTipo(TipoServicio tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString(){
        return idServicio.toString();
    }

}
