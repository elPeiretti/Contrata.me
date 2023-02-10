package com.efp.contratame.ar.persistencia.retrofit.interfaces;

import com.efp.contratame.ar.persistencia.retrofit.entity.RequerimientoRF;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface RequerimientoService {

    @PUT("requerimiento/{id}.json")
    Call<RequerimientoRF> saveRequerimiento(@Path("id") String idUsuario, @Body RequerimientoRF reqRF);

    @GET("requerimiento/{id}.json")
    Call<List<RequerimientoRF>> getAllRequerimientosFrom(@Path("id") String idUsuario);
}
