package com.efp.contratame.ar.persistencia.retrofit;

import com.efp.contratame.ar.persistencia.datasource.PrestadorDataSource;
import com.efp.contratame.ar.persistencia.retrofit.interfaces.PrestadorService;
import com.efp.contratame.ar.persistencia.retrofit.interfaces.TipoServicioService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.UUID;

import okhttp3.OkHttpClient;
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
    public void getAllPrestadores(GetAllPrestadoresCallback callback) {

    }

    @Override
    public void getPrestador(UUID idPrestador, GetPrestadorCallback callback) {

    }
}
