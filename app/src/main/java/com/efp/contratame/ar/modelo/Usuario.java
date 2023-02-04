package com.efp.contratame.ar.modelo;

import java.util.UUID;

public class Usuario {

    private UUID idUsuario;
    private String email;
    private String nombre;
    private String foto_perfil;
    private String tipo_sesion; //facebook.com, google.com, firebase

    public Usuario(String email, String nombre, String foto_perfil, String tipo_sesion) {
        this.email = email;
        this.nombre = nombre;
        this.foto_perfil = foto_perfil;
        this.tipo_sesion = tipo_sesion;
    }

    public Usuario(UUID idUsuario, String email, String nombre, String foto_perfil, String tipo_sesion) {
        this.idUsuario = idUsuario;
        this.email = email;
        this.nombre = nombre;
        this.foto_perfil = foto_perfil;
        this.tipo_sesion = tipo_sesion;
    }

    public UUID getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(UUID idUsuario) {
        this.idUsuario = idUsuario;
    }

    public void setTipo_sesion(String tipo_sesion) {
        this.tipo_sesion = tipo_sesion;
    }

    public String getTipo_sesion() {
        return tipo_sesion;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFoto_perfil() {
        return foto_perfil;
    }

    public void setFoto_perfil(String foto_perfil) {
        this.foto_perfil = foto_perfil;
    }
}
