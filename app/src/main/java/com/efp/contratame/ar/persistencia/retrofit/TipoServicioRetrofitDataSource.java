package com.efp.contratame.ar.persistencia.retrofit;

import androidx.annotation.NonNull;

import com.efp.contratame.ar.persistencia.datasource.TipoServicioDataSource;
import com.efp.contratame.ar.persistencia.retrofit.entity.TipoServicioRF;
import com.efp.contratame.ar.persistencia.retrofit.interfaces.TipoServicioService;
import com.efp.contratame.ar.persistencia.retrofit.mapper.TipoServicioMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.List;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TipoServicioRetrofitDataSource implements TipoServicioDataSource {

    private final TipoServicioService tsService;

    public TipoServicioRetrofitDataSource(){
        Gson gson = new GsonBuilder().setLenient().create();
        OkHttpClient client = new OkHttpClient.Builder().build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://contratame-8fd7a-default-rtdb.firebaseio.com/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        tsService = retrofit.create(TipoServicioService.class);
    }

    @Override
    public void getAllTipoServicios(GetAllTipoServiciosCallback callback) {
        Call<List<TipoServicioRF>> reqAsyn = tsService.getAllTipoServicios();

        reqAsyn.enqueue(new Callback<List<TipoServicioRF>>() {
            @Override
            public void onResponse(@NonNull Call<List<TipoServicioRF>> call, @NonNull Response<List<TipoServicioRF>> response) {
                if(response.code() == 200){
                    List<TipoServicioRF> data = response.body();
                    if(data == null) {callback.onError(); return;}

                    callback.onResult(TipoServicioMapper.fromEntities(data));
                }
                else{
                    callback.onError();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<TipoServicioRF>> call, @NonNull Throwable t) {
                callback.onError();
            }
        });
    }
}
