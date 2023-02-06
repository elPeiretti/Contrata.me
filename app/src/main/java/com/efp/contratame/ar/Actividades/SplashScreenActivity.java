package com.efp.contratame.ar.Actividades;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.efp.contratame.ar.Actividades.main.MainActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashScreenActivity extends AppCompatActivity {
    private Context ctx;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SystemClock.sleep(2000);
        ctx=this;
        firebaseAuth= FirebaseAuth.getInstance();
      //  startActivity(new Intent(SplashScreenActivity.this, IniciarSesion.class));
        setAuthStateListener();

    }

    public void setAuthStateListener(){
        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                if (user != null) {
                    Log.d("user", "user not null");
                    Log.d("user", user.getEmail());

                    // startActivity(new Intent(IniciarSesion.this,MainActivity.class)
                    //     .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                    Intent intent = new Intent( ctx,MainActivity.class);
                    intent.putExtra("idUsuario",firebaseAuth.getCurrentUser().getUid());
                    intent.putExtra("nombre", firebaseAuth.getCurrentUser().getDisplayName() == null ? "" : firebaseAuth.getCurrentUser().getDisplayName());
                    intent.putExtra("mail", firebaseAuth.getCurrentUser().getEmail());
                    intent.putExtra("foto", firebaseAuth.getCurrentUser().getPhotoUrl() == null ? "" : firebaseAuth.getCurrentUser().getPhotoUrl().toString());
                    String tipo = user.getProviderData().get(1).getProviderId();
                    if(tipo.equalsIgnoreCase("google.com")){
                        intent.putExtra("sesion", "google.com");
                    }else  if(tipo.equalsIgnoreCase("facebook.com")){
                        intent.putExtra("sesion", "facebook.com");
                    }else {
                        intent.putExtra("sesion", "firebase");

                    }

                    startActivity(intent);
                    finish();
                } else {
                    startActivity(new Intent(SplashScreenActivity.this, IniciarSesion.class));
                    finish();
                }
            }
        };
    }
    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(firebaseAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        firebaseAuth.removeAuthStateListener(firebaseAuthListener);
    }
}
