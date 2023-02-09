package com.efp.contratame.ar.Actividades;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresPermission;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.efp.contratame.ar.Actividades.main.MainActivity;
import com.efp.contratame.ar.R;
import com.efp.contratame.ar.databinding.FragmentCrearRequerimientoBinding;
import com.efp.contratame.ar.modelo.TipoServicio;
import com.efp.contratame.ar.persistencia.datasource.TipoServicioDataSource;
import com.efp.contratame.ar.persistencia.repository.TipoServicioRepository;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import kotlin.jvm.internal.Lambda;

public class CrearRequerimientoFragment extends Fragment implements TipoServicioDataSource.GetAllTipoServiciosCallback, OnMapReadyCallback {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private FragmentCrearRequerimientoBinding binding;
    private Spinner spinner;
    private ArrayAdapter<CharSequence> adapterRubro;
    private Context ctx= this.getContext();
    //cosas para el mapa
    private GoogleMap mapa;
    private ActivityResultLauncher<String> activityResultLauncher;
    private boolean permitido = false;
    private boolean ubicacionOk = false;
    //cosas para la foto
    private ActivityResultLauncher<String> fotoGetter;
    private Uri fotoSeleccionada;

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
                            .setPositiveButton("Entendido", (dialogInterface, i) -> {
                                activityResultLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION);
                            })
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

    public static CrearRequerimientoFragment newInstance(String param1, String param2) {
        CrearRequerimientoFragment fragment = new CrearRequerimientoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //verificar permisos
        activityResultLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION);

        binding = FragmentCrearRequerimientoBinding.inflate(inflater, container, false);
        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Crear requerimiento");

        spinner = binding.spinnerRurbos;
        adapterRubro = new ArrayAdapter<>(this.getContext(),
                android.R.layout.simple_spinner_item, new ArrayList<>());
        adapterRubro.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapterRubro);

        binding.buttonAgregarFoto.setOnClickListener(view -> {
            fotoGetter.launch("image/*");
        });

        binding.buttonPublicar.setOnClickListener(view -> {
            if (!permitido){
                Toast.makeText(getActivity(), "Se requieren los permisos de ubicacion para continuar.", Toast.LENGTH_LONG).show();
                return;
            }
            if(!ubicacionOk){
                Toast.makeText(getActivity(), "Actualice su ubicacion para continuar.", Toast.LENGTH_LONG).show();
                return;
            }

            //TODO logica de guardar el requerimiento
            Log.i("REQ_FRAGMENT","REQUERIMIENTO CREADO");
            NavHostFragment.findNavController(this).navigate(R.id.action_crearRequerimientoFragment_to_menuPpalFragment2);
        });

        binding.buttonDescartar.setOnClickListener(view -> {
            NavHostFragment.findNavController(this).navigate(R.id.action_crearRequerimientoFragment_to_resultadosServiciosFragment);
        });

        binding.buttonActualizarMap.setOnClickListener(new View.OnClickListener() {
            @Override
            @RequiresPermission(Manifest.permission.ACCESS_FINE_LOCATION)
            public void onClick(View view) {
                actualizarUbicacion();
            }
        });

        //seria mejor guardar en la actividad los tipos servicios cuando los busco para el menu ppal?
        TipoServicioRepository.createInstance().getAllTipoServicios(this);

        //init mapa
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.google_map);
        mapFragment.getMapAsync(this);

        return binding.getRoot();
    }

    // GetAllTipoServicioCallback
    @Override
    public void onError() {

    }

    @Override
    public void onResult(List<TipoServicio> tipos) {
        adapterRubro.clear();
        tipos.add(TipoServicioRepository.OTRO);
        adapterRubro.addAll(tipos.stream().map(TipoServicio::getNombre).collect(Collectors.toList()));
    }

    @Override
    @RequiresPermission(Manifest.permission.ACCESS_FINE_LOCATION)
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mapa = googleMap;

        if (permitido)
            actualizarUbicacion();
    }

    @RequiresPermission(Manifest.permission.ACCESS_FINE_LOCATION)
    public void actualizarUbicacion(){

        mapa.setMyLocationEnabled(true);
        LocationManager locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
        Location currLoc = locationManager.getLastKnownLocation(locationManager.getBestProvider(new Criteria(), false));

        if(currLoc != null){
            LatLng pos = new LatLng(currLoc.getLatitude(), currLoc.getLongitude());
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

}