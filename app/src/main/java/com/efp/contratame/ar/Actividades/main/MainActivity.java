package com.efp.contratame.ar.Actividades.main;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.bumptech.glide.Glide;
import com.efp.contratame.ar.Actividades.AlarmReceiver;
import com.efp.contratame.ar.Actividades.DetalleProveedorServicioFragment;
import com.efp.contratame.ar.Actividades.IniciarSesion;
import com.efp.contratame.ar.Actividades.PerfilUsuarioFragment;
import com.efp.contratame.ar.Actividades.ResultadosServiciosFragment;
import com.efp.contratame.ar.R;
import com.efp.contratame.ar.databinding.ActivityMainBinding;
import com.efp.contratame.ar.modelo.Requerimiento;
import com.efp.contratame.ar.modelo.Servicio;
import com.efp.contratame.ar.modelo.TipoServicio;
import com.efp.contratame.ar.modelo.Usuario;
import com.efp.contratame.ar.persistencia.datasource.RequerimientoDataSource;
import com.efp.contratame.ar.persistencia.repository.RequerimientoRepository;
import com.facebook.AccessToken;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

public class MainActivity extends AppCompatActivity implements MenuPpalFragment.onTipoServicioSelectedListener,
        TipoServicioGetter, NavigationView.OnNavigationItemSelectedListener, UsuarioGetter,
        DetalleProveedorServicioFragment.ServicioGetter, ResultadosServiciosFragment.OnServicioSelectedListener,
        RequerimientoDataSource.EliminarRequerimientoCallback,
        RequerimientoDataSource.GetAllRequerimientosFromCallback{

    private ActivityMainBinding binding;
    private TipoServicio tipoServicioSeleccionado;
    private Usuario user;
    private AppBarConfiguration mAppBarConfiguration;
    private ActionBarDrawerToggle drawerToggle;
    private DrawerLayout drawer;
    private NavController nav;
    private View header;
    private Context ctx = this;
    private Servicio servicioSeleccionado;
    private PendingIntent pendingIntent;
    private Calendar calendar;
    private List<Requerimiento> requerimientos=new ArrayList<Requerimiento>();
    private String id_requerimiento_eliminar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createNotificationChannel();
        Bundle extras = getIntent().getExtras();

        user = new Usuario(
                extras.getString("idUsuario"),
                extras.getString("mail"),
                extras.getString("nombre"),
                extras.getString("foto"),
                extras.getString("sesion"));

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if(extras.containsKey("idRequerimiento")){
            Log.i("Entra", extras.get("idRequerimiento").toString());
        }

        if(extras.containsKey("fragment")){
            switch (extras.getString("fragment")){
                case "presiona la noti":
                    NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                    manager.cancel(123);
                    break;
                case "presiona eliminar":
                    Log.i("Eliminar", "ingresa");
                    Navigation.findNavController(this, R.id.nav_host_fragment_content_main).navigate(R.id.misServiciosFragment);
                    NotificationManager manager1 = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                    String req = extras.get("idRequerimiento").toString();
                    Log.i("Eliminar el requerimiento ", extras.get("idRequerimiento").toString());
                    manager1.cancel(123);
                    crearDialogo(this,req);
                    break;
                case "presiona mantener":
                    NotificationManager manager2 = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                    manager2.cancel(123);
                    break;
            }
        }

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

        TextView tv = findViewById(R.id.tv_email_drawer);
        Intent intent = this.getIntent();
        Bundle extra = intent.getExtras();
        header = navigationView.getHeaderView(0);
        setearValoresDrawer();
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){
            CharSequence name = "Reminders";
            String description = "Channel for reminders";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel("channelid",name,importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
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
                break;
            case R.id.nav_servicios:
                Navigation.findNavController(this, R.id.nav_host_fragment_content_main).navigate(R.id.misServiciosFragment);
                break;
            case R.id.nav_configuraciones:
                Toast.makeText(MainActivity.this, "Se selecciono configuraciones", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_perfil:
                Navigation.findNavController(this, R.id.nav_host_fragment_content_main).navigate(R.id.perfilUsuarioFragment);
                break;
            case R.id.nav_favoritos:
                Toast.makeText(MainActivity.this, "Se selecciono favoritos", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_inicio:
                Navigation.findNavController(this, R.id.nav_host_fragment_content_main).navigate(R.id.menuPpalFragment2);
                break;
            case R.id.nav_cerrar_sesion:
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent( ctx, IniciarSesion.class);
                startActivity(intent);
                break;
        }

        setTitle(item.getTitle());
        drawer.closeDrawers();
        return false;
    }

    public void setearValoresDrawer() {
        TextView tv_email = header.findViewById(R.id.tv_email_drawer);
        TextView tv_nombre = header.findViewById(R.id.tv_nombre_suario);
        ImageView imagen = header.findViewById(R.id.imagenUsuario);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        tv_email.setText(user.getEmail());
        tv_nombre.setText(user.getDisplayName());
        Glide.with(imagen.getContext())
                .load(user.getPhotoUrl())
                .placeholder(R.drawable.blank_profile_picture_973460_1280)
                .error(R.drawable.blank_profile_picture_973460_1280)
                .into(imagen);
    }

    @Override
    public Usuario getCurrentUsuario() {
        return user;
    }

    @Override
    public UUID getIdServicioSeleccionado() {
        return servicioSeleccionado.getIdServicio();
    }

    @Override
    public void onServicioSelected(Servicio s) {
        this.servicioSeleccionado = s;
    }

    public Context getCtx() {
        return ctx;
    }

    public void crearDialogo(Context ctx, String idReq) {
        Log.i("Eliminar el requerimiento ", idReq);
        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
        builder.setMessage("¿Está seguro que desea eliminarlo?").setTitle("Mensaje de confirmación");
        builder.setIcon(R.drawable.icono_sin_fondo);
        builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                id_requerimiento_eliminar=idReq;
                eliminarRequermiento();
            }
        });
        builder.setNegativeButton("No", (dialogInterface, i) -> Toast.makeText(MainActivity.this,
                        "Su requerimiento no se eliminará", Toast.LENGTH_LONG)
                .show());
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onError() {
        Log.e("ERROR_RETROFIT","No se pudieron cargar los requerimientos");
    }

    @Override
    public void onResult(List<Requerimiento> requerimientoList) {
        this.requerimientos=requerimientoList;
        Log.i("eliminar",String.valueOf(requerimientoList.size()));
        Requerimiento eliminar=new Requerimiento();
        for(Requerimiento r : requerimientos){
            if(r.getIdRequerimiento().toString().equals(id_requerimiento_eliminar)){
                eliminar=r;
                Log.i("eliminar",r.getIdRequerimiento().toString());
                break;
            }
        }
        RequerimientoRepository.createInstance().eliminarRequerimiento(eliminar,this.getCurrentUsuario().getIdUsuario(),this);
        Toast.makeText(this, "Requerimiento eliminado exitosamente", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onResult() {
        Navigation.findNavController(this, R.id.nav_host_fragment_content_main).navigate(R.id.misServiciosFragment);
    }

    public void eliminarRequermiento(){
        RequerimientoRepository.createInstance().getAllRequerimientosFrom(this.getCurrentUsuario().getIdUsuario(),this);
    }
}