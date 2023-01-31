package com.efp.contratame.ar.persistencia.retrofit.interfaces;

import com.efp.contratame.ar.persistencia.retrofit.entity.ServicioRF;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ServicioService {

    @GET("servicio.json")
    Call<List<ServicioRF>> getAllServiciosDelTipo(@Query("keyTipoServicio") String keyTipoServicio);

}
