package com.efp.contratame.ar.Actividades.main;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.efp.contratame.ar.R;
import com.efp.contratame.ar.databinding.FragmentMenuPpalBinding;
import com.efp.contratame.ar.modelo.TipoServicio;
import com.efp.contratame.ar.persistencia.repository.TipoServicioRepository;

public class MenuPpalFragment extends Fragment implements ServicioIconRecyclerAdapter.OnTipoServicioSelectedListener, SearchView.OnQueryTextListener{

    private FragmentMenuPpalBinding binding;
    private ServicioIconRecyclerAdapter rvAdapter;
    private onTipoServicioSelectedListener listener;

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

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedINstanceState){
        binding = FragmentMenuPpalBinding.inflate(inflater,container,false);

        ((MainActivity) getActivity()).getSupportActionBar().setTitle("¿Qué necesitás?");

        //setup recyclerview
        RecyclerView rv = binding.rvServicios;
        rv.setLayoutManager(new GridLayoutManager(getContext(),3));
        rvAdapter = new ServicioIconRecyclerAdapter(getContext(), TipoServicioRepository._TIPOSERVICIOS, this);
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


        SearchView txtBusqueda = binding.txtBusqueda;
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
        return false;
    }
}
