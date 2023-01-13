package com.efp.contratame.ar.modelo;

import android.os.Parcel;
import android.os.Parcelable;

public class Servicio{
    private Prestador prestador;
    private TiposServicios tipo;
    private String descripcion;
    private float puntuacion;

    public Servicio(Prestador prestador, TiposServicios tipo, String descripcion, float puntuacion) {
        this.prestador = prestador;
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.puntuacion = puntuacion;
    }


    public float getPuntuacion() {
        return puntuacion;
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

    public TiposServicios getTipo() {
        return tipo;
    }

    public void setTipo(TiposServicios tipo) {
        this.tipo = tipo;
    }


}
