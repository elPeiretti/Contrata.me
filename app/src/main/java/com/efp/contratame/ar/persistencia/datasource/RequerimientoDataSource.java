package com.efp.contratame.ar.persistencia.datasource;

import com.efp.contratame.ar.modelo.Requerimiento;

import java.util.List;
import java.util.UUID;

public interface RequerimientoDataSource {

    interface SaveRequerimientoCallback {
        void onError();
        void onResult();
    }
    interface GetAllRequerimientosByFromCallback {
        void onError();
        void onResult(List<Requerimiento> requerimientoList);
    }

    void saveRequerimiento(Requerimiento req);
    void getAllRequerimientosFrom(UUID idUsuario);
}
