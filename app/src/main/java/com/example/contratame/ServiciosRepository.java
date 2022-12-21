package com.example.contratame;

import android.content.Context;
import android.media.Image;

import com.example.contratame.modelo.Prestador;
import com.example.contratame.modelo.Servicio;
import com.example.contratame.modelo.TiposServicios;

import java.util.List;

public class ServiciosRepository {

   private static ServiciosRepository _REPO = null;
   // public Servicio(Prestador prestador, TiposServicios tipo, String descripcion, float puntuacion) {

   public static final List<Servicio> _SERVICIOS = List.of(
           new Servicio(new Prestador("Pedro Gómez", "https://radiojunin.com/wp-content/uploads/2020/11/plomero.jpg"), TiposServicios.PLOMERIA,"Hola hago buenos trabajos de plomeria, contratenme xfi.", 3),
           new Servicio(new Prestador("Lionel Messi", "https://www.mndelgolfo.com/blog/wp-content/uploads/2017/09/herramientas-para-electricista.jpg"), TiposServicios.ELECTRICIDAD, "Hola hago buenos trabajos de plomeria, contratenme xfi.", 2),
           new Servicio(new Prestador("AAA", "https://radiojunin.com/wp-content/uploads/2020/11/plomero.jpg"), TiposServicios.PLOMERIA,"Hola hago buenos trabajos de plomeria, contratenme xfi.", 3),
           new Servicio(new Prestador("BBB", "https://www.mndelgolfo.com/blog/wp-content/uploads/2017/09/herramientas-para-electricista.jpg"), TiposServicios.ELECTRICIDAD, "Hola hago buenos trabajos de plomeria, contratenme xfi.", 1),
           new Servicio(new Prestador("Pedro Gómez", "https://radiojunin.com/wp-content/uploads/2020/11/plomero.jpg"), TiposServicios.PLOMERIA,"Hola hago buenos trabajos de plomeria, contratenme xfi.", 5),
           new Servicio(new Prestador("Lionel Messi", "https://www.mndelgolfo.com/blog/wp-content/uploads/2017/09/herramientas-para-electricista.jpg"), TiposServicios.ELECTRICIDAD, "Hola hago buenos trabajos de plomeria, contratenme xfi.", 2)
   );





}
