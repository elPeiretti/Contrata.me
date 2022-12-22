package com.example.contratame.Actividades;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.contratame.databinding.ActivityCrearCuentaBinding;
import com.example.contratame.databinding.ActivityResutladosServiciosBinding;
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
                validarCampos();
            }
        });
    }

    private void validarCampos() {
        email= binding.txtEmaillCrear.getText().toString().trim();
        password = binding.txtContrasenaCrear.getText().toString().trim();

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            binding.txtEmaillCrear.setError("Formato de email inválido");
        }else if(TextUtils.isEmpty(password)){
            binding.txtContrasenaCrear.setError("Ingrese una contraseña");
        }else if(password.length()<6){
            binding.txtContrasenaCrear.setError("La contraseña debe tener al menos 6 caracteres");
        }else{
            firebaseCrearCuenta();
        }

    }

    private void firebaseCrearCuenta() {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Toast.makeText(CrearCuenta.this, "Su cuenta fue creada con éxito", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent( ctx,MainActivity.class);
                        startActivity(intent);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(CrearCuenta.this, "Se ha producido un error, vuelva a intentarlo más tarde", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}