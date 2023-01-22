package com.efp.contratame.ar.persistencia.repository;

import com.efp.contratame.ar.modelo.Prestador;
import com.efp.contratame.ar.modelo.Servicio;
import com.efp.contratame.ar.modelo.TiposServicios;

import java.util.List;

public class ServicioRepository {

   private static ServicioRepository _REPO = null;

   public static final List<Servicio> _SERVICIOS = List.of(
           new Servicio(new Prestador("Pedro Gómez", "https://radiojunin.com/wp-content/uploads/2020/11/plomero.jpg"), TiposServicios.PLOMERIA,"Hola hago buenos trabajos de plomeria, contratenme xfi.", 3,null),
           new Servicio(new Prestador("Lionel Messi", "https://www.mndelgolfo.com/blog/wp-content/uploads/2017/09/herramientas-para-electricista.jpg"), TiposServicios.ELECTRICIDAD, "Hola hago buenos trabajos de plomeria, contratenme xfi.", 2,null),
           new Servicio(new Prestador("AAA", "https://radiojunin.com/wp-content/uploads/2020/11/plomero.jpg"), TiposServicios.PLOMERIA,"Hola hago buenos trabajos de plomeria, contratenme xfi.", 3,null),
           new Servicio(new Prestador("BBB", "https://www.mndelgolfo.com/blog/wp-content/uploads/2017/09/herramientas-para-electricista.jpg"), TiposServicios.ELECTRICIDAD, "Hola hago buenos trabajos de plomeria, contratenme xfi.", 1,null),
           new Servicio(new Prestador("Pedro Gómez", "https://radiojunin.com/wp-content/uploads/2020/11/plomero.jpg"), TiposServicios.PLOMERIA,"Hola hago buenos trabajos de plomeria, contratenme xfi.", 5,null),
           new Servicio(new Prestador("Lionel Mes", "https://www.mndelgolfo.com/blog/wp-content/uploads/2017/09/herramientas-para-electricista.jpg"), TiposServicios.ELECTRICIDAD, "Hola hago buenos trabajos de plomeria, contratenme xfi.", 2,null),
           new Servicio(new Prestador("Lionel M", "https://www.mndelgolfo.com/blog/wp-content/uploads/2017/09/herramientas-para-electricista.jpg"), TiposServicios.ELECTRICIDAD, "desc1", 2,null),
           new Servicio(new Prestador("Lionel", "https://www.mndelgolfo.com/blog/wp-content/uploads/2017/09/herramientas-para-electricista.jpg"), TiposServicios.ELECTRICIDAD, "desc2", 2,null),
           new Servicio(new Prestador("Lio Messi", "https://www.mndelgolfo.com/blog/wp-content/uploads/2017/09/herramientas-para-electricista.jpg"), TiposServicios.ELECTRICIDAD, "desc3", 2,null),
           new Servicio(new Prestador("L Messi", "https://www.mndelgolfo.com/blog/wp-content/uploads/2017/09/herramientas-para-electricista.jpg"), TiposServicios.ELECTRICIDAD, "desc4", 2,null),
           new Servicio(new Prestador("Lucas Messi", "https://www.mndelgolfo.com/blog/wp-content/uploads/2017/09/herramientas-para-electricista.jpg"), TiposServicios.ELECTRICIDAD, "desc5", 2,null),
           new Servicio(new Prestador("Lionel Massa", "https://www.mndelgolfo.com/blog/wp-content/uploads/2017/09/herramientas-para-electricista.jpg"), TiposServicios.ELECTRICIDAD, "desc6", 2,null),
           new Servicio(new Prestador("Julio Pepe", "https://www.mndelgolfo.com/blog/wp-content/uploads/2017/09/herramientas-para-electricista.jpg"), TiposServicios.ELECTRICIDAD, "desc7", 2,null),
           new Servicio(new Prestador("Esteban Quito", "https://www.mndelgolfo.com/blog/wp-content/uploads/2017/09/herramientas-para-electricista.jpg"), TiposServicios.ELECTRICIDAD, "desc8", 2,null),
           new Servicio(new Prestador("Juan Carlos", "https://www.mndelgolfo.com/blog/wp-content/uploads/2017/09/herramientas-para-electricista.jpg"), TiposServicios.ELECTRICIDAD, "desc7", 2,null),
           new Servicio(new Prestador("John Charles", "https://www.mndelgolfo.com/blog/wp-content/uploads/2017/09/herramientas-para-electricista.jpg"), TiposServicios.ELECTRICIDAD, "desc10", 2,null)
   );





}
