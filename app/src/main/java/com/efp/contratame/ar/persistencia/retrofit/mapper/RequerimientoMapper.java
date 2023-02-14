package com.efp.contratame.ar.persistencia.retrofit.mapper;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;

import com.efp.contratame.ar.modelo.Prestador;
import com.efp.contratame.ar.modelo.Requerimiento;
import com.efp.contratame.ar.modelo.TipoServicio;
import com.efp.contratame.ar.persistencia.retrofit.entity.RequerimientoRF;
import com.google.android.gms.maps.model.LatLng;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Base64;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class RequerimientoMapper {

    public static Requerimiento fromEntity(RequerimientoRF entity, TipoServicio tipoServicio){
        Bitmap foto = null;
        if (!entity.getImagen().isEmpty()){
            byte[] fotoDecoded = Base64.getDecoder().decode(entity.getImagen());
            foto = BitmapFactory.decodeByteArray(fotoDecoded, 0, fotoDecoded.length);
        }

        return new Requerimiento(
                UUID.fromString(entity.getIdRequerimiento()),
                entity.getTitulo(),
                tipoServicio,
                entity.getDescripcion(),
                foto,
                new LatLng(Double.parseDouble(entity.getUbicLatitud()), Double.parseDouble(entity.getUbicLongitud()))
        );
    }

    public static List<Requerimiento> fromEntities(List<RequerimientoRF> entities, List<TipoServicio> tipoServicios){
        return entities.stream()
                .map(r -> RequerimientoMapper.fromEntity(
                        r,
                        tipoServicios.stream()
                                .filter(t -> t.getIdTipoServicio().toString().equals(r.getKeyRubro()))
                                .findFirst()
                                .orElse(null)))
                .collect(Collectors.toList());
    }

    public static RequerimientoRF toEntity(Requerimiento req){
        byte[] fotoByteArray = null;
        if(req.getImagen() != null) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            req.getImagen().compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            fotoByteArray = byteArrayOutputStream.toByteArray();
        }

        return new RequerimientoRF(
                req.getIdRequerimiento().toString(),
                req.getTitulo(),
                req.getRubro().getIdTipoServicio().toString(),
                req.getDescripcion(),
                req.getImagen() == null ? "" : Base64.getEncoder().encodeToString(fotoByteArray),
                String.valueOf(req.getUbicacion().latitude),
                String.valueOf(req.getUbicacion().longitude)
        );
    }
}
