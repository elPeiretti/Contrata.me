package com.efp.contratame.ar.persistencia.retrofit.mapper;

import com.efp.contratame.ar.modelo.Prestador;
import com.efp.contratame.ar.modelo.Servicio;
import com.efp.contratame.ar.modelo.TipoServicio;
import com.efp.contratame.ar.persistencia.retrofit.entity.ServicioRF;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class ServicioMapper {

    public static Servicio fromEntity(ServicioRF s, TipoServicio tipoServicio, List<Prestador> prestadores){

        Prestador prest = prestadores.stream()
                .filter(p -> p.getIdPrestador().toString().equals(s.getKeyPrestador()))
                .findFirst()
                .orElse(null);

        return new Servicio(
                UUID.fromString(s.getIdServicio()),
                prest,
                tipoServicio,
                s.getDescripcion(),
                Float.parseFloat(s.getPuntuacion()),
                s.getGaleriaImagenes()
        );
    }

    public static List<Servicio> fromEntities(List<ServicioRF> data, TipoServicio tipoServicio, List<Prestador> prestadores) {
        return data.stream()
                .map(s -> ServicioMapper.fromEntity(s,tipoServicio,prestadores))
                .collect(Collectors.toList());
    }
}
