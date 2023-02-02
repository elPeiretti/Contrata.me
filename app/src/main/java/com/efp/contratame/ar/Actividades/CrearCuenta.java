package com.efp.contratame.ar.Actividades;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.efp.contratame.ar.auxiliares.ValidadorDeCampos;
import com.efp.contratame.ar.databinding.ActivityCrearCuentaBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class CrearCuenta extends AppCompatActivity {

    private ActivityCrearCuentaBinding binding;
    private TextView iniciarSesion;
    private Context ctx;
    private String email="";
    private String password="";
    private FirebaseAuth firebaseAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        binding = ActivityCrearCuentaBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        ctx=this;

        firebaseAuth = FirebaseAuth.getInstance();

        iniciarSesion = binding.tvIniciaSesion;
        iniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ctx, IniciarSesion.class);
                startActivity(intent);
            }
        });


        binding.btnContinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (ValidadorDeCampos.validarCampos(binding.txtEmaillCrear.getText().toString().trim(),binding.txtContrasenaCrear.getText().toString().trim())){
                    case 0:
                        firebaseCrearCuenta();
                        break;
                    case 1:
                        binding.txtEmaillCrear.setError("Formato de email inválido");
                        break;
                    case 2:
                        binding.txtContrasenaCrear.setError("Ingrese una contraseña");
                        break;
                    case 3:
                        binding.txtContrasenaCrear.setError("La contraseña debe tener al menos 6 caracteres");
                        break;
                }
            }
        });
    }


    private void firebaseCrearCuenta() {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Toast.makeText(CrearCuenta.this, "Se ha creado su cuenta con éxito, por favor incie sesión", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent( ctx,IniciarSesion.class);
                        startActivity(intent);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(CrearCuenta.this, "Se ha producido un error, vuelva a intentarlo más tarde", Toast.LENGTH_SHORT).show();
                        Log.d("FIREBASE", e.getMessage());
                    }
                });
    }
}