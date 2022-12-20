package com.example.contratame;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.example.contratame.databinding.ActivityResutladosServiciosBinding;
import com.example.contratame.modelo.Servicio;

import java.util.List;

public class ResutladosServicios extends AppCompatActivity {

    ActivityResutladosServiciosBinding binding;
    RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private Context ctx = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityResutladosServiciosBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        mAdapter= new ServiciosRecyclerAdapter(ServiciosRepository._SERVICIOS, ctx);
        recyclerView = binding.recyclerServicios;
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(ctx);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);

    }
}