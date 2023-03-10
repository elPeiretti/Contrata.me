package com.efp.contratame.ar.Actividades.main.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Spinner;

import com.efp.contratame.ar.Actividades.main.MainActivity;
import com.efp.contratame.ar.Actividades.main.TipoServicioGetter;
import com.efp.contratame.ar.R;
import com.efp.contratame.ar.misc.EspressoIdlingResource;
import com.efp.contratame.ar.persistencia.datasource.ServicioDataSource;
import com.efp.contratame.ar.persistencia.repository.ServicioRepository;
import com.efp.contratame.ar.Actividades.main.adapters.ServiciosRecyclerAdapter;
import com.efp.contratame.ar.Actividades.main.misc.MyViewModel;
import com.efp.contratame.ar.Actividades.main.misc.SelectListener;
import com.efp.contratame.ar.databinding.FragmentResultadosServiciosBinding;
import com.efp.contratame.ar.modelo.Servicio;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ResultadosServiciosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ResultadosServiciosFragment extends Fragment implements SearchView.OnQueryTextListener, SelectListener, ServicioDataSource.GetAllServiciosDelTipoCallback {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private FragmentResultadosServiciosBinding binding;
    private RecyclerView recyclerView;
    private ServiciosRecyclerAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private Context ctx= this.getContext();
    private SearchView txtBusqueda;
    private Spinner spinner;
    private MyViewModel viewModel;

    private TipoServicioGetter getterServicio;
    private OnServicioSelectedListener servicioSelectedListener;
    private ProgressBar barra;

    public ResultadosServiciosFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ResultadosServiciosFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ResultadosServiciosFragment newInstance(String param1, String param2) {
        ResultadosServiciosFragment fragment = new ResultadosServiciosFragment();
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
        if (context instanceof TipoServicioGetter){
            getterServicio = (TipoServicioGetter) context;
        }
        if (context instanceof OnServicioSelectedListener){
            servicioSelectedListener = (OnServicioSelectedListener) context;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentResultadosServiciosBinding.inflate(inflater, container, false);
        ((MainActivity) getActivity()).getSupportActionBar().setTitle(getterServicio.getTipoSeleccionado().getNombre());
        /*TODO PONER EL NOMBRE DEL GRUPO QUE ELIGE
            esto esta aca:
            getterServicio.getTipoSeleccionado().getNombre()
         */

        viewModel = new ViewModelProvider(requireActivity()).get(MyViewModel.class);

        //Funcionalidad de filtrado
        //TODO

        //Funcionalidad de b??squeda
        txtBusqueda = binding.txtBusqueda;
        txtBusqueda.setOnQueryTextListener(this);

        //Funcionalidad de orden
        spinner= binding.spinner;
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                mAdapter.ordenar(adapterView.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        binding.btnCrearRequerimiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(ResultadosServiciosFragment.this).navigate(R.id.action_resultadosServiciosFragment_to_crearRequerimientoFragment);
            }
        });

        //esto es para que no tire error cuando tardan en llegar los datos de retrofit
        //------mAdapter = new ServiciosRecyclerAdapter(List.of(), ctx, this);

        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        //RECYCLERVIEW
        barra = binding.progressBarCrearReq;
        barra.setVisibility(View.VISIBLE);
        //Carga de servicios
        mAdapter= new ServiciosRecyclerAdapter(new ArrayList<>(), ctx, this);
        EspressoIdlingResource.getInstance().increment(); // Se usa para testing
        ServicioRepository.createInstance().getAllServiciosDelTipo(getterServicio.getTipoSeleccionado(),this);

        recyclerView = binding.recyclerServicios;
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(ctx);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);
        DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),1);
        recyclerView.addItemDecoration(mDividerItemDecoration);

    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        mAdapter.filtrado(s);
        return false;
    }

    public interface OnServicioSelectedListener {
        void onServicioSelected(Servicio s);
    }

    @Override
    public void onItemClicked(Servicio s) {
        viewModel.setSelected(s);
        servicioSelectedListener.onServicioSelected(s);
        NavHostFragment.findNavController(ResultadosServiciosFragment.this).navigate(R.id.action_resultadosServiciosFragment_to_detalleProveedorServicioFragment2);

    }

    // METODOS DE GetAllServiciosDelTipoCallback
    @Override
    public void onResult(List<Servicio> servicios) {
        barra.setVisibility(View.GONE);
        recyclerView.setVisibility(servicios.isEmpty() ? View.GONE : View.VISIBLE);
        binding.tvMensajeEmpty.setVisibility(servicios.isEmpty() ? View.VISIBLE : View.GONE);
        mAdapter.updateData(servicios);
        EspressoIdlingResource.getInstance().decrement(); // Se usa para testing
    }

    @Override
    public void onError() {
        //TODO
        Log.e("ERROR_RETROFIT","No se pudieron cargar los servicios");
    }

}