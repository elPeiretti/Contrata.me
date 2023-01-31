package com.efp.contratame.ar.persistencia.retrofit.interfaces;

import com.efp.contratame.ar.persistencia.retrofit.entity.ServicioRF;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface ServicioService {


    @GET("servicio.json?")
    Call<List<ServicioRF>> getAllServiciosDelTipo(@QueryMap Map<String,String> options);

}
