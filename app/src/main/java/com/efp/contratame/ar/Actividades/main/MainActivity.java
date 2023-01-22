package com.efp.contratame.ar.Actividades.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.efp.contratame.ar.Actividades.ResultadosServiciosFragment;
import com.efp.contratame.ar.R;
import com.efp.contratame.ar.databinding.ActivityMainBinding;
import com.efp.contratame.ar.modelo.TipoServicio;

public class MainActivity extends AppCompatActivity implements MenuPpalFragment.onTipoServicioSelectedListener, ResultadosServiciosFragment.TipoServicioGetter {

    private Toolbar toolbar;
    private ActivityMainBinding binding;
    private TipoServicio tipoServicioSeleccionado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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

    public void setTipoServicioSeleccionado(TipoServicio tp){
        this.tipoServicioSeleccionado = tp;
    }

    public TipoServicio getTipoServicioSeleccionado(){
        return tipoServicioSeleccionado;
    }

    @Override
    public void onTipoServicioSelected(TipoServicio tp) {
        setTipoServicioSeleccionado(tp);
    }

    @Override
    public TipoServicio getTipoSeleccionado() {
        return tipoServicioSeleccionado;
    }
}