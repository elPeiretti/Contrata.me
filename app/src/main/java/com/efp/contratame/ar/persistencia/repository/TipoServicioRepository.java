package com.efp.contratame.ar.persistencia.repository;

import com.efp.contratame.ar.modelo.TipoServicio;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TipoServicioRepository {

    private static TipoServicioRepository _REPO = null;

    // for debugging purposes
    public static final List<TipoServicio> _TIPOSERVICIOS = List.of(
            new TipoServicio("Servicio 1", "https://assets.stickpng.com/images/6002f9d851c2ec00048c6c78.png"),
            new TipoServicio("Servicio dos", "https://uxwing.com/wp-content/themes/uxwing/download/hand-gestures/good-icon.png"),
            new TipoServicio("Servicio serv", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQlUZKpMaRrQBlWOp6paedKAGY9vsTbnhs9SQ&usqp=CAU"),
            new TipoServicio("Serviserv", "https://static.vecteezy.com/system/resources/previews/001/206/198/original/mountain-icon-png.png"),
            new TipoServicio("Official Bosch", "https://seeklogo.com/images/B/Bosch_Service-logo-A26710111C-seeklogo.com.png"),
            new TipoServicio("Streaming", "https://img.favpng.com/7/24/21/logo-icon-game-icon-xbox-logo-icon-dVj3RELK.jpg"),
            new TipoServicio("Otro serv","https://img.icons8.com/external-flaticons-flat-flat-icons/600/000000/external-single-hotel-management-flaticons-flat-flat-icons.png"),
            new TipoServicio("Enfermeria", "https://cdn-icons-png.flaticon.com/512/206/206859.png"),
            new TipoServicio("Carpinteria", "https://cdn.iconscout.com/icon/premium/png-256-thumb/carpentry-work-1-769352.png"),
            new TipoServicio("Electricista", "https://cdn-icons-png.flaticon.com/512/1983/1983275.png"),
            new TipoServicio("Plomeria", "https://icons.iconarchive.com/icons/atyourservice/service-categories/512/Plumbing-icon.png"),
            new TipoServicio("Desarrollo web", "https://toppng.com/uploads/preview/web-development-icon-png-clipart-website-development-web-dev-icon-11562967383vhxlfmnerz.png"),
            new TipoServicio("Migracion de datos", "https://cdn-icons-png.flaticon.com/512/2857/2857376.png"),
            new TipoServicio("Chef privado", "data:image/png;base64,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"),
            new TipoServicio("Profesor particular", "https://cdn-icons-png.flaticon.com/512/4297/4297905.png"),
            new TipoServicio("Servicio", "https://cdn-icons-png.flaticon.com/512/891/891948.png"),
            new TipoServicio("Otro", "https://i.pinimg.com/originals/58/0f/13/580f139b3ac232030a812614cfc8585b.png")
    );

    public static final Map<String,TipoServicio> TIPOSERVICIO_MAP = _TIPOSERVICIOS.stream().collect(Collectors.toMap(TipoServicio::getNombre,t -> t));
}