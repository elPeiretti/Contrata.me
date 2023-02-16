package com.efp.contratame.ar.Actividades;

import android.Manifest;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresPermission;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.bumptech.glide.Glide;
import com.efp.contratame.ar.Actividades.main.MainActivity;
import com.efp.contratame.ar.Actividades.main.TipoServicioGetter;
import com.efp.contratame.ar.Actividades.main.UsuarioGetter;
import com.efp.contratame.ar.R;
import com.efp.contratame.ar.auxiliares.MyViewModelRequerimiento;
import com.efp.contratame.ar.databinding.FragmentModificarRequerimientoBinding;
import com.efp.contratame.ar.modelo.Requerimiento;
import com.efp.contratame.ar.modelo.TipoServicio;
import com.efp.contratame.ar.persistencia.datasource.RequerimientoDataSource;
import com.efp.contratame.ar.persistencia.datasource.TipoServicioDataSource;
import com.efp.contratame.ar.persistencia.repository.RequerimientoRepository;
import com.efp.contratame.ar.persistencia.repository.TipoServicioRepository;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

public class ModificarRequerimientoFragment extends Fragment implements TipoServicioDataSource.GetAllTipoServiciosCallback,
        OnMapReadyCallback, RequerimientoDataSource.SaveRequerimientoCallback {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private FragmentModificarRequerimientoBinding binding;
    private Spinner spinner;
    private ArrayAdapter<TipoServicio> adapterRubro;
    private Context ctx= this.getContext();
    private UsuarioGetter usuarioGetter;
    private TipoServicioGetter tipoServicioGetter;
    private Requerimiento nuevo_requerimiento;
    private Requerimiento requerimiento_previo;
    //cosas para el mapa
    private GoogleMap mapa;
    private ActivityResultLauncher<String> activityResultLauncher;
    private boolean permitido = false;
    private boolean ubicacionOk = false;
    private LatLng pos;
    //cosas para la foto
    private final ActivityResultLauncher<String> fotoGetter;
    private Uri fotoSeleccionada;
    private boolean modifica_imagen = false;
    private Bitmap foto;

    public ModificarRequerimientoFragment() {

        ModificarRequerimientoFragment ctx = this;

        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), result -> {
            if (!result) {
                if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
                    Log.i("PERMISOS", "HAY QUE PEDIR PERMISOS NUEVAMENTE");
                    new AlertDialog.Builder(getActivity())
                            .setTitle("Permisos requeridos")
                            .setMessage("Para poder crear un nuevo requerimiento," +
                                    " necesitamos conocer tu ubicacion para que los" +
                                    " prestadores puedan saber donde se debe realizar el trabajo.")
                            .setPositiveButton("Entendido", (dialogInterface, i) -> {
                                activityResultLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION);
                            })
                            .setNegativeButton("Volver", (dialogInterface, i) -> {
                                NavHostFragment.findNavController(ctx).navigate(R.id.action_crearRequerimientoFragment_to_resultadosServiciosFragment);
                                Toast.makeText(getActivity(), "Se requieren los permisos de ubicacion para continuar.", Toast.LENGTH_LONG).show();
                            })
                            .create()
                            .show();
                }
                permitido = false;
            }
            else{
                permitido = true;
            }
        });

        fotoGetter = registerForActivityResult(new ActivityResultContracts.GetContent(), result -> {
            if (result != null) {
                fotoSeleccionada = result;
                binding.buttonAgregarFoto.setImageURI(result);
            }
            else if (fotoSeleccionada != null){
                binding.buttonAgregarFoto.setImageURI(fotoSeleccionada);
            }
            /*else {
                binding.buttonAgregarFoto.setImageBitmap(requerimiento_previo.getImagen());
            }*/
        });
    }

    public static ModificarRequerimientoFragment newInstance(String param1, String param2) {
        ModificarRequerimientoFragment fragment = new ModificarRequerimientoFragment();
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
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof UsuarioGetter)
            usuarioGetter = (UsuarioGetter) context;
        if (context instanceof TipoServicioGetter)
            tipoServicioGetter = (TipoServicioGetter) context;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //verificar permisos
        activityResultLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION);

        binding = FragmentModificarRequerimientoBinding.inflate(inflater, container, false);
        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Modificar requerimiento");

        spinner = binding.spinnerRurbos;
        adapterRubro = new ArrayAdapter<>(this.getContext(),
                android.R.layout.simple_spinner_item, new ArrayList<>());
        adapterRubro.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapterRubro);
        TipoServicioRepository.createInstance().getAllTipoServicios(this);

        binding.buttonAgregarFoto.setOnClickListener(view -> {
            modifica_imagen = true;
            fotoGetter.launch("image/*");
        });

        //init mapa
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.google_map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);


        binding.tituloEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                binding.tituloInputLayout
                        .setError(editable.toString().isEmpty() ? "Este campo no puede estar vacio." : null);
            }
        });

        binding.descripcionEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                binding.descripcionInputLayout
                        .setError(editable.toString().isEmpty() ? "Este campo no puede estar vacio." : null);
            }
        });

        binding.buttonPublicar.setOnClickListener(view -> {
            if (!permitido){
                Toast.makeText(getActivity(), "Se requieren los permisos de ubicacion para continuar.", Toast.LENGTH_LONG).show();
                return;
            }
            if(!ubicacionOk){
                Toast.makeText(getActivity(), "Actualice su ubicacion para continuar.", Toast.LENGTH_LONG).show();
                return;
            }
            if(!tituloDescripcionAreOk()){
                Toast.makeText(getActivity(), "Asegurese de completar todo para continuar.", Toast.LENGTH_LONG).show();
                return;
            }

            try {
                setLoadingScreen(View.VISIBLE);
                if(!modifica_imagen) foto = requerimiento_previo.getImagen();
                if(fotoSeleccionada==null){
                    foto = requerimiento_previo.getImagen();
                }
                else{
                    foto = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(),fotoSeleccionada);
                }
                nuevo_requerimiento = new Requerimiento(
                        requerimiento_previo.getIdRequerimiento(),
                        binding.tituloEditText.getText().toString(),
                        (TipoServicio) binding.spinnerRurbos.getSelectedItem(),
                        binding.descripcionEditText.getText().toString(),
                        foto,
                        pos
                );
                RequerimientoRepository.createInstance().saveRequerimiento(nuevo_requerimiento,usuarioGetter.getCurrentUsuario().getIdUsuario(),this);
                Log.i("actualiza", "actualiza");
            } catch (IOException e) {
                Log.e("error cargando la imagen",e.getMessage());
                Toast.makeText(getActivity(), "Ha ocurrido un error, intentelo nuevamente.", Toast.LENGTH_LONG).show();
            }

        });

        binding.buttonDescartar.setOnClickListener(view -> {
            NavHostFragment.findNavController(this).navigate(R.id.action_modificarRequerimientoFragment_to_misServiciosFragment);
        });

        binding.buttonActualizarMap.setOnClickListener(new View.OnClickListener() {
            @Override
            @RequiresPermission(Manifest.permission.ACCESS_FINE_LOCATION)
            public void onClick(View view) {
                actualizarUbicacion();
            }
        });

        MyViewModelRequerimiento viewModel = new ViewModelProvider(requireActivity()).get(MyViewModelRequerimiento.class);
        viewModel.getSelected().observe(getViewLifecycleOwner(), item -> {
            requerimiento_previo=item;
            binding.tituloEditText.setText(item.getTitulo());
            binding.descripcionEditText.setText(item.getDescripcion());
            binding.buttonAgregarFoto.setImageBitmap(item.getImagen());
        });

        return binding.getRoot();
    }

    private void selectValue(Spinner spinner, Object value) {
        Log.i("rubro1", value.toString());
        for (int i = 0; i < spinner.getCount(); i++) {
            Log.i("rubro2", spinner.getItemAtPosition(i).toString());

            if (spinner.getItemAtPosition(i).equals(value)) {
                spinner.setSelection(i);
                break;
            }
        }
    }

    // GetAllTipoServicioCallback
    @Override
    public void onResult(List<TipoServicio> tipos) {
        setLoadingScreen(View.GONE);
        adapterRubro.clear();
        tipos.add(TipoServicioRepository.OTRO);
        adapterRubro.addAll(tipos);
        Log.i("rubro0", String.valueOf(spinner.getCount()));
        binding.spinnerRurbos.setSelection(adapterRubro.getPosition(requerimiento_previo.getRubro()),true);
    }

    // SaveRequerimientoCallback
    @Override
    public void onResult() {
        Log.i("REQ_FRAGMENT","REQUERIMIENTO CREADO");
        NavHostFragment.findNavController(this).navigate(R.id.action_modificarRequerimientoFragment_to_misServiciosFragment);
        Toast.makeText(getActivity(), "Requerimiento actualizado exitosamente", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onError() {
        Log.e("ERROR-RETROFIT","SE HA PRODUCIDO UN ERROR CARGANDO/GUARDANDO DATOS");
    }

    @Override
    @RequiresPermission(Manifest.permission.ACCESS_FINE_LOCATION)
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mapa = googleMap;
        cargarUbicacionPrevia(requerimiento_previo);
    }

    public void cargarUbicacionPrevia(Requerimiento req){
        pos = new LatLng(req.getUbicacion().latitude, req.getUbicacion().longitude);
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(pos)
                .zoom(14.5f)
                .bearing(90)
                .tilt(40)
                .build();
        mapa.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        ubicacionOk = true;
    }

    @RequiresPermission(Manifest.permission.ACCESS_FINE_LOCATION)
    public void actualizarUbicacion(){

        mapa.setMyLocationEnabled(true);
        LocationManager locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
        Location currLoc = locationManager.getLastKnownLocation(locationManager.getBestProvider(new Criteria(), false));
        if(requerimiento_previo!= null){

        }
        if(currLoc != null){
            pos = new LatLng(currLoc.getLatitude(), currLoc.getLongitude());
            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(pos)
                    .zoom(14.5f)
                    .bearing(90)
                    .tilt(40)
                    .build();
            mapa.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            ubicacionOk = true;
        }
        else{
            Toast.makeText(getActivity(), "Por favor, active la ubicacion.", Toast.LENGTH_LONG).show();
            ubicacionOk = false;
        }
    }

    public boolean tituloDescripcionAreOk(){
        boolean ok = true;
        if(binding.tituloEditText.getText().toString().isEmpty()) {
            ok = false;
            binding.tituloInputLayout.setError("Este campo no puede estar vacio.");
        }
        if(binding.descripcionEditText.getText().toString().isEmpty()) {
            ok = false;
            binding.descripcionInputLayout.setError("Este campo no puede estar vacio.");
        }

        return ok;
    }
    public void setLoadingScreen(int status) {
        binding.progressBarModificarReq.setVisibility(status);
        binding.scrollCrearReq.setVisibility(status == View.VISIBLE ? View.GONE : View.VISIBLE);
    }

}