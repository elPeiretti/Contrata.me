package com.efp.contratame.ar.persistencia.retrofit;

import androidx.annotation.NonNull;

import com.efp.contratame.ar.modelo.Requerimiento;
import com.efp.contratame.ar.persistencia.datasource.RequerimientoDataSource;
import com.efp.contratame.ar.persistencia.retrofit.entity.RequerimientoRF;
import com.efp.contratame.ar.persistencia.retrofit.interfaces.RequerimientoService;
import com.efp.contratame.ar.persistencia.retrofit.mapper.RequerimientoMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.UUID;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RequerimientoRetrofitDataSource implements RequerimientoDataSource {

    private final RequerimientoService requerimientoService;

    public RequerimientoRetrofitDataSource(){
        // TODO agregar auth
        Gson gson = new GsonBuilder().setLenient().create();
        OkHttpClient client = new OkHttpClient.Builder().build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://contratame-8fd7a-default-rtdb.firebaseio.com/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        requerimientoService = retrofit.create(RequerimientoService.class);
    }

    @Override
    public void saveRequerimiento(Requerimiento req, UUID idUsuario, SaveRequerimientoCallback callback) {
        Call<RequerimientoRF> reqAsyn = requerimientoService
                .saveRequerimiento(idUsuario.toString(), RequerimientoMapper.toEntity(req));
        reqAsyn.enqueue(new Callback<RequerimientoRF>() {
            @Override
            public void onResponse(Call<RequerimientoRF> call, Response<RequerimientoRF> response) {

            }

            @Override
            public void onFailure(Call<RequerimientoRF> call, Throwable t) {

            }
        });
    }

    @Override
    public void getAllRequerimientosFrom(UUID idUsuario, GetAllRequerimientosFromCallback callback) {
        //TODO
    }
}
