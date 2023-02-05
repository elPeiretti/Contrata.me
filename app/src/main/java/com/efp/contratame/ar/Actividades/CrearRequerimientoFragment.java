package com.efp.contratame.ar.Actividades;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
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
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CrearRequerimientoFragment extends Fragment implements TipoServicioDataSource.GetAllTipoServiciosCallback, OnMapReadyCallback {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private FragmentCrearRequerimientoBinding binding;
    private Spinner spinner;
    private ArrayAdapter<CharSequence> adapterRubro;
    private Context ctx= this.getContext();

    private GoogleMap mapa;
    private ActivityResultLauncher<String> activityResultLauncher;

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
                } else {
                    NavHostFragment.findNavController(ctx).navigate(R.id.action_crearRequerimientoFragment_to_resultadosServiciosFragment);
                    Toast.makeText(getActivity(), "Se requieren los permisos de ubicacion para continuar.", Toast.LENGTH_LONG).show();
                }
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
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
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mapa = googleMap;

        //mapa.getUiSettings().setAllGesturesEnabled(false);
        mapa.setMyLocationEnabled(true);
    }
}