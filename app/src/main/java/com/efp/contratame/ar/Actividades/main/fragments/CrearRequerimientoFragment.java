package com.efp.contratame.ar.Actividades.main.fragments;

import android.Manifest;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresPermission;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.efp.contratame.ar.Actividades.main.misc.AlarmReceiver;
import com.efp.contratame.ar.Actividades.main.MainActivity;
import com.efp.contratame.ar.Actividades.main.TipoServicioGetter;
import com.efp.contratame.ar.Actividades.main.UsuarioGetter;
import com.efp.contratame.ar.R;
import com.efp.contratame.ar.misc.EspressoIdlingResource;
import com.efp.contratame.ar.databinding.FragmentCrearRequerimientoBinding;
import com.efp.contratame.ar.modelo.Requerimiento;
import com.efp.contratame.ar.modelo.TipoServicio;
import com.efp.contratame.ar.persistencia.datasource.RequerimientoDataSource;
import com.efp.contratame.ar.persistencia.datasource.TipoServicioDataSource;
import com.efp.contratame.ar.persistencia.repository.RequerimientoRepository;
import com.efp.contratame.ar.persistencia.repository.TipoServicioRepository;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class CrearRequerimientoFragment extends Fragment implements TipoServicioDataSource.GetAllTipoServiciosCallback,
        OnMapReadyCallback, RequerimientoDataSource.SaveRequerimientoCallback {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private FragmentCrearRequerimientoBinding binding;
    private ArrayAdapter<TipoServicio> adapterRubro;
    private UsuarioGetter usuarioGetter;
    private TipoServicioGetter tipoServicioGetter;
    private Requerimiento nuevo_requerimiento;
    //cosas para el mapa
    private GoogleMap mapa;
    private ActivityResultLauncher<String> activityResultLauncher;
    private boolean permitido = false;
    private boolean ubicacionOk = false;
    private LatLng pos;
    //cosas para la foto
    private final ActivityResultLauncher<String> fotoGetter;
    private Uri fotoSeleccionada;
    private Calendar calendario;

    public CrearRequerimientoFragment() {

        CrearRequerimientoFragment ctx = this;

        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), result -> {
            if (!result) {
                if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
                    Log.i("PERMISOS", "HAY QUE PEDIR PERMISOS NUEVAMENTE");
                    new AlertDialog.Builder(getActivity())
                            .setTitle("Permisos requeridos")
                            .setMessage("Para poder crear un nuevo requerimiento," +
                                    " necesitamos conocer tu ubicacion para que los" +
                                    " prestadores puedan saber donde se debe realizar el trabajo.")
                            .setPositiveButton("Entendido", (dialogInterface, i) -> activityResultLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION))
                            .setNegativeButton("Volver", (dialogInterface, i) -> {
                                NavHostFragment.findNavController(ctx).navigate(R.id.action_crearRequerimientoFragment_to_resultadosServiciosFragment);
                                Toast.makeText(getActivity(), "Se requieren los permisos de ubicacion para continuar.", Toast.LENGTH_LONG).show();
                            })
                            .create()
                            .show();
                }
                permitido = false;
            }
            else{
                permitido = true;
            }
        });


        fotoGetter = registerForActivityResult(new ActivityResultContracts.GetContent(), result -> {
            if (result != null) {
                fotoSeleccionada = result;
                binding.buttonAgregarFoto.setImageURI(result);
            }
            else if (fotoSeleccionada != null){
                binding.buttonAgregarFoto.setImageURI(fotoSeleccionada);
            }
            else{
                binding.buttonAgregarFoto.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.foto_presione_aqui));
            }
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof UsuarioGetter)
            usuarioGetter = (UsuarioGetter) context;
        if (context instanceof TipoServicioGetter)
            tipoServicioGetter = (TipoServicioGetter) context;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //verificar permisos
        activityResultLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION);

        binding = FragmentCrearRequerimientoBinding.inflate(inflater, container, false);
        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Crear requerimiento");

        Spinner spinner = binding.spinnerRurbos;
        adapterRubro = new ArrayAdapter<>(this.getContext(),
                android.R.layout.simple_spinner_item, new ArrayList<>());
        adapterRubro.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapterRubro);

        binding.progressBarCrearReq.setVisibility(View.GONE);

        binding.buttonAgregarFoto.setOnClickListener(view -> fotoGetter.launch("image/*"));

        binding.tituloEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                binding.tituloInputLayout
                        .setError(editable.toString().isEmpty() ? "Este campo no puede estar vacio." : null);
            }
        });

        binding.descripcionEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                binding.descripcionInputLayout
                        .setError(editable.toString().isEmpty() ? "Este campo no puede estar vacio." : null);
            }
        });

        //fecha = binding.BtnFecha;
        calendario = Calendar.getInstance();
        /*fecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DATE);
                c.setTimeInMillis(calendario.getTimeInMillis()+(1000 * 60 * 60 * 24));

                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                            }
                        },
                        year, month, day);
                datePickerDialog.getDatePicker().setMinDate(calendario.getTimeInMillis());
                datePickerDialog.show();
            }
        });*/

        binding.buttonPublicar.setOnClickListener(view -> {
            if (!permitido){
                Toast.makeText(getActivity(), "Se requieren los permisos de ubicacion para continuar.", Toast.LENGTH_LONG).show();
                return;
            }
            if(!ubicacionOk){
                Toast.makeText(getActivity(), "Actualice su ubicacion para continuar.", Toast.LENGTH_LONG).show();
                return;
            }
            if(!tituloDescripcionAreOk()){
                Toast.makeText(getActivity(), "Asegurese de completar todo para continuar.", Toast.LENGTH_LONG).show();
                return;
            }

            try {
                setLoadingScreen(View.VISIBLE);
                nuevo_requerimiento = new Requerimiento(
                        UUID.randomUUID(),
                        Objects.requireNonNull(binding.tituloEditText.getText()).toString(),
                        (TipoServicio) binding.spinnerRurbos.getSelectedItem(),
                        Objects.requireNonNull(binding.descripcionEditText.getText()).toString(),
                        fotoSeleccionada == null ? null : MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), fotoSeleccionada),
                        pos
                );
                EspressoIdlingResource.getInstance().increment(); // PARA TEST
                RequerimientoRepository.createInstance().saveRequerimiento(nuevo_requerimiento,usuarioGetter.getCurrentUsuario().getIdUsuario(),this);
            } catch (IOException e) {
                Log.e("error cargando la imagen",e.getMessage());
                NavHostFragment.findNavController(this).navigate(R.id.action_crearRequerimientoFragment_to_resultadosServiciosFragment);
                Toast.makeText(getActivity(), "Ha ocurrido un error, intentelo nuevamente.", Toast.LENGTH_LONG).show();
            }

        });

        binding.buttonDescartar.setOnClickListener(view ->
                NavHostFragment.findNavController(this).navigate(R.id.action_crearRequerimientoFragment_to_resultadosServiciosFragment)
        );

        binding.buttonActualizarMap.setOnClickListener(new View.OnClickListener() {
            @Override
            @RequiresPermission(Manifest.permission.ACCESS_FINE_LOCATION)
            public void onClick(View view) {
                actualizarUbicacion();
            }
        });

        //seria mejor guardar en la actividad los tipos servicios cuando los busco para el menu ppal?
        EspressoIdlingResource.getInstance().increment(); // PARA TEST
        TipoServicioRepository.createInstance().getAllTipoServicios(this);

        //init mapa
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.google_map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);
        return binding.getRoot();
    }

    public void setLoadingScreen(int status) {
        binding.progressBarCrearReq.setVisibility(status);
        binding.scrollCrearReq.setVisibility(status == View.VISIBLE ? View.GONE : View.VISIBLE);
    }

    // GetAllTipoServicioCallback
    @Override
    public void onResult(List<TipoServicio> tipos) {
        setLoadingScreen(View.GONE);
        adapterRubro.clear();
        tipos.add(TipoServicioRepository.OTRO);
        adapterRubro.addAll(tipos);
        binding.spinnerRurbos.setSelection(adapterRubro.getPosition(tipoServicioGetter.getTipoSeleccionado()),true);
        EspressoIdlingResource.getInstance().decrement(); // PARA TEST
    }

    // SaveRequerimientoCallback
    @Override
    public void onResult() {
        Log.i("REQ_FRAGMENT","REQUERIMIENTO CREADO");
        setAlarm(nuevo_requerimiento);
        NavHostFragment.findNavController(this).navigate(R.id.action_crearRequerimientoFragment_to_menuPpalFragment2);
        Toast.makeText(getActivity(), "Requerimiento creado exitosamente", Toast.LENGTH_LONG).show();
        EspressoIdlingResource.getInstance().decrement();
    }

    @Override
    public void onError() {
        Log.e("ERROR-RETROFIT","SE HA PRODUCIDO UN ERROR CARGANDO/GUARDANDO DATOS");
        NavHostFragment.findNavController(this).navigate(R.id.action_crearRequerimientoFragment_to_resultadosServiciosFragment);
        Toast.makeText(getActivity(), "Ha ocurrido un error, intentelo nuevamente mas tarde.", Toast.LENGTH_LONG).show();
    }

    @Override
    @RequiresPermission(Manifest.permission.ACCESS_FINE_LOCATION)
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mapa = googleMap;

        if (permitido)
            actualizarUbicacion();
    }

    private void setAlarm(Requerimiento req) {
        Context cont = getActivity().getApplicationContext();
        AlarmManager alarmManager = (AlarmManager) cont.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(cont, AlarmReceiver.class);
        Bundle extras = getActivity().getIntent().getExtras();
        intent.putExtras(extras);
        intent.putExtra("idRequerimiento", req.getIdRequerimiento().toString());

        Log.i("IdRequerimiento1", req.getIdRequerimiento().toString());


        intent.putExtra("tituloRequerimiento",req.getTitulo());
        PendingIntent pendingIntent = PendingIntent.getBroadcast(cont, 0, intent,PendingIntent.FLAG_IMMUTABLE);
        calendario.add(Calendar.SECOND, 10);
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendario.getTimeInMillis(), pendingIntent);
    }

    @RequiresPermission(Manifest.permission.ACCESS_FINE_LOCATION)
    public void actualizarUbicacion(){

        mapa.setMyLocationEnabled(true);
        LocationManager locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
        Location currLoc = locationManager.getLastKnownLocation(locationManager.getBestProvider(new Criteria(), false));

        if(currLoc != null){
            pos = new LatLng(currLoc.getLatitude(), currLoc.getLongitude());
            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(pos)
                    .zoom(14.5f)
                    .bearing(90)
                    .tilt(40)
                    .build();
            mapa.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            ubicacionOk = true;
        }
        else{
            Toast.makeText(getActivity(), "Por favor, active la ubicacion.", Toast.LENGTH_LONG).show();
            ubicacionOk = false;
        }
    }

    public boolean tituloDescripcionAreOk(){
        boolean ok = true;
        if(Objects.requireNonNull(binding.tituloEditText.getText()).toString().isEmpty()) {
            ok = false;
            binding.tituloInputLayout.setError("Este campo no puede estar vacio.");
        }
        if(Objects.requireNonNull(binding.descripcionEditText.getText()).toString().isEmpty()) {
            ok = false;
            binding.descripcionInputLayout.setError("Este campo no puede estar vacio.");
        }

        return ok;
    }

}