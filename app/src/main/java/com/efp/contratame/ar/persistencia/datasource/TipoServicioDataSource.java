package com.efp.contratame.ar.persistencia.datasource;

import com.efp.contratame.ar.modelo.TipoServicio;
import java.util.List;

public interface TipoServicioDataSource {

    interface GetAllTipoServiciosCallback {
        void onError();
        void onResult(List<TipoServicio> tipos);
    }

    void getAllTipoServicios(GetAllTipoServiciosCallback callback);
}
