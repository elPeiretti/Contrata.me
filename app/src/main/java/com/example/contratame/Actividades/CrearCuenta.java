package com.example.contratame.Actividades;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.contratame.databinding.ActivityCrearCuentaBinding;
import com.example.contratame.databinding.ActivityResutladosServiciosBinding;

public class CrearCuenta extends AppCompatActivity {

    private ActivityCrearCuentaBinding binding;
    private TextView iniciarSesion;
    private Context ctx;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        binding = ActivityCrearCuentaBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        ctx=this;

        iniciarSesion = binding.tvIniciaSesion;
        iniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ctx, IniciarSesion.class);
                startActivity(intent);
            }
        });
    }
}