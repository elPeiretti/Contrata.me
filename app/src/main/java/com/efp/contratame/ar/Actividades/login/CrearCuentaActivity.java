package com.efp.contratame.ar.Actividades.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.efp.contratame.ar.databinding.ActivityCrearCuentaBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Pattern;

public class CrearCuentaActivity extends AppCompatActivity {

    private ActivityCrearCuentaBinding binding;
    private TextView iniciarSesion;
    private Context ctx;
    private String email="";
    private String password="";
    private FirebaseAuth firebaseAuth;
    public static final Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+"
    );



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
                Intent intent = new Intent(ctx, IniciarSesionActivity.class);
                startActivity(intent);
            }
        });


        binding.btnContinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email= binding.txtEmaillCrear.getText().toString().trim();
                password= binding.txtContrasenaCrear.getText().toString().trim();
                switch (CrearCuentaActivity.validarCampos(email, password)){
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


        public  static int validarCampos(String email, String password) {
            int rdo = 0;
            if(email == null || email.isEmpty() || !EMAIL_ADDRESS_PATTERN.matcher(email).matches()){
                rdo=1;
            }else if(password!=null && password.length()==0){
                rdo=2;
            }else if(password.length()<6){
                rdo=3;
            }
            return rdo;

        }


    private void firebaseCrearCuenta() {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Toast.makeText(CrearCuentaActivity.this, "Se ha creado su cuenta con éxito", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent( ctx, IniciarSesionActivity.class);
                        intent.putExtra("idUsuario",authResult.getUser().getUid());
                        intent.putExtra("nombre", authResult.getUser().getDisplayName() == null ? "" : authResult.getUser().getDisplayName());
                        intent.putExtra("mail", authResult.getUser().getEmail());
                        intent.putExtra("foto", authResult.getUser().getPhotoUrl() == null ? "" : authResult.getUser().getPhotoUrl().toString());
                        intent.putExtra("sesion", "firebase");
                        startActivity(intent);
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(CrearCuentaActivity.this, "Se ha producido un error, vuelva a intentarlo más tarde", Toast.LENGTH_SHORT).show();
                        Log.d("FIREBASE", e.getMessage());
                    }
                });
    }
}