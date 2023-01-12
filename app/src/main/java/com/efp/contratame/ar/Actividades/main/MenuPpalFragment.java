package com.efp.contratame.ar.Actividades.main;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.efp.contratame.ar.R;
import com.efp.contratame.ar.databinding.FragmentMenuPpalBinding;
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
        RecyclerView rv = binding.rvServicios;
        rv.setLayoutManager(new GridLayoutManager(getContext(),3));
        rvAdapter = new ServicioIconRecyclerAdapter(getContext(), ServicioRepository._SERVICIOS);
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



        return binding.getRoot();
    }
}
