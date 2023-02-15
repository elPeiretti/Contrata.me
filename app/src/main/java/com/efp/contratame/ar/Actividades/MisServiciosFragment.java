package com.efp.contratame.ar.Actividades;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.efp.contratame.ar.Actividades.main.MainActivity;
import com.efp.contratame.ar.Actividades.main.UsuarioGetter;
import com.efp.contratame.ar.R;
import com.efp.contratame.ar.adapters.MisServiciosRecyclerAdapter;
import com.efp.contratame.ar.auxiliares.MyViewModelRequerimiento;
import com.efp.contratame.ar.databinding.FragmentMisServiciosBinding;
import com.efp.contratame.ar.modelo.Requerimiento;
import com.efp.contratame.ar.persistencia.datasource.RequerimientoDataSource;
import com.efp.contratame.ar.persistencia.repository.RequerimientoRepository;

import java.util.ArrayList;
import java.util.List;

public class MisServiciosFragment extends Fragment implements RequerimientoDataSource.GetAllRequerimientosFromCallback {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private FragmentMisServiciosBinding binding;
    private RecyclerView recyclerView;
    private MisServiciosRecyclerAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private Context ctx= this.getContext();
    private MyViewModelRequerimiento viewModel;
    private UsuarioGetter user;
    private ProgressBar barra;
    private int progress;
    private Handler handler = new Handler();
    boolean bool_callback1 = false;

    public MisServiciosFragment() {}

    public static MisServiciosFragment newInstance(String param1, String param2) {
        MisServiciosFragment fragment = new MisServiciosFragment();
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
    public void onAttach(Context context){
        super.onAttach(context);
        if (context instanceof UsuarioGetter) {
            user = (UsuarioGetter) context;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMisServiciosBinding.inflate(inflater, container, false);
        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Mis servicios");

        viewModel = new ViewModelProvider(requireActivity()).get(MyViewModelRequerimiento.class);
        binding.btnCrearRequerimiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(MisServiciosFragment.this).navigate(R.id.action_misServiciosFragment_to_crearRequerimientoFragment);
            }
        });
        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        mAdapter= new MisServiciosRecyclerAdapter(new ArrayList<>(), ctx);
        barra = binding.progressBarCrearReq;
        barra.setVisibility(View.VISIBLE);
        RequerimientoRepository.createInstance().getAllRequerimientosFrom(user.getCurrentUsuario().getIdUsuario(),this);
        recyclerView = binding.recyclerMisServicios;
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(ctx);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);
        DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),1);
        recyclerView.addItemDecoration(mDividerItemDecoration);
    }

    @Override
    public void onResult(List<Requerimiento> requerimientoList) {
        barra.setVisibility(View.GONE);
        recyclerView.setVisibility(requerimientoList.isEmpty() ? View.GONE : View.VISIBLE);
        binding.tvMensajeEmpty.setVisibility(requerimientoList.isEmpty() ? View.VISIBLE : View.GONE);
        mAdapter.updateData(requerimientoList);
    }

    @Override
    public void onError() {
        //TODO
        Log.e("ERROR_RETROFIT","No se pudieron cargar los requerimientos");
    }
}