package com.efp.contratame.ar;

import com.efp.contratame.ar.modelo.Prestador;
import com.efp.contratame.ar.modelo.Servicio;
import com.efp.contratame.ar.modelo.TiposServicios;

import java.util.List;

public class ServiciosRepository {

   private static ServiciosRepository _REPO = null;
   // public Servicio(Prestador prestador, TiposServicios tipo, String descripcion, float puntuacion) {

   public static List<String> galeriaImagenes= List.of(
           "https://i.pinimg.com/originals/b2/1e/03/b21e03c83670e1badfbb70ff8b59b80f.jpg",
           "https://i.ytimg.com/vi/CCRdNq9E8PQ/maxresdefault.jpg" ,
           "https://http2.mlstatic.com/D_NQ_NP_667603-MLA48013518275_102021-O.webp"
   );
   public static final List<Servicio> _SERVICIOS = List.of(
           new Servicio(new Prestador("Pedro Gómez", "https://radiojunin.com/wp-content/uploads/2020/11/plomero.jpg"), TiposServicios.PLOMERIA,"Hola hago buenos trabajos de plomeria, contratenme xfi.", 3 ,galeriaImagenes ),
           new Servicio(new Prestador("Lionel Messi", "https://www.mndelgolfo.com/blog/wp-content/uploads/2017/09/herramientas-para-electricista.jpg"), TiposServicios.ELECTRICIDAD, "Hola hago buenos trabajos de plomeria, contratenme xfi.", 2,galeriaImagenes ),
           new Servicio(new Prestador("AAA", "https://radiojunin.com/wp-content/uploads/2020/11/plomero.jpg"), TiposServicios.PLOMERIA,"Hola hago buenos trabajos de plomeria, contratenme xfi.", 3,galeriaImagenes ),
           new Servicio(new Prestador("BBB", "https://www.mndelgolfo.com/blog/wp-content/uploads/2017/09/herramientas-para-electricista.jpg"), TiposServicios.ELECTRICIDAD, "Hola hago buenos trabajos de plomeria, contratenme xfi.", 1,galeriaImagenes ),
           new Servicio(new Prestador("Pedro Gómez", "https://radiojunin.com/wp-content/uploads/2020/11/plomero.jpg"), TiposServicios.PLOMERIA,"Hola hago buenos trabajos de plomeria, contratenme xfi.", 5,galeriaImagenes ),
           new Servicio(new Prestador("Lionel Messi", "https://www.mndelgolfo.com/blog/wp-content/uploads/2017/09/herramientas-para-electricista.jpg"), TiposServicios.ELECTRICIDAD, "Hola hago buenos trabajos de plomeria, contratenme xfi.", 2,galeriaImagenes )
   );






}
