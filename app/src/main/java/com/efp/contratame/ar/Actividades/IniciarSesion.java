package com.efp.contratame.ar.Actividades;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.efp.contratame.ar.Actividades.main.MainActivity;
import com.efp.contratame.ar.databinding.ActivityIniciarSesionBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class IniciarSesion extends AppCompatActivity {

    private ActivityIniciarSesionBinding binding;
    private Button btnLogin;
    private Context ctx;
    private TextView registrate;
    private String email="";
    private String password="";
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityIniciarSesionBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        ctx=this;

        firebaseAuth= FirebaseAuth.getInstance();

        btnLogin = binding.btnIniciarSesion;
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validarCampos();
            }
        });

        registrate = binding.tvRegistrate;
        registrate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( ctx,CrearCuenta.class);
                startActivity(intent);
            }
        });

        binding.btnGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });



    }

    private void validarCampos() {
        email= binding.txtEmail.getText().toString().trim();
        password = binding.txtContrasena.getText().toString().trim();

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            binding.txtEmail.setError("Formato de email inválido");
        }else if(TextUtils.isEmpty(password)){
            binding.txtContrasena.setError("Ingrese una contraseña");
        }else if(password.length()<6){
            binding.txtContrasena.setError("La contraseña debe tener al menos 6 caracteres");
        }else{
            firebaseIniciarSesion();
        }

    }


    private void firebaseIniciarSesion() {
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Toast.makeText(IniciarSesion.this, "Se ha iniciado sesión con éxito", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent( ctx, MainActivity.class);
                        startActivity(intent);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(IniciarSesion.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
    }
}