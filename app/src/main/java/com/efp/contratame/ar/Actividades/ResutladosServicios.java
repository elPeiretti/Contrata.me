package com.efp.contratame.ar.Actividades;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SearchView;
import android.widget.Spinner;


import com.efp.contratame.ar.R;
import com.efp.contratame.ar.adapters.ServiciosRecyclerAdapter;
import com.efp.contratame.ar.ServiciosRepository;
import com.efp.contratame.ar.auxiliares.SelectListener;
import com.efp.contratame.ar.databinding.ActivityResutladosServiciosBinding;
import com.efp.contratame.ar.modelo.Servicio;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class ResutladosServicios extends AppCompatActivity implements SearchView.OnQueryTextListener, SelectListener {

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

        //SACAR
        binding.btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                LoginManager.getInstance().logOut();
                Intent intent = new Intent( ctx,IniciarSesion.class);
                startActivity(intent);
            }
        });
        binding.btnMensajes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ctx, Mensajes.class));
            }
        });



        //TOOLBAR
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Grupo servicio");
        //TODO Acá tendríamos que ver de pasar de la primera pantalla a esta cual es ese grupo y mostrarlo


        //RECYCLERVIEW
        mAdapter= new ServiciosRecyclerAdapter(ServiciosRepository._SERVICIOS, ctx, this);
        mAdapter.ordenar("Mejor puntuación primero");
        recyclerView = binding.recyclerServicios;
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(ctx);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);
        DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),1);
        recyclerView.addItemDecoration(mDividerItemDecoration);

        //Funcionalidad de filtrado
        //TODO

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

    @Override
    public void onItemClicked(Servicio s) {
        Intent intent = new Intent( ResutladosServicios.this,DetalleProveedorServicio.class);
        intent.putExtra("nombre", s.getPrestador().getNombre());
        intent.putExtra("puntuacion", s.getPuntuacion());
        intent.putExtra("imagen", s.getPrestador().getImagen_perfil());
        intent.putExtra("descripcion", s.getDescripcion());
        intent.putExtra("listaImagenes", new ArrayList<String>(s.getGaleriaImagenes()));
        startActivity(intent);
    }
}