package com.efp.contratame.ar.persistencia.retrofit.interfaces;

import com.efp.contratame.ar.persistencia.retrofit.entity.PrestadorRF;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface PrestadorService {

    @GET("prestador.json")
    Call<List<PrestadorRF>> getAllPrestadores();

    @GET("prestador/{id}.json")
    Call<PrestadorRF> getPrestador(@Path("id") String idPrestador);
}
