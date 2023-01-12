package com.efp.contratame.ar.Actividades.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import com.efp.contratame.ar.databinding.FragmentMenuPpalBinding;
import com.efp.contratame.ar.modelo.Servicio;
import com.efp.contratame.ar.persistencia.repository.ServicioRepository;

public class MenuPpalFragment extends Fragment {

    private FragmentMenuPpalBinding binding;
    private ServicioIconRecyclerAdapter rvAdapter;

    public MenuPpalFragment(){}

    public static MenuPpalFragment newInstance(){
        MenuPpalFragment frag = new MenuPpalFragment();
        //aca setear argumentos en caso de requerirlos
        return frag;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedINstanceState){
        binding = FragmentMenuPpalBinding.inflate(inflater,container,false);

        //setup recyclerview
        binding.rvServicios.setLayoutManager(new GridLayoutManager(getContext(),3));
        rvAdapter = new ServicioIconRecyclerAdapter(getContext(), ServicioRepository.SERVICIO_LIST);
        binding.rvServicios.setAdapter(rvAdapter);


        return binding.getRoot();
    }
}
