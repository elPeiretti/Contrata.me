package com.efp.contratame.ar.persistencia.repository;

import com.efp.contratame.ar.persistencia.datasource.PrestadorDataSource;
import com.efp.contratame.ar.persistencia.retrofit.PrestadorRetrofitDataSource;

import java.util.UUID;

public class PrestadorRepository implements PrestadorDataSource {

    private PrestadorDataSource prestadorDataSource;

    private PrestadorRepository(){}
    private PrestadorRepository(PrestadorDataSource prestadorDataSource){
        this.prestadorDataSource = prestadorDataSource;
    }

    public static PrestadorDataSource createInstance(){
        return new PrestadorRepository(new PrestadorRetrofitDataSource());
    }

    @Override
    public void getAllPrestadores(GetAllPrestadoresCallback callback) {
        prestadorDataSource.getAllPrestadores(callback);
    }

    @Override
    public void getPrestador(UUID idPrestador, GetPrestadorCallback callback) {
        prestadorDataSource.getPrestador(idPrestador, callback);
    }
}
