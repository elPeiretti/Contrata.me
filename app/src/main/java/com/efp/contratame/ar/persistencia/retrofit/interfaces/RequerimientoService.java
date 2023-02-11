package com.efp.contratame.ar.persistencia.retrofit.interfaces;

import com.efp.contratame.ar.persistencia.retrofit.entity.RequerimientoRF;

import java.util.Map;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RequerimientoService {

    @POST("requerimiento/{id}.json")
    Call<RequerimientoRF> saveRequerimiento(@Path("id") String idUsuario, @Body RequerimientoRF reqRF);

    @GET("requerimiento/{id}.json")
    Call<Map<String,RequerimientoRF>> getAllRequerimientosFrom(@Path("id") String idUsuario);
}
