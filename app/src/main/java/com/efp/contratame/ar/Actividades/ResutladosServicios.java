package com.efp.contratame.ar.Actividades;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SearchView;
import android.widget.Spinner;
import com.efp.contratame.ar.R;
import com.efp.contratame.ar.ServiciosRecyclerAdapter;
import com.efp.contratame.ar.databinding.ActivityResutladosServiciosBinding;
import com.efp.contratame.ar.persistencia.repository.ServicioRepository;

public class ResutladosServicios extends AppCompatActivity implements SearchView.OnQueryTextListener{

    private ActivityResutladosServiciosBinding binding;
    private RecyclerView recyclerView;
    private ServiciosRecyclerAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private Context ctx = this;
    private SearchView txtBusqueda;
    private Spinner spinner;

    Toolbar toolbar;


    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityResutladosServiciosBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        mAdapter= new ServiciosRecyclerAdapter(ServicioRepository._SERVICIOS, ctx);
        mAdapter.ordenar("Mejor puntuación primero");
        recyclerView = binding.recyclerServicios;
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(ctx);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);


        //toolbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Grupo servicio");
        //TODO Acá tendríamos que ver de pasar de la primera pantalla a esta cual es ese grupo y mostrarlo



        //Funcionalidad de filtrado

        //Funcionalidad de búsqueda
        txtBusqueda = binding.txtBusqueda;
        txtBusqueda.setOnQueryTextListener(this);

        //Funcionalidad de orden
        spinner= binding.spinner;
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                mAdapter.ordenar(adapterView.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        switch(item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        mAdapter.filtrado(s);
        return false;
    }
}