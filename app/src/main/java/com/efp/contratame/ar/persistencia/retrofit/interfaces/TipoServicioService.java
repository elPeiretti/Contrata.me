package com.efp.contratame.ar.persistencia.retrofit.interfaces;


import com.efp.contratame.ar.persistencia.retrofit.entity.TipoServicioRF;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface TipoServicioService {

    @GET("tiposervicio.json")
    Call<List<TipoServicioRF>> getAllTipoServicios();

}
