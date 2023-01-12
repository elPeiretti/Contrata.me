package com.efp.contratame.ar.persistencia.repository;

import com.efp.contratame.ar.modelo.Prestador;
import com.efp.contratame.ar.modelo.Servicio;
import com.efp.contratame.ar.modelo.TiposServicios;

import java.util.List;

public class ServicioRepository {

    //for debugging purposes
    public static final List<Servicio> SERVICIO_LIST = List.of(
            new Servicio(new Prestador("Juan juanes","no imagen"), TiposServicios.AIRE_ACONDICIONADO,
                    "descripcion2", 5f),
            new Servicio(new Prestador("Juan juan","no imagen"), TiposServicios.CARPINTERIA,
                    "descripcion3", 3.5f),
            new Servicio(new Prestador("Juan john","no imagen"), TiposServicios.CONSTRUCCIÓN,
                    "descripcion4", 2f),
            new Servicio(new Prestador("Juan julio","no imagen"), TiposServicios.ELECTRICIDAD,
                    "descripcion5", 2.3f),
            new Servicio(new Prestador("Juan juarez","no imagen"), TiposServicios.AIRE_ACONDICIONADO,
                    "descripcion6", 3.3f),
            new Servicio(new Prestador("Juan justo","no imagen"), TiposServicios.GASISTA,
                    "descripcion7", 4.9f),
            new Servicio(new Prestador("Juan julian","no imagen"), TiposServicios.GASISTA,
                    "descripcion8", 3.1f)
    );

}
