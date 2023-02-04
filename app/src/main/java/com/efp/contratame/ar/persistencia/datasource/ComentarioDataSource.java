package com.efp.contratame.ar.persistencia.datasource;

import com.efp.contratame.ar.modelo.Comentario;

import java.util.List;
import java.util.UUID;

public interface ComentarioDataSource {

    interface GetAllComentariosDeServicioCallback{
        void onResult(List<Comentario> comentarioList);
        void onError();
    }

    void getAllComentariosDeServicio(UUID servicio, GetAllComentariosDeServicioCallback callback);
}
