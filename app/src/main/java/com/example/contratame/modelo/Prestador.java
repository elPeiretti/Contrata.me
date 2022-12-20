package com.example.contratame.modelo;

import android.media.Image;

import java.util.List;

public class Prestador {
    private String Nombre;
    private List<Servicio> serviciosBrindados;
    private String imagen_perfil;

    public Prestador(String nombre, String imagen_perfil) {
        Nombre = nombre;
        this.imagen_perfil = imagen_perfil;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public List<Servicio> getServiciosBrindados() {
        return serviciosBrindados;
    }

    public void setServiciosBrindados(List<Servicio> serviciosBrindados) {
        this.serviciosBrindados = serviciosBrindados;
    }

    public String getImagen_perfil() {
        return imagen_perfil;
    }

    public void setImagen_perfil(String imagen_perfil) {
        this.imagen_perfil = imagen_perfil;
    }
}
