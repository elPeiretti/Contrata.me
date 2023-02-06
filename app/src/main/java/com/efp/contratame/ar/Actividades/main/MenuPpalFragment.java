package com.efp.contratame.ar.Actividades.main;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.efp.contratame.ar.R;
import com.efp.contratame.ar.databinding.FragmentMenuPpalBinding;
import com.efp.contratame.ar.modelo.TipoServicio;
import com.efp.contratame.ar.persistencia.datasource.TipoServicioDataSource;
import com.efp.contratame.ar.persistencia.repository.TipoServicioRepository;

import java.util.ArrayList;
import java.util.List;

public class MenuPpalFragment extends Fragment implements ServicioIconRecyclerAdapter.OnTipoServicioSelectedListener, SearchView.OnQueryTextListener, TipoServicioDataSource.GetAllTipoServiciosCallback {

    private FragmentMenuPpalBinding binding;
    private ServicioIconRecyclerAdapter rvAdapter;
    private onTipoServicioSelectedListener listener;
    private RecyclerView rv;

    public MenuPpalFragment(){}

    public static MenuPpalFragment newInstance(){
        MenuPpalFragment frag = new MenuPpalFragment();
        //aca setear argumentos en caso de requerirlos
        return frag;
    }

    public interface onTipoServicioSelectedListener {
        public void onTipoServicioSelected(TipoServicio tp);
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        if (context instanceof onTipoServicioSelectedListener){
            listener = (onTipoServicioSelectedListener) context;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity()).getSupportActionBar().setTitle("¿Qué necesitás?");
    }



    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedINstanceState){
        binding = FragmentMenuPpalBinding.inflate(inflater,container,false);



        //setup recyclerview
        rv = binding.rvServicios;
        rv.setLayoutManager(new GridLayoutManager(getContext(),3));
        TipoServicioRepository.createInstance().getAllTipoServicios(this);

        SearchView txtBusqueda = binding.txBuscar;
        txtBusqueda.setOnQueryTextListener(this);


        rvAdapter = new ServicioIconRecyclerAdapter(getContext(), new ArrayList<>(), this);
        rv.setAdapter(rvAdapter);

        int spacing = getResources().getDimensionPixelSize(R.dimen.recycler_spacing)/2;
        rv.setPadding(spacing, 0, spacing, 0);
        rv.setClipToPadding(false);
        rv.setClipChildren(false);
        rv.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state){
                outRect.set(spacing,spacing*2,spacing,spacing*2);
            }
        });

        txtBusqueda = binding.txBuscar;
        txtBusqueda.setOnQueryTextListener(this);

        return binding.getRoot();
    }



    @Override
    public void onTipoServicioSelected(TipoServicio tp) {
        listener.onTipoServicioSelected(tp);
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        rvAdapter.filtrado(s);
        //return true;

       return false;
    }


    //metodos de TipoServicioDataSource.GetAllTipoServiciosCallback
    @Override
    public void onError() {
        //TODO
        Log.e("API_REST","ERROR CARGANDO LOS TIPOS DE SERVICIO");
    }

    @Override
    public void onResult(List<TipoServicio> tipos) {
        tipos.add(TipoServicioRepository.OTRO);
        rvAdapter.updateData(tipos);
    }
}
