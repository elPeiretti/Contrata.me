package com.efp.contratame.ar.persistencia.retrofit;

import android.util.Log;

import androidx.annotation.NonNull;

import com.efp.contratame.ar.modelo.Requerimiento;
import com.efp.contratame.ar.modelo.TipoServicio;
import com.efp.contratame.ar.persistencia.datasource.RequerimientoDataSource;
import com.efp.contratame.ar.persistencia.datasource.TipoServicioDataSource;
import com.efp.contratame.ar.persistencia.repository.TipoServicioRepository;
import com.efp.contratame.ar.persistencia.retrofit.entity.RequerimientoRF;
import com.efp.contratame.ar.persistencia.retrofit.entity.ServicioRF;
import com.efp.contratame.ar.persistencia.retrofit.interfaces.RequerimientoService;
import com.efp.contratame.ar.persistencia.retrofit.mapper.RequerimientoMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
    public void saveRequerimiento(Requerimiento req, String idUsuario, SaveRequerimientoCallback callback) {
        Call<RequerimientoRF> reqAsyn = requerimientoService
                .saveRequerimiento(idUsuario, RequerimientoMapper.toEntity(req));
        reqAsyn.enqueue(new Callback<RequerimientoRF>() {
            @Override
            public void onResponse(@NonNull Call<RequerimientoRF> call, @NonNull Response<RequerimientoRF> response) {
                if (response.code() == 200){
                    callback.onResult();
                }
                else{
                    callback.onError();
                }
            }

            @Override
            public void onFailure(@NonNull Call<RequerimientoRF> call, @NonNull Throwable t) {
                callback.onError();
            }
        });
    }

    @Override
    public void getAllRequerimientosFrom(String idUsuario, GetAllRequerimientosFromCallback callback) {
        TipoServicioRepository.createInstance().getAllTipoServicios(new TipoServicioDataSource.GetAllTipoServiciosCallback() {
            @Override
            public void onError() {
                callback.onError();
            }

            @Override
            public void onResult(List<TipoServicio> tipos) {
                Call<Map<String,RequerimientoRF>> reqAsyn = requerimientoService.getAllRequerimientosFrom(idUsuario);

                Log.i("RETRO",reqAsyn.request().url().toString());
                reqAsyn.enqueue(new Callback<Map<String, RequerimientoRF>>() {
                    @Override
                    public void onResponse(@NonNull Call<Map<String, RequerimientoRF>> call, @NonNull Response<Map<String, RequerimientoRF>> response) {

                        if (response.code() == 200 && response.body() != null) {
                            Map<String, RequerimientoRF> data = response.body();
                            callback.onResult(RequerimientoMapper.fromEntities(new ArrayList<>(data.values()), tipos));
                        }
                        else{
                            callback.onError();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<Map<String, RequerimientoRF>> call, @NonNull Throwable t) {

                    }
                });
            }
        });
    }
}
