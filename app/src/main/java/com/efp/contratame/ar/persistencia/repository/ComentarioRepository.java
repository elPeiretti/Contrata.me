package com.efp.contratame.ar.persistencia.repository;

import com.efp.contratame.ar.persistencia.datasource.ComentarioDataSource;
import com.efp.contratame.ar.persistencia.datasource.TipoServicioDataSource;
import com.efp.contratame.ar.persistencia.retrofit.ComentarioRetrofitDataSource;
import com.efp.contratame.ar.persistencia.retrofit.TipoServicioRetrofitDataSource;

import java.util.UUID;

public class ComentarioRepository implements ComentarioDataSource {

    private ComentarioDataSource comentarioDataSource;

    private ComentarioRepository(){}
    private ComentarioRepository(ComentarioDataSource comentarioDataSource){
        this.comentarioDataSource = comentarioDataSource;
    }

    public static ComentarioDataSource createInstance(){
        return new ComentarioRepository(new ComentarioRetrofitDataSource());
    }

    @Override
    public void getAllComentariosDeServicio(UUID idServicio, GetAllComentariosDeServicioCallback callback) {
        comentarioDataSource.getAllComentariosDeServicio(idServicio, callback);
    }
}
