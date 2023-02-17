package com.efp.contratame.ar.modelo;

import android.graphics.Bitmap;
import android.net.Uri;
import com.google.android.gms.maps.model.LatLng;
import java.util.UUID;

public class Requerimiento {

    private UUID idRequerimiento;
    private String titulo;
    private TipoServicio rubro;
    private String descripcion;
    private Bitmap imagen;
    private LatLng ubicacion;

    public Requerimiento(UUID idRequerimiento, String titulo, TipoServicio rubro, String descripcion, Bitmap imagen, LatLng ubicacion) {
        this.idRequerimiento = idRequerimiento;
        this.titulo = titulo;
        this.rubro = rubro;
        this.descripcion = descripcion;
        this.imagen = imagen;
        this.ubicacion = ubicacion;
    }

    public Requerimiento( ){

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

    public Bitmap getImagen() {
        return imagen;
    }

    public void setImagen(Bitmap imagen) {
        this.imagen = imagen;
    }

    public LatLng getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(LatLng ubicacion) {
        this.ubicacion = ubicacion;
    }
}
