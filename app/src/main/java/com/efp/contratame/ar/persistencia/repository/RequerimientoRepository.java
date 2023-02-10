package com.efp.contratame.ar.persistencia.repository;

import com.efp.contratame.ar.modelo.Requerimiento;
import com.efp.contratame.ar.persistencia.datasource.RequerimientoDataSource;
import com.efp.contratame.ar.persistencia.datasource.TipoServicioDataSource;
import com.efp.contratame.ar.persistencia.retrofit.RequerimientoRetrofitDataSource;
import com.efp.contratame.ar.persistencia.retrofit.TipoServicioRetrofitDataSource;

import java.util.UUID;

public class RequerimientoRepository implements RequerimientoDataSource {

    private final RequerimientoDataSource reqDataSource;

    private RequerimientoRepository(RequerimientoDataSource reqDataSource){
        this.reqDataSource = reqDataSource;
    }

    public static RequerimientoDataSource createInstance(){
        return new RequerimientoRepository(new RequerimientoRetrofitDataSource());
    }

    @Override
    public void saveRequerimiento(Requerimiento req, UUID idUsuario) {
        reqDataSource.saveRequerimiento(req, idUsuario);
    }

    @Override
    public void getAllRequerimientosFrom(UUID idUsuario) {
        reqDataSource.getAllRequerimientosFrom(idUsuario);
    }
}
