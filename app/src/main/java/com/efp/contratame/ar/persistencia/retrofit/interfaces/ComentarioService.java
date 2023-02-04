package com.efp.contratame.ar.persistencia.retrofit.interfaces;

import com.efp.contratame.ar.persistencia.retrofit.entity.ComentarioRF;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ComentarioService {

    @GET("comentario/{idServ}.json")
    Call<List<ComentarioRF>> getAllComentariosDeServicio(@Path("idServ") String idServicio);
}
