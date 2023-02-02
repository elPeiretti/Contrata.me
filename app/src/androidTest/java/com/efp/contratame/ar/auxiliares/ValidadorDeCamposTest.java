package com.efp.contratame.ar.auxiliares;

import static org.junit.Assert.assertEquals;

import android.util.Log;
import android.util.Patterns;

import com.efp.contratame.ar.auxiliares.ValidadorDeCampos;

import org.junit.Test;

public class ValidadorDeCamposTest {

     @Test
      public void validarCamposEmailPasswordValidos() {
         assertEquals(0,ValidadorDeCampos.validarCampos("anto@mail.com", "MiContrasenia"));
      }

    @Test
    public void validarCamposEmailInvalido() {
        Log.d("test", String.valueOf(!Patterns.EMAIL_ADDRESS.matcher("anto@mail").matches()));
        assertEquals(1,ValidadorDeCampos.validarCampos("anto@mail", "MiContrasenia"));
    }

    @Test
    public void validarCamposNoPassword() {
        Log.d("test", String.valueOf(!Patterns.EMAIL_ADDRESS.matcher("anto@mail").matches()));
        assertEquals(2,ValidadorDeCampos.validarCampos("anto@mail.com", ""));
    }

    @Test
    public void validarCamposPasswordInvalida() {
        Log.d("test", String.valueOf(!Patterns.EMAIL_ADDRESS.matcher("anto@mail").matches()));
        assertEquals(3,ValidadorDeCampos.validarCampos("anto@mail.com", "Hola"));
    }

}