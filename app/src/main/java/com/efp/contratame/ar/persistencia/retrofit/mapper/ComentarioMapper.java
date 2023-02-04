package com.efp.contratame.ar.persistencia.retrofit.mapper;

import com.efp.contratame.ar.modelo.Comentario;
import com.efp.contratame.ar.persistencia.retrofit.entity.ComentarioRF;
import com.google.firebase.auth.FirebaseAuth;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class ComentarioMapper {

    public static Comentario fromEntity(ComentarioRF c){
        return new Comentario(
                c.getUsuario(),
                LocalDate.parse(c.getTimestamp()),
                c.getContenido()
        );
    }

    public static List<Comentario> fromEntities(List<ComentarioRF> entities) {
        return entities.stream().map(ComentarioMapper::fromEntity).collect(Collectors.toList());
    }
}
