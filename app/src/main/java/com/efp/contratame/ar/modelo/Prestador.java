package com.efp.contratame.ar.modelo;

import java.util.List;

public class Prestador {
    private String Nombre;
    private List<Servicio> serviciosBrindados;
    private String imagenPerfil;

    public Prestador(String nombre, String imagenPerfil) {
        Nombre = nombre;
        this.imagenPerfil = imagenPerfil;
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

    public String getImagenPerfil() {
        return imagenPerfil;
    }

    public void setImagenPerfil(String imagenPerfil) {
        this.imagenPerfil = imagenPerfil;
    }
}
