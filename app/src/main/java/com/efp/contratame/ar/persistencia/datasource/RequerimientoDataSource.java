package com.efp.contratame.ar.persistencia.datasource;

import com.efp.contratame.ar.modelo.Requerimiento;

public interface RequerimientoDataSource {

    interface SaveRequerimientoCallback{
        void onError();
        void onResult();
    }

    void saveRequerimiento(Requerimiento req);
}
