package com.efp.contratame.ar.modelo;

public class Usuario {

    private String idUsuario;
    private String email;
    private String nombre;
    private String foto_perfil;
    private String tipoSesion;

    public Usuario(String idUsuario, String email, String nombre, String foto_perfil, String tipoSesion) {
        this.idUsuario = idUsuario;
        this.email = email;
        this.nombre = nombre;
        this.foto_perfil = foto_perfil;
        this.tipoSesion = tipoSesion;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTipoSesion() {
        return tipoSesion;
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
