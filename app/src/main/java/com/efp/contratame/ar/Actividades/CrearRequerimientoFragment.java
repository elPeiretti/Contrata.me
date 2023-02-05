package com.efp.contratame.ar.Actividades;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.efp.contratame.ar.Actividades.main.MainActivity;
import com.efp.contratame.ar.R;
import com.efp.contratame.ar.databinding.FragmentCrearRequerimientoBinding;
import com.efp.contratame.ar.modelo.TipoServicio;
import com.efp.contratame.ar.persistencia.datasource.TipoServicioDataSource;
import com.efp.contratame.ar.persistencia.repository.TipoServicioRepository;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

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
    GoogleMap mapa;

    public CrearRequerimientoFragment() {
        // Required empty public constructor
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
    }
}