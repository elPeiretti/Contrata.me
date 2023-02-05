package com.efp.contratame.ar.Actividades;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.efp.contratame.ar.Actividades.main.MainActivity;
import com.efp.contratame.ar.databinding.FragmentCrearRequerimientoBinding;

public class CrearRequerimientoFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private FragmentCrearRequerimientoBinding binding;
    private Spinner spinner;
    private ArrayAdapter<CharSequence> adapterRubro;
    private Context ctx= this.getContext();
    final String[] arrayRubros = {"Instalaciones", "Construcción", "Aire acondicionado", "Carpintería", "Pintura", "Plomería",
            "Electricidad", "Limpieza", "Jardinería", "Costurería", "Gasista", "Cerrajería", "Transporte",
            "Belleza", "Seguridad"};

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
        adapterRubro = new ArrayAdapter (this.getContext(),
                android.R.layout.simple_spinner_item,arrayRubros);
        adapterRubro.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
      //  adapterRubro = new ArrayAdapter<>(ctx, android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.rubros));
        spinner.setAdapter(adapterRubro);
        return binding.getRoot();
    }


}