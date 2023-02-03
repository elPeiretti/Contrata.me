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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.efp.contratame.ar.Actividades.main.MainActivity;
import com.efp.contratame.ar.databinding.ActivityIniciarSesionBinding;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.Arrays;

public class IniciarSesion extends AppCompatActivity {

    private ActivityIniciarSesionBinding binding;
    private Button btnLogin;
    private Context ctx;
    private TextView registrate;
    private String email="";
    private String password="";
    private FirebaseAuth firebaseAuth;
    private CallbackManager callbackManager;
    private Button btnFacebook;
    private AccessTokenTracker accessTokenTracker;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;
    private GoogleSignInClient googleSignInClient;

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

        //Inicio de sesión con facebook


        btnFacebook= binding.btnFacebook;
        callbackManager = CallbackManager.Factory.create();
        if(LoginManager.getInstance()!=null){
            LoginManager.getInstance().logOut();
        }

        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        handleFacebookAccessToken(loginResult.getAccessToken());
                        Log.d("TAG","procede el login");

                    }
                    @Override
                    public void onCancel() {
                        Log.d("TAG","cancel");

                    }
                    @Override
                    public void onError(FacebookException exception) {
                        Log.d("TAG", exception.getMessage());
                    }
                });
        btnFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginManager.getInstance().logInWithReadPermissions(IniciarSesion.this, Arrays.asList("public_profile", "email","user_birthday"));
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


        //GOOGLE SIGN IN
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("174434494854-j3apseeq97etj17a18lnhm6ftggclf2i.apps.googleusercontent.com")
                .requestEmail()
                .build();

     googleSignInClient = GoogleSignIn.getClient(ctx, googleSignInOptions);


        binding.btnGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = googleSignInClient.getSignInIntent();
                startActivityForResult(intent, 100);
            }
        });
        firebaseAuth = FirebaseAuth.getInstance();

        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // startActivity(new Intent(IniciarSesion.this,MainActivity.class)
                      //     .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                }
            }
        };




        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                if(currentAccessToken==null){
                    firebaseAuth.signOut();
                    LoginManager.getInstance().logOut();
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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        callbackManager.onActivityResult(requestCode, resultCode,data);

        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==100){
            Log.d("GOOGLE", "Got ID token.");
            Task<GoogleSignInAccount> accountTask = GoogleSignIn.getSignedInAccountFromIntent(data);
            if(accountTask.isSuccessful()){
                Log.d("GOOGLE", "Task is successful.");
                try {
                    GoogleSignInAccount googleSignInAccount=accountTask.getResult(ApiException.class);
                    if(googleSignInAccount!=null) {
                       AuthCredential authCredential= GoogleAuthProvider
                                .getCredential(googleSignInAccount.getIdToken()
                                        ,null);

                        firebaseAuth.signInWithCredential(authCredential)
                                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        // Check condition
                                        if(task.isSuccessful()){
                                            Intent intent = new Intent( ctx,MainActivity.class);
                                            startActivity(intent
                                                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                                            googleSignInClient.signOut();
                                       }
                                        else
                                        {
                                           Log.d("GOOGLE","Authentication Failed :"+task.getException().getMessage());
                                        }
                                    }
                                });
                    }
                }
                catch (ApiException e)
                {
                    e.printStackTrace();
                }
            }
        }
        }




    public void validarCampos() {
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

    private void handleFacebookAccessToken(AccessToken token) {
        Log.d("TAG", "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d("TAG", "signInWithCredential:success");
                            Intent intent = new Intent( ctx,MainActivity.class);
                            startActivity(intent);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("TAG", "signInWithCredential:failure", task.getException());
                            Toast.makeText(IniciarSesion.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
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