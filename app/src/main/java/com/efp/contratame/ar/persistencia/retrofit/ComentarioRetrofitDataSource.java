package com.efp.contratame.ar.persistencia.retrofit;

import android.util.Log;

import androidx.annotation.NonNull;

import com.efp.contratame.ar.persistencia.datasource.ComentarioDataSource;
import com.efp.contratame.ar.persistencia.retrofit.entity.ComentarioRF;
import com.efp.contratame.ar.persistencia.retrofit.entity.TipoServicioRF;
import com.efp.contratame.ar.persistencia.retrofit.interfaces.ComentarioService;
import com.efp.contratame.ar.persistencia.retrofit.interfaces.TipoServicioService;
import com.efp.contratame.ar.persistencia.retrofit.mapper.ComentarioMapper;
import com.efp.contratame.ar.persistencia.retrofit.mapper.TipoServicioMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;
import java.util.UUID;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ComentarioRetrofitDataSource implements ComentarioDataSource {

    private final ComentarioService comentarioService;

    public ComentarioRetrofitDataSource(){
        Gson gson = new GsonBuilder().setLenient().create();
        OkHttpClient client = new OkHttpClient.Builder().build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://contratame-8fd7a-default-rtdb.firebaseio.com/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        comentarioService = retrofit.create(ComentarioService.class);
    }

    @Override
    public void getAllComentariosDeServicio(UUID servicio, GetAllComentariosDeServicioCallback callback) {
        Call<List<ComentarioRF>> reqAsyn = comentarioService.getAllComentariosDeServicio(servicio.toString());
        Log.i("RETRO",reqAsyn.request().url().toString());
        reqAsyn.enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<List<ComentarioRF>> call, @NonNull Response<List<ComentarioRF>> response) {
                if(response.code() == 200){
                    List<ComentarioRF> data = response.body();
                    if(data == null) {callback.onError(); return;}

                    callback.onResult(ComentarioMapper.fromEntities(data));
                }
                else{
                    callback.onError();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<ComentarioRF>> call, @NonNull Throwable t) {
                callback.onError();
            }
        });
    }
}
