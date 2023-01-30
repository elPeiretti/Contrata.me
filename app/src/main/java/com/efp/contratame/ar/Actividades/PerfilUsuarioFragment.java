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

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private FragmentPerfilUsuarioBinding binding;
    private Context ctx = this.getContext();
    private FirebaseUser user;

    public PerfilUsuarioFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PerfilUsuarioFragment.
     */
    // TODO: Rename and change types and number of parameters
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentPerfilUsuarioBinding.inflate(inflater, container, false);
        ctx = this.getContext();
        user = FirebaseAuth.getInstance().getCurrentUser();
        binding.btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();

                Intent intent = new Intent( ctx,IniciarSesion.class);
                startActivity(intent);
            }
        });

        binding.tvEmailUsuario.setText(user.getEmail());
        binding.tvNombreSuario.setText(user.getDisplayName());
        String tipo = user.getProviderData().get(1).getProviderId();
        if(tipo.equals("facebook.com")) {
            Glide.with(binding.imagenUsuario.getContext()).load("https://graph.facebook.com/me/picture?access_token="+ AccessToken.getCurrentAccessToken().getToken()).into(binding.imagenUsuario);
            Log.d("foto", "https://graph.facebook.com/me/picture?access_token="+ AccessToken.getCurrentAccessToken().getToken());
        }else if (tipo.equals("google.com")){
            Glide.with(binding.imagenUsuario.getContext()).load(user.getPhotoUrl()).into(binding.imagenUsuario);
        }else{
            binding.imagenUsuario.setImageResource(R.drawable.blank_profile_picture_973460_1280);

/*
            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                    .setDisplayName("Jane Q. User")
                    .setPhotoUri(Uri.parse("https://concepto.de/wp-content/uploads/2018/08/persona-e1533759204552.jpg"))
                    .build();

            user.updateProfile(profileUpdates)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Log.d("USUARIO", "User profile updated.");
                                binding.tvNombreSuario.setText(user.getDisplayName());
                                Glide.with(binding.imagenUsuario.getContext()).load(user.getPhotoUrl()).into(binding.imagenUsuario);
                            }
                        }
                    });

 */
        }

        binding.btnEditarPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(PerfilUsuarioFragment.this).navigate(R.id.action_perfilUsuarioFragment_to_cambiarPerfilFragment);
            }
        });

        return binding.getRoot();
    }
}