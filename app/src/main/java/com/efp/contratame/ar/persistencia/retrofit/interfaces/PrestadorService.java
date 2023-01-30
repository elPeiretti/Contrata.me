package com.efp.contratame.ar.persistencia.retrofit.interfaces;

import com.efp.contratame.ar.persistencia.retrofit.entity.PrestadorRF;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface PrestadorService {

    @GET("prestador.json")
    Call<List<PrestadorRF>> getAllPrestadores();

    @GET("prestador.json")
    Call<PrestadorRF> getPrestador(String idPrestador);
}
