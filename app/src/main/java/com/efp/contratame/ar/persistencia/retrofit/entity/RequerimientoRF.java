package com.efp.contratame.ar.persistencia.retrofit.entity;


public class RequerimientoRF {

    private String idRequerimiento;
    private String titulo;
    private String keyRubro;
    private String descripcion;
    private String imagen;
    private String UbicLatitud;
    private String UbicLongitud;

    public RequerimientoRF(String idRequerimiento, String titulo, String keyRubro, String descripcion, String imagen, String ubicLatitud, String ubicLongitud) {
        this.idRequerimiento = idRequerimiento;
        this.titulo = titulo;
        this.keyRubro = keyRubro;
        this.descripcion = descripcion;
        this.imagen = imagen;
        this.UbicLatitud = ubicLatitud;
        this.UbicLongitud = ubicLongitud;
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
        return UbicLatitud;
    }

    public void setUbicLatitud(String ubicLatitud) {
        UbicLatitud = ubicLatitud;
    }

    public String getUbicLongitud() {
        return UbicLongitud;
    }

    public void setUbicLongitud(String ubicLongitud) {
        UbicLongitud = ubicLongitud;
    }
}
