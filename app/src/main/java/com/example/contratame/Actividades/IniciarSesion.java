package com.example.contratame.Actividades;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.contratame.databinding.ActivityIniciarSesionBinding;

public class IniciarSesion extends AppCompatActivity {

    private ActivityIniciarSesionBinding binding;
    private Button btnLogin;
    private Context ctx;
    private TextView registrate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityIniciarSesionBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        ctx=this;

        btnLogin = binding.btnIniciarSesion;
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( ctx,MainActivity.class);
                startActivity(intent);
            }
        });

        registrate = binding.tvRegistrate;
        registrate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( ctx,CrearCuenta.class);
                startActivity(intent);
            }
        });


    }
}