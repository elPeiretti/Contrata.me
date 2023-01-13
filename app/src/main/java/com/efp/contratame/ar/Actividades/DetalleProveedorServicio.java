package com.efp.contratame.ar.Actividades;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;


import com.bumptech.glide.Glide;
import com.efp.contratame.ar.GaleriaRecyclerAdapter;
import com.efp.contratame.ar.ServiciosRepository;
import com.efp.contratame.ar.databinding.ActivityDetalleProveedorServicioBinding;

public class DetalleProveedorServicio extends AppCompatActivity {

    private ActivityDetalleProveedorServicioBinding binding;
    private RecyclerView recyclerView;
    private GaleriaRecyclerAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private Context ctx=this;
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

        mAdapter= new GaleriaRecyclerAdapter(ServiciosRepository.galeriaImagenes, ctx);
        recyclerView = binding.recyclerImagenes;
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(ctx, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);
    }
}