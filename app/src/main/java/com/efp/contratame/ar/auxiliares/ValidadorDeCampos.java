package com.efp.contratame.ar.auxiliares;

import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;

public class ValidadorDeCampos {
    public static int validarCampos(String email, String password) {
        int rdo = 0;
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            rdo=1;
        }else if(TextUtils.isEmpty(password)){
            rdo=2;
        }else if(password.length()<6){
            rdo=3;
        }
        return rdo;
    }
}
