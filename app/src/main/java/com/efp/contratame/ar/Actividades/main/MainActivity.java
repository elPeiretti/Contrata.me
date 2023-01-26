package com.efp.contratame.ar.Actividades.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.efp.contratame.ar.Actividades.ResultadosServiciosFragment;
import com.efp.contratame.ar.R;
import com.efp.contratame.ar.databinding.ActivityMainBinding;
import com.efp.contratame.ar.modelo.TipoServicio;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity implements MenuPpalFragment.onTipoServicioSelectedListener, ResultadosServiciosFragment.TipoServicioGetter {

    private ActivityMainBinding binding;
    private TipoServicio tipoServicioSeleccionado;
    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);
        getSupportActionBar().setTitle("Qué necesitas?");
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_inicio, R.id.nav_mensajes, R.id.nav_perfil, R.id.nav_favoritos, R.id.nav_servicios, R.id.nav_configuraciones)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main, menu);
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