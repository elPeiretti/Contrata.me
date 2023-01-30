package com.efp.contratame.ar.persistencia.retrofit;

import androidx.annotation.NonNull;

import com.efp.contratame.ar.modelo.Prestador;
import com.efp.contratame.ar.modelo.TipoServicio;
import com.efp.contratame.ar.persistencia.datasource.PrestadorDataSource;
import com.efp.contratame.ar.persistencia.datasource.ServicioDataSource;
import com.efp.contratame.ar.persistencia.repository.PrestadorRepository;
import com.efp.contratame.ar.persistencia.retrofit.entity.ServicioRF;
import com.efp.contratame.ar.persistencia.retrofit.interfaces.ServicioService;
import com.efp.contratame.ar.persistencia.retrofit.mapper.ServicioMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServicioRetrofitDataSource implements ServicioDataSource {

    private final ServicioService servicioService;

    public ServicioRetrofitDataSource() {
        Gson gson = new GsonBuilder().setLenient().create();
        OkHttpClient client = new OkHttpClient.Builder().build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://contratame-8fd7a-default-rtdb.firebaseio.com/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        servicioService = retrofit.create(ServicioService.class);
    }

    @Override
    public void getAllServiciosDelTipo(TipoServicio tipoServicio, GetAllServiciosDelTipoCallback callback) {

        // Traigo todos los prestadores porque no hice un metodo especifico para traer solo los necesarios
        // Muy ineficiente, pero podria solucionarse de ser necesario
        PrestadorRepository.createInstance().getAllPrestadoresSinServicios(new PrestadorDataSource.GetAllPrestadoresSinServiciosCallback() {
            @Override
            public void onError() {
                callback.onError();
            }

            @Override
            public void onResult(List<Prestador> prestadores) {
                Call<List<ServicioRF>> reqAsyn = servicioService.getAllServiciosDelTipo(tipoServicio.getIdTipoServicio().toString());
                reqAsyn.enqueue(new Callback<>() {
                    @Override
                    public void onResponse(@NonNull Call<List<ServicioRF>> call, @NonNull Response<List<ServicioRF>> response) {
                        if (response.code() == 200) {
                            List<ServicioRF> data = response.body();
                            if (data == null) {
                                callback.onError();
                                return;
                            }
                            // en "prestadores" tengo todos los prestadores, en "data" los ServicioRF y "tipoServicio" es el tipoServicio
                            callback.onResult(ServicioMapper.fromEntities(data, tipoServicio, prestadores));

                        } else {
                            callback.onError();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<List<ServicioRF>> call, @NonNull Throwable t) {
                        callback.onError();
                    }
                });
            }
        });


    }
}
