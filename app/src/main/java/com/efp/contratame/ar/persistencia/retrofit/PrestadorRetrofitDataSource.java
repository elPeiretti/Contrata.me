package com.efp.contratame.ar.persistencia.retrofit;

import android.util.Log;

import androidx.annotation.NonNull;

import com.efp.contratame.ar.persistencia.datasource.PrestadorDataSource;
import com.efp.contratame.ar.persistencia.retrofit.entity.PrestadorRF;
import com.efp.contratame.ar.persistencia.retrofit.interfaces.PrestadorService;
import com.efp.contratame.ar.persistencia.retrofit.mapper.PrestadorMapper;
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

public class PrestadorRetrofitDataSource implements PrestadorDataSource {

    private final PrestadorService prestadorService;

    public PrestadorRetrofitDataSource(){
        Gson gson = new GsonBuilder().setLenient().create();
        OkHttpClient client = new OkHttpClient.Builder().build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://contratame-8fd7a-default-rtdb.firebaseio.com/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        prestadorService = retrofit.create(PrestadorService.class);
    }


    @Override
    public void getAllPrestadoresSinServicios(GetAllPrestadoresSinServiciosCallback callback) {
        Call<List<PrestadorRF>> reqAsyn = prestadorService.getAllPrestadores();

        reqAsyn.enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<List<PrestadorRF>> call, @NonNull Response<List<PrestadorRF>> response) {
                if(response.code() == 200){
                    List<PrestadorRF> data = response.body();
                    if(data == null) {callback.onError(); return;}

                    //TODO onResult de getAllPrestadores (y el mapper tamb)
                    callback.onResult(PrestadorMapper.sinServiciosFromEntities(data));
                }
                else{
                    callback.onError();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<PrestadorRF>> call, @NonNull Throwable t) {
                Log.e("ERROR_RETROFIT",t.getMessage());
                callback.onError();
            }
        });
    }

    @Override
    public void getPrestador(UUID idPrestador, GetPrestadorCallback callback) {
        Call<PrestadorRF> reqAsyn = prestadorService.getPrestador(idPrestador.toString());

        reqAsyn.enqueue(new Callback<>() {

            @Override
            public void onResponse(@NonNull Call<PrestadorRF> call, @NonNull Response<PrestadorRF> response) {
                if(response.code() == 200){
                    PrestadorRF data = response.body();
                    if(data == null) {callback.onError(); return;}

                    //TODO onResult de getPrestador (y el mapper tamb)
                    //callback.onResult(PrestadorMapper.fromEntity(data));
                }
                else{
                    callback.onError();
                }
            }

            @Override
            public void onFailure(@NonNull Call<PrestadorRF> call, @NonNull Throwable t) {
                Log.e("ERROR_RETROFIT",t.getMessage());
                callback.onError();
            }
        });
    }
}
