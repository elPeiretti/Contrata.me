package com.efp.contratame.ar.Actividades;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.bumptech.glide.Glide;
import com.efp.contratame.ar.databinding.ActivityDetalleProveedorServicioBinding;
import com.efp.contratame.ar.databinding.ActivityResutladosServiciosBinding;

public class DetalleProveedorServicio extends AppCompatActivity {

    private ActivityDetalleProveedorServicioBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityDetalleProveedorServicioBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        Intent intent = this.getIntent();
        Bundle extra = intent.getExtras();


        binding.tvNombre.setText(extra.getString("nombre"));
        binding.ratingBar2.setRating(extra.getFloat("puntuacion"));

        String EDteamImage = extra.getString("imagen");
        Glide.with(binding.imageView.getContext()).load(EDteamImage).into(binding.imageView);

        binding.tvDescripcion.setText("descripcion");
    }
}