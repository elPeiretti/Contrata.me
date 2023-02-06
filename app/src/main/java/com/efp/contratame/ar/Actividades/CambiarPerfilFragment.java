package com.efp.contratame.ar.Actividades;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.efp.contratame.ar.Actividades.main.MainActivity;
import com.efp.contratame.ar.R;
import com.efp.contratame.ar.databinding.FragmentCambiarPerfilBinding;
import com.facebook.AccessToken;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CambiarPerfilFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CambiarPerfilFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private FragmentCambiarPerfilBinding binding;
    private FirebaseUser user;
    private Uri imagenUri;

    public CambiarPerfilFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CambiarPerfilFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CambiarPerfilFragment newInstance(String param1, String param2) {
        CambiarPerfilFragment fragment = new CambiarPerfilFragment();
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
        binding = FragmentCambiarPerfilBinding.inflate(inflater, container,false);
        user = FirebaseAuth.getInstance().getCurrentUser();
        imagenUri = user.getPhotoUrl();
        binding.editTextName.setText(user.getDisplayName());

        Glide.with(binding.imagenUsuario.getContext())
                .load(imagenUri)
                .placeholder(R.drawable.blank_profile_picture_973460_1280)
                .error(R.drawable.blank_profile_picture_973460_1280)
                .into(binding.imagenUsuario);




        binding.btnConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                        .setDisplayName(binding.editTextName.getText().toString())
                        .setPhotoUri(imagenUri)
                        .build();
                Log.d("IMAGEN", imagenUri.toString());

                user.updateProfile(profileUpdates)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Log.d("USUARIO", "User profile updated.");
                                    ((MainActivity)getActivity()).setearValoresDrawer();
                                }
                            }
                        });
            }
        });

        binding.imagenUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent openGalleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(openGalleryIntent, 1000);
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1000){
            Log.d("IMAGEN", "request code = 1000");
            if(resultCode == Activity.RESULT_OK){
                Log.d("IMAGEN", "result code OK");
                imagenUri = data.getData();
                Glide.with(binding.imagenUsuario.getContext()).load(imagenUri).into(binding.imagenUsuario);

            }
        }
    }


}