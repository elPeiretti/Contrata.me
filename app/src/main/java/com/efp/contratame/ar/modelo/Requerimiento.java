package com.efp.contratame.ar.modelo;

import android.net.Uri;
import com.google.android.gms.maps.model.LatLng;
import java.util.UUID;

public class Requerimiento {

    private UUID idRequerimiento;
    private String titulo;
    private TipoServicio rubro;
    private String descripcion;
    private Uri imagen;
    private LatLng ubicacion;

    public Requerimiento(UUID idRequerimiento, String titulo, TipoServicio rubro, String descripcion, Uri imagen, LatLng ubicacion) {
        this.idRequerimiento = idRequerimiento;
        this.titulo = titulo;
        this.rubro = rubro;
        this.descripcion = descripcion;
        this.imagen = imagen;
        this.ubicacion = ubicacion;
    }

    public UUID getIdRequerimiento() {
        return idRequerimiento;
    }

    public void setIdRequerimiento(UUID idRequerimiento) {
        this.idRequerimiento = idRequerimiento;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public TipoServicio getRubro() {
        return rubro;
    }

    public void setRubro(TipoServicio rubro) {
        this.rubro = rubro;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Uri getImagen() {
        return imagen;
    }

    public void setImagen(Uri imagen) {
        this.imagen = imagen;
    }

    public LatLng getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(LatLng ubicacion) {
        this.ubicacion = ubicacion;
    }
}
