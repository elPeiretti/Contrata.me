package com.efp.contratame.ar.persistencia.datasource;

import com.efp.contratame.ar.modelo.TipoServicio;
import java.util.List;

public interface TipoServicioDataSource {

    interface GetAllTipoServicioCallback {
        void onError();
        void onResult(List<TipoServicio> tipos);
    }

}
