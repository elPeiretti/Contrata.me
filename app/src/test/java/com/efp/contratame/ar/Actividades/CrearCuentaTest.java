package com.efp.contratame.ar.Actividades;


import static org.junit.Assert.assertEquals;


import org.junit.Test;

public class CrearCuentaTest {

    @Test
    public void validarCamposEmailPasswordValidos() {
        assertEquals(0,CrearCuenta.validarCampos("anto@mail.com", "MiContrasenia"));
    }

    @Test
    public void validarCamposEmailInvalido() {
        assertEquals(1,CrearCuenta.validarCampos("anto@mail", "MiContrasenia"));
    }

    @Test
    public void validarCamposNoPassword() {
        assertEquals(2,CrearCuenta.validarCampos("anto@mail.com", ""));
    }

    @Test
    public void validarCamposPasswordInvalida() {
        assertEquals(3,CrearCuenta.validarCampos("anto@mail.com", "Hola"));
    }
}