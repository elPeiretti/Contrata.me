package com.efp.contratame.ar.Actividades;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.efp.contratame.ar.Actividades.main.MainActivity;
import com.efp.contratame.ar.Actividades.main.ServicioIconRecyclerAdapter;
import com.efp.contratame.ar.adapters.GaleriaRecyclerAdapter;
import com.efp.contratame.ar.auxiliares.MyViewModel;
import com.efp.contratame.ar.databinding.FragmentDetalleProveedorServicioBinding;
import com.efp.contratame.ar.modelo.Comentario;
import com.efp.contratame.ar.persistencia.datasource.ComentarioDataSource;
import com.efp.contratame.ar.persistencia.repository.ComentarioRepository;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetalleProveedorServicioFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetalleProveedorServicioFragment extends Fragment implements ComentarioDataSource.GetAllComentariosDeServicioCallback {

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
    private UUID idServicioSeleccionado;

    private RecyclerView rvComentarios;
    private ComentarioRecyclerAdapter adapterComentario;

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
            String EDteamImage = item.getPrestador().getImagenPerfil();
            Glide.with(binding.imageView.getContext()).load(EDteamImage).into(binding.imageView);
            binding.tvDescripcion.setText(item.getDescripcion());
            galeriImagenes = item.getGaleriaImagenes();
        });

        rvComentarios = binding.rvComentarios;
        rvComentarios.setHasFixedSize(true);
        rvComentarios.setLayoutManager(new LinearLayoutManager(getContext()));
        rvComentarios.setAdapter(adapterComentario = new ComentarioRecyclerAdapter(getContext(),new ArrayList<>()));

        ComentarioRepository.createInstance().getAllComentariosDeServicio(UUID.fromString("49566de5-fd7d-43e4-b537-a097a54f1f87"),this);

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

    // metodos del callback
    @Override
    public void onResult(List<Comentario> comentarioList) {
        adapterComentario.updateData(comentarioList);
    }

    @Override
    public void onError() {
        Log.e("ERROR_RETROFIT","No se pudieron cargar los comentarios");
    }
}