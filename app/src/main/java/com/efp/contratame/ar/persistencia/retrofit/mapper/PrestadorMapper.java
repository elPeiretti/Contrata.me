package com.efp.contratame.ar.persistencia.retrofit.mapper;

import com.efp.contratame.ar.modelo.Prestador;
import com.efp.contratame.ar.persistencia.retrofit.entity.PrestadorRF;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class PrestadorMapper {

    public static Prestador sinServiciosFromEntity(PrestadorRF entity){
        return new Prestador(
                UUID.fromString(entity.getIdPrestador()),
                entity.getNombre(),
                null,
                entity.getImagenPerfil()
        );
    }

    public static List<Prestador> sinServiciosFromEntities(List<PrestadorRF> entities){
        return entities.stream()
                .map(PrestadorMapper::sinServiciosFromEntity)
                .collect(Collectors.toList());
    }
}
