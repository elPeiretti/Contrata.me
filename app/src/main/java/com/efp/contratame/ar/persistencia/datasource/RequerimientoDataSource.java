package com.efp.contratame.ar.persistencia.datasource;

import com.efp.contratame.ar.modelo.Requerimiento;

import java.util.List;
import java.util.UUID;

public interface RequerimientoDataSource {

    interface SaveRequerimientoCallback {
        void onError();
        void onResult();
    }
    interface GetAllRequerimientosFromCallback {
        void onError();
        void onResult(List<Requerimiento> requerimientoList);
    }

    void saveRequerimiento(Requerimiento req, String idUsuario, SaveRequerimientoCallback callback);
    void getAllRequerimientosFrom(String idUsuario, GetAllRequerimientosFromCallback callback);
}
