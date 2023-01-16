package com.efp.contratame.ar.Actividades;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

import com.efp.contratame.ar.R;
import com.efp.contratame.ar.adapters.ContactosRecyclerAdapter;
import com.efp.contratame.ar.adapters.GaleriaRecyclerAdapter;
import com.efp.contratame.ar.databinding.ActivityMensajesBinding;
import com.efp.contratame.ar.modelo.Prestador;

import java.util.ArrayList;
import java.util.List;

public class Mensajes extends AppCompatActivity implements SearchView.OnQueryTextListener{
    private ActivityMensajesBinding binding;
    private ContactosRecyclerAdapter mAdapter;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private Context ctx=this;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMensajesBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        mAdapter= new ContactosRecyclerAdapter(List.of(
                new Prestador("Mi Nombre Prestador", "https://www.mndelgolfo.com/blog/wp-content/uploads/2017/09/herramientas-para-electricista.jpg"),
                new Prestador("Alan gomez", "https://www.mndelgolfo.com/blog/wp-content/uploads/2017/09/herramientas-para-electricista.jpg"),
                new Prestador("Shakira", "https://www.mndelgolfo.com/blog/wp-content/uploads/2017/09/herramientas-para-electricista.jpg"),
                new Prestador("Mi Nombre Prestador3", "https://www.mndelgolfo.com/blog/wp-content/uploads/2017/09/herramientas-para-electricista.jpg"),
                new Prestador("Mi Nombre Prestador4", "https://www.mndelgolfo.com/blog/wp-content/uploads/2017/09/herramientas-para-electricista.jpg"),
                new Prestador("Mi Nombre Prestador5", "https://www.mndelgolfo.com/blog/wp-content/uploads/2017/09/herramientas-para-electricista.jpg"),
                new Prestador("Mi Nombre Prestador6", "https://www.mndelgolfo.com/blog/wp-content/uploads/2017/09/herramientas-para-electricista.jpg"),
                new Prestador("Mi Nombre Prestador7", "https://www.mndelgolfo.com/blog/wp-content/uploads/2017/09/herramientas-para-electricista.jpg")
                ), ctx);
        recyclerView = binding.recyclerContactos;
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(ctx);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);
        DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),1);
        recyclerView.addItemDecoration(mDividerItemDecoration);

        //Toolbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Mensajes");

        binding.txtBusqueda.setOnQueryTextListener(this);
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
    public boolean onQueryTextSubmit(String s) { return false;  }

    @Override
    public boolean onQueryTextChange(String s) {
        mAdapter.filtrado(s);
        return false;
    }
}