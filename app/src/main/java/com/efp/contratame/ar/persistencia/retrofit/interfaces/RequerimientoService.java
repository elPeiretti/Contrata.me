package com.efp.contratame.ar.persistencia.retrofit.interfaces;

import com.efp.contratame.ar.persistencia.retrofit.entity.RequerimientoRF;

import java.util.Map;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

public interface RequerimientoService {

    @PUT("requerimiento/{idUser}/{idReq}.json")
    Call<RequerimientoRF> saveRequerimiento(@Path("idUser") String idUsuario,
                                            @Path("idReq") String idReq,
                                            @Body RequerimientoRF reqRF);

    @GET("requerimiento/{id}.json")
    Call<Map<String,RequerimientoRF>> getAllRequerimientosFrom(@Path("id") String idUsuario);

    @DELETE("requerimiento/{idUser}/{idReq}.json")
    Call<RequerimientoRF> deleteRequerimiento(@Path("idUser") String idUsuario,
                                              @Path("idReq") String idReq);
}
