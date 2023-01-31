package com.efp.contratame.ar.persistencia.retrofit;

import android.util.Log;

import androidx.annotation.NonNull;

import com.efp.contratame.ar.modelo.Prestador;
import com.efp.contratame.ar.modelo.TipoServicio;
import com.efp.contratame.ar.persistencia.datasource.PrestadorDataSource;
import com.efp.contratame.ar.persistencia.datasource.ServicioDataSource;
import com.efp.contratame.ar.persistencia.repository.PrestadorRepository;
import com.efp.contratame.ar.persistencia.repository.ServicioRepository;
import com.efp.contratame.ar.persistencia.repository.TipoServicioRepository;
import com.efp.contratame.ar.persistencia.retrofit.entity.ServicioRF;
import com.efp.contratame.ar.persistencia.retrofit.interfaces.ServicioService;
import com.efp.contratame.ar.persistencia.retrofit.mapper.ServicioMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
                //solucion al problema de las comillas
                Map<String, String> opt = new LinkedHashMap<>();
                opt.put("orderBy","\"keyTipoServicio\"");
                opt.put("equalTo","\""+
                        (tipoServicio.equals(TipoServicioRepository.OTRO) ? "" : tipoServicio.getIdTipoServicio().toString()) +
                        "\"");

                Call<Map<String,ServicioRF>> reqAsyn = servicioService.getAllServiciosDelTipo(opt);
                Log.i("RETRO",reqAsyn.request().url().toString());
                reqAsyn.enqueue(new Callback<>() {
                    @Override
                    public void onResponse(@NonNull Call<Map<String,ServicioRF>> call, @NonNull Response<Map<String,ServicioRF>> response) {

                        if (response.code() == 200) {
                            Map<String,ServicioRF> data = response.body();

                            if (data == null) {
                                callback.onError();
                                return;
                            }
                            // en "prestadores" tengo todos los prestadores, en "data" los ServicioRF y "tipoServicio" es el tipoServicio
                            callback.onResult(ServicioMapper.fromEntities(new ArrayList<>(data.values()), tipoServicio, prestadores));

                        } else {
                            callback.onError();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<Map<String,ServicioRF>> call, @NonNull Throwable t) {
                        Log.e("ERROR_RETROFIT",t.getMessage());
                        callback.onError();
                    }
                });
            }
        });


    }
}
