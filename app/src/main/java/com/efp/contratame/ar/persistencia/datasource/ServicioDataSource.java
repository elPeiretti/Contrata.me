package com.efp.contratame.ar.persistencia.datasource;

import com.efp.contratame.ar.modelo.Servicio;
import com.efp.contratame.ar.modelo.TipoServicio;
import java.util.List;

public interface ServicioDataSource {

    interface GetAllServiciosDelTipoCallback{
        void onResult(List<Servicio> servicios);
        void onError();
    }

    void getAllServiciosDelTipo(TipoServicio tipoServicio, GetAllServiciosDelTipoCallback callback);
    // Como no se implementa la parte del prestador, no es necesario implementar
    // el guardado de un nuevo servicio.
}
