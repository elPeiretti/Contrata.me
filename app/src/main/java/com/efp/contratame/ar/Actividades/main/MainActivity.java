package com.efp.contratame.ar.Actividades.main;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.efp.contratame.ar.Actividades.ResultadosServiciosFragment;
import com.efp.contratame.ar.R;
import com.efp.contratame.ar.databinding.ActivityMainBinding;
import com.efp.contratame.ar.modelo.TipoServicio;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity implements MenuPpalFragment.onTipoServicioSelectedListener, ResultadosServiciosFragment.TipoServicioGetter, NavigationView.OnNavigationItemSelectedListener {

    private ActivityMainBinding binding;
    private TipoServicio tipoServicioSeleccionado;
    private AppBarConfiguration mAppBarConfiguration;
    private ActionBarDrawerToggle drawerToggle;
    private DrawerLayout drawer;
    private NavController nav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);
        drawer = binding.drawerLayout;

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //nav = Navigation.findNavController(binding.navView);

        drawerToggle = new ActionBarDrawerToggle(
            this,
            drawer,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close);

        drawer.setDrawerListener(drawerToggle);
        drawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NavigationView navigationView = binding.navView;
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_mensajes:
                Toast.makeText(MainActivity.this, "Se selecciono mensajes", Toast.LENGTH_SHORT).show();
                Navigation.findNavController(this, R.id.nav_host_fragment_content_main).navigate(R.id.mensajesFragment);
                break;
            case R.id.nav_servicios:
                Toast.makeText(MainActivity.this, "Se selecciono servicios", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_configuraciones:
                Toast.makeText(MainActivity.this, "Se selecciono configuraciones", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_perfil:
                Toast.makeText(MainActivity.this, "Se selecciono perfil", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_favoritos:
                Toast.makeText(MainActivity.this, "Se selecciono favoritos", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_inicio:
                Toast.makeText(MainActivity.this, "Se selecciono inicio", Toast.LENGTH_SHORT).show();
                Navigation.findNavController(this, R.id.nav_host_fragment_content_main).navigate(R.id.menuPpalFragment2);
                break;
        }

        setTitle(item.getTitle());
        drawer.closeDrawers();
        return false;
    }
}