package com.efp.contratame.ar.Actividades;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.efp.contratame.ar.Actividades.main.MainActivity;
import com.efp.contratame.ar.Actividades.main.TipoServicioGetter;
import com.efp.contratame.ar.Actividades.main.UsuarioGetter;
import com.efp.contratame.ar.R;
import com.efp.contratame.ar.adapters.MisServiciosRecyclerAdapter;
import com.efp.contratame.ar.auxiliares.MyViewModelRequerimiento;
import com.efp.contratame.ar.auxiliares.MisServiciosSelectListener;
import com.efp.contratame.ar.databinding.FragmentMisServiciosBinding;
import com.efp.contratame.ar.modelo.Requerimiento;
import com.efp.contratame.ar.modelo.TipoServicio;
import com.efp.contratame.ar.persistencia.datasource.RequerimientoDataSource;
import com.efp.contratame.ar.persistencia.repository.RequerimientoRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MisServiciosFragment extends Fragment implements RequerimientoDataSource.GetAllRequerimientosFromCallback, MisServiciosSelectListener, RequerimientoDataSource.EliminarRequerimientoCallback {

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
        /*if (context instanceof OnRequerimientoSelectedListener){
            requerimientoSelectedListener = (OnRequerimientoSelectedListener) context;
        }*/
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
        mAdapter= new MisServiciosRecyclerAdapter(new ArrayList<>(), ctx,this);
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

    @Override
    public void onResult() {
        Log.i("elimina", "elimina");
        mAdapter.removeItem(viewModel.getSelected().getValue());
        Toast.makeText(getActivity(), "Requerimiento eliminado exitosamente", Toast.LENGTH_LONG).show();
        //hay que ver si se quedo sin requerimientos
        recyclerView.setVisibility(mAdapter.getItemCount() == 0 ? View.GONE : View.VISIBLE);
        binding.tvMensajeEmpty.setVisibility(mAdapter.getItemCount() == 0 ? View.VISIBLE : View.GONE);

    }

    @Override
    public void navigateToModificar(Requerimiento req) {
        viewModel.setSelected(req);
        NavHostFragment.findNavController(MisServiciosFragment.this).navigate(R.id.action_misServiciosFragment_to_modificarRequerimientoFragment);
    }

    @Override
    public void navigateToEliminar(Requerimiento req) {
        viewModel.setSelected(req);
        RequerimientoRepository.createInstance().eliminarRequerimiento(req, user.getCurrentUsuario().getIdUsuario(),this);

       /* //cancelar alarma
        Intent alarmIntent = new Intent(this.ctx, MainActivity.class);
        alarmIntent.putExtra("idRequerimiento", req.getIdRequerimiento());
        alarmIntent.putExtra("idUsuario", user.getCurrentUsuario().getIdUsuario());
        alarmIntent.putExtra("mail", user.getCurrentUsuario().getEmail());
        alarmIntent.putExtra("nombre", user.getCurrentUsuario().getNombre());
        alarmIntent.putExtra("foto", user.getCurrentUsuario().getFoto_perfil());
        alarmIntent.putExtra("sesion", user.getCurrentUsuario().getTipoSesion());
        alarmIntent.putExtra("fragment", "presiona eliminar");

        AlarmManager alarmManager = (AlarmManager) ctx.getSystemService(Context.ALARM_SERVICE);
        PendingIntent displayIntent = PendingIntent.getBroadcast(ctx.getApplicationContext(), 1, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_ONE_SHOT | PendingIntent.FLAG_IMMUTABLE);
        alarmManager.cancel(displayIntent);
*/
    }
}