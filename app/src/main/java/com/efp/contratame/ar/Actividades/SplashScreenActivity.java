package com.efp.contratame.ar.Actividades;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;

import androidx.appcompat.app.AppCompatActivity;

import com.efp.contratame.ar.Actividades.main.MainActivity;

public class SplashScreenActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SystemClock.sleep(2000);
        startActivity(new Intent(SplashScreenActivity.this, IniciarSesion.class));
        finish();
    }
}
