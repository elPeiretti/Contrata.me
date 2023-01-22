package com.efp.contratame.ar.Actividades;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.efp.contratame.ar.Actividades.main.MainActivity;
import com.efp.contratame.ar.adapters.GaleriaRecyclerAdapter;
import com.efp.contratame.ar.auxiliares.MyViewModel;
import com.efp.contratame.ar.databinding.FragmentDetalleProveedorServicioBinding;


import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetalleProveedorServicioFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetalleProveedorServicioFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private FragmentDetalleProveedorServicioBinding binding;
    private RecyclerView recyclerView;
    private GaleriaRecyclerAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private Context ctx=this.getContext();
    private List<String> galeriImagenes = new ArrayList<>();

    public DetalleProveedorServicioFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DetalleProveedorServicioFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DetalleProveedorServicioFragment newInstance(String param1, String param2) {
        DetalleProveedorServicioFragment fragment = new DetalleProveedorServicioFragment();
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
        binding = FragmentDetalleProveedorServicioBinding.inflate(inflater, container, false);
        ((MainActivity) getActivity()).getSupportActionBar().setTitle("");

        MyViewModel viewModel = new ViewModelProvider(requireActivity()).get(MyViewModel.class);
        viewModel.getSelected().observe(getViewLifecycleOwner(), item -> {
            binding.tvNombre.setText(item.getPrestador().getNombre());
            binding.ratingBar2.setRating(item.getPuntuacion());
            String EDteamImage = item.getPrestador().getImagen_perfil();
            Glide.with(binding.imageView.getContext()).load(EDteamImage).into(binding.imageView);
            binding.tvDescripcion.setText(item.getDescripcion());
            galeriImagenes = item.getGaleriaImagenes();
        });

       /* Intent intent = this.getIntent();
        Bundle extra = intent.getExtras();

        binding.tvNombre.setText(extra.getString("nombre"));
        binding.ratingBar2.setRating(extra.getFloat("puntuacion"));
        String EDteamImage = extra.getString("imagen");
        Glide.with(binding.imageView.getContext()).load(EDteamImage).into(binding.imageView);
        binding.tvDescripcion.setText(extra.getString("descripcion"));

        */


    //    mAdapter= new GaleriaRecyclerAdapter((ArrayList<String>) getIntent().getSerializableExtra("listaImagenes"), ctx);


        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        mAdapter= new GaleriaRecyclerAdapter(galeriImagenes, ctx);
        recyclerView = binding.recyclerImagenes;
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(ctx, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);
    }
}