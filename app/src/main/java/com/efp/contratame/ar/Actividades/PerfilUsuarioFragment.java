package com.efp.contratame.ar.Actividades;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.efp.contratame.ar.Actividades.main.MainActivity;
import com.efp.contratame.ar.Actividades.main.MenuPpalFragment;
import com.efp.contratame.ar.Actividades.main.UsuarioGetter;
import com.efp.contratame.ar.R;
import com.efp.contratame.ar.databinding.FragmentPerfilUsuarioBinding;
import com.efp.contratame.ar.modelo.Usuario;
import com.facebook.AccessToken;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.auth.UserProfileChangeRequest;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PerfilUsuarioFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PerfilUsuarioFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private FragmentPerfilUsuarioBinding binding;
    private Context ctx = this.getContext();
    private UsuarioGetter usuarioGetter;

    public PerfilUsuarioFragment() {
        // Required empty public constructor
    }

    public static PerfilUsuarioFragment newInstance(String param1, String param2) {
        PerfilUsuarioFragment fragment = new PerfilUsuarioFragment();
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
    public void onAttach(@NonNull Context context){
        super.onAttach(context);
        if (context instanceof UsuarioGetter){
            usuarioGetter = (UsuarioGetter) context;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ((MainActivity) getActivity()).getSupportActionBar().setTitle("");

        binding = FragmentPerfilUsuarioBinding.inflate(inflater, container, false);
        ctx = this.getContext();

        Usuario user = usuarioGetter.getCurrentUsuario();
        binding.tvNombrePerfil.setText(user.getNombre());
        binding.tvEmailUsuario.setText(user.getEmail());
        String tipo = user.getTipoSesion();
        if(tipo.equalsIgnoreCase("google.com")){
            binding.tvTipoSesion.setText("Google");
        }else  if(tipo.equalsIgnoreCase("facebook.com")){
            binding.tvTipoSesion.setText("Facebook");
        }else {
            binding.tvTipoSesion.setText("Mail y contraseña");

        }

            Glide.with(binding.imagenUsuario.getContext())
                    .load(user.getFoto_perfil())
                    .placeholder(R.drawable.blank_profile_picture_973460_1280)
                    .error(R.drawable.blank_profile_picture_973460_1280)
                    .into(binding.imagenUsuario);


        binding.btnEditarPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(PerfilUsuarioFragment.this).navigate(R.id.action_perfilUsuarioFragment_to_cambiarPerfilFragment);
            }
        });

        return binding.getRoot();
    }
}