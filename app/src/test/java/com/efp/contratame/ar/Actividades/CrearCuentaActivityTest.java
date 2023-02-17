package com.efp.contratame.ar.Actividades;


import static org.junit.Assert.assertEquals;


import com.efp.contratame.ar.Actividades.login.CrearCuentaActivity;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;


public class CrearCuentaActivityTest {

    @CsvSource(value={"anto@mail.com,MiContrasenia"})
    @ParameterizedTest
    public void validarCamposEmailPasswordValidos(String mail, String password) {
        assertEquals(0, CrearCuentaActivity.validarCampos(mail, password));
    }

    @CsvSource(value={"anto@mail,MiContrasenia","anto.com,MiContrasenia", "ant,MiContrasenia"})
    @ParameterizedTest
    public void validarCamposEmailInvalido(String mail, String password) {
        assertEquals(1, CrearCuentaActivity.validarCampos(mail, password));
    }

    @CsvSource(value={"anto@mail.com,''"})
    @ParameterizedTest
    public void validarCamposNoPassword(String mail, String password) {
        assertEquals(2, CrearCuentaActivity.validarCampos(mail, password));
    }

    @CsvSource(value={"anto@mail.com,hola","anto@mail.com,pass","anto@mail.com,psw1"})
    @ParameterizedTest
    public void validarCamposPasswordInvalida(String mail, String password) {
        assertEquals(3, CrearCuentaActivity.validarCampos(mail, password));
    }

}