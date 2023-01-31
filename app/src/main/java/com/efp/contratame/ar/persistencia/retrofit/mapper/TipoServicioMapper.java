package com.efp.contratame.ar.persistencia.retrofit.mapper;

import com.efp.contratame.ar.modelo.TipoServicio;
import com.efp.contratame.ar.persistencia.retrofit.entity.TipoServicioRF;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class TipoServicioMapper {

    public static List<TipoServicio> fromEntities(List<TipoServicioRF> entities){
        return entities.stream().map(TipoServicioMapper::fronEntity).collect(Collectors.toList());
    }

    public static TipoServicio fronEntity(TipoServicioRF entity){
        return new TipoServicio(
                UUID.fromString(entity.getIdTipoServicio()),
                entity.getNombre(),
                entity.getIcono()
        );
    }

}
