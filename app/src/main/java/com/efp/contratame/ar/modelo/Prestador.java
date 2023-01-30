package com.efp.contratame.ar.modelo;

import java.util.List;
import java.util.UUID;

public class Prestador {

    private UUID idPrestador;
    private String nombre;
    private List<Servicio> serviciosBrindados;
    private String imagenPerfil;

    public Prestador(String nombre, String imagenPerfil) {
        this.nombre = nombre;
        this.imagenPerfil = imagenPerfil;
    }

    public Prestador(UUID idPrestador, String nombre, List<Servicio> serviciosBrindados, String imagenPerfil) {
        this.idPrestador = idPrestador;
        this.nombre = nombre;
        this.serviciosBrindados = serviciosBrindados;
        this.imagenPerfil = imagenPerfil;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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
