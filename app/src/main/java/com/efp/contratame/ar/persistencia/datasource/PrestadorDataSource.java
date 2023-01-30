package com.efp.contratame.ar.persistencia.datasource;

import com.efp.contratame.ar.modelo.Prestador;
import java.util.List;
import java.util.UUID;

public interface PrestadorDataSource {

    interface GetAllPrestadoresSinServiciosCallback {
        void onError();
        void onResult(List<Prestador> prestadores);
    }

    interface GetPrestadorCallback{
        void onError();
        void onResult(Prestador prestador);
    }

    void getAllPrestadoresSinServicios(GetAllPrestadoresSinServiciosCallback callback);
    void getPrestador(UUID idPrestador, GetPrestadorCallback callback);
}
