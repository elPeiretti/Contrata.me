package com.efp.contratame.ar.persistencia.datasource;

import com.efp.contratame.ar.modelo.Prestador;
import java.util.List;
import java.util.UUID;

public interface PrestadorDataSource {

    interface GetAllPrestadoresCallback{
        void onError();
        void onResult(List<Prestador> prestadores);
    }

    interface GetPrestadorCallback{
        void onError();
        void onResult(Prestador prestador);
    }

    List<Prestador> getAllPrestadores();
    Prestador getPrestador(UUID idPrestador);
}
