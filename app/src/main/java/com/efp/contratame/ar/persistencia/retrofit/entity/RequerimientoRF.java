package com.efp.contratame.ar.persistencia.retrofit.entity;


public class RequerimientoRF {

    private String idRequerimiento;
    private String titulo;
    private String keyRubro;
    private String descripcion;
    // imagen en base64
    private String imagen;
    private String ubicLatitud;
    private String ubicLongitud;

    public RequerimientoRF(String idRequerimiento, String titulo, String keyRubro, String descripcion, String imagen, String ubicLatitud, String ubicLongitud) {
        this.idRequerimiento = idRequerimiento;
        this.titulo = titulo;
        this.keyRubro = keyRubro;
        this.descripcion = descripcion;
        this.imagen = imagen;
        this.ubicLatitud = ubicLatitud;
        this.ubicLongitud = ubicLongitud;
    }

    public String getIdRequerimiento() {
        return idRequerimiento;
    }

    public void setIdRequerimiento(String idRequerimiento) {
        this.idRequerimiento = idRequerimiento;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getKeyRubro() {
        return keyRubro;
    }

    public void setKeyRubro(String keyRubro) {
        this.keyRubro = keyRubro;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getUbicLatitud() {
        return ubicLatitud;
    }

    public void setUbicLatitud(String ubicLatitud) {
        this.ubicLatitud = ubicLatitud;
    }

    public String getUbicLongitud() {
        return ubicLongitud;
    }

    public void setUbicLongitud(String ubicLongitud) {
        this.ubicLongitud = ubicLongitud;
    }
}
