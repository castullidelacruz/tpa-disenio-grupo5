package ar.edu.utn.frba.dds.scripts;

import ar.edu.utn.frba.dds.model.entities.Hecho;
import ar.edu.utn.frba.dds.model.entities.User;
import ar.edu.utn.frba.dds.model.entities.criterios.CriterioCategoria;
import ar.edu.utn.frba.dds.model.entities.criterios.CriterioDescripcion;
import ar.edu.utn.frba.dds.model.entities.criterios.CriterioFecha;
import ar.edu.utn.frba.dds.model.entities.criterios.CriterioFechaCarga;
import ar.edu.utn.frba.dds.model.entities.criterios.CriterioRangoFechas;
import ar.edu.utn.frba.dds.model.entities.criterios.CriterioTitulo;
import ar.edu.utn.frba.dds.model.entities.criterios.CriterioUbicacion;
import ar.edu.utn.frba.dds.model.entities.fuentes.Fuente;
import ar.edu.utn.frba.dds.model.entities.fuentes.FuenteDinamica;
import ar.edu.utn.frba.dds.model.entities.fuentes.TipoFuente;
import ar.edu.utn.frba.dds.model.entities.solicitudes.SolicitudDeCarga;
import ar.edu.utn.frba.dds.repositories.RepositorioFuentes;
import ar.edu.utn.frba.dds.server.AppRole;
import ar.edu.utn.frba.dds.service.ServicioAutenticacion;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.time.LocalDateTime;

public class Bootstrap implements WithSimplePersistenceUnit {
  public static void init() {
    new Bootstrap().cargarDatosIniciales();
  }

  private void cargarDatosIniciales() {
    withTransaction(() -> {
      RepositorioFuentes repoFuentes = RepositorioFuentes.getInstance();
      ServicioAutenticacion servicioAutenticacion = new ServicioAutenticacion();
      servicioAutenticacion.registerUser("admin", "admin123", AppRole.ADMIN);

      // 1. Verificar si ya hay datos
      Long conteoFuentes = entityManager()
          .createQuery("SELECT COUNT(f) FROM Fuente f", Long.class).getSingleResult();

      if (conteoFuentes > 0) {
        System.out.println("--- Seeder: Los datos iniciales ya existen. Omitiendo carga.");
        return;
      }

      // --- 2. CREAR FUENTE DINÁMICA  ---
      FuenteDinamica fuenteDinamica = new FuenteDinamica();
      entityManager().persist(fuenteDinamica);

      Fuente fuenteAsociada = entityManager().find(Fuente.class, fuenteDinamica.getId());

      System.out.println("--- Seeder: Fuente Dinámica inicial creada con ID: "
          + fuenteAsociada.getId());

      // --- 3. CREAR HECHOS DE PRUEBA ASOCIADOS ---

      Hecho hecho1 = new Hecho(
          "Incendio en Barrio Sur - Edificio A",
          "Se reporta foco de incendio en un edificio abandonado, cerca de la plaza.",
          "Incendio",
          -34.6037,
          -58.3816,
          LocalDateTime.now().minusDays(2),
          LocalDateTime.now(),
          TipoFuente.DINAMICA,
          "url_imagen_incendio.jpg",
          Boolean.TRUE,
          fuenteAsociada
      );

      Hecho hecho2 = new Hecho(
          "Accidente Vial - Av. 9 de Julio",
          "Colisión múltiple en el centro, visibilidad baja por neblina.",
          "Accidente Vial",
          -34.6090,
          -58.3820,
          LocalDateTime.now().minusHours(5),
          LocalDateTime.now(),
          TipoFuente.DINAMICA,
          "url_video_accidente.mp4",
          Boolean.TRUE,
          fuenteAsociada
      );

      Hecho hecho3 = new Hecho(
          "Mancha tóxica en el río",
          "Se observa vertido ilegal de residuos industriales en el Riachuelo.",
          "Contaminación",
          -34.6400,
          -58.3500,
          LocalDateTime.now().minusDays(1),
          LocalDateTime.now(),
          TipoFuente.DINAMICA,
          null,
          Boolean.TRUE,
          fuenteAsociada
      );

      entityManager().persist(hecho1);
      entityManager().persist(hecho2);
      entityManager().persist(hecho3);

      SolicitudDeCarga solicitudDeCarga = new SolicitudDeCarga("abc", "abc", "abc",
          27.0, 26.0, LocalDateTime.now(), null, false, fuenteDinamica);
      entityManager().persist(solicitudDeCarga);
      CriterioCategoria criterioCategoria = new CriterioCategoria();
      entityManager().persist(criterioCategoria);
      CriterioDescripcion criterioDescripcion = new CriterioDescripcion();
      entityManager().persist(criterioDescripcion);
      CriterioFecha criterioFecha = new CriterioFecha();
      entityManager().persist(criterioFecha);
      CriterioFechaCarga criterioFechaCarga = new CriterioFechaCarga();
      entityManager().persist(criterioFechaCarga);
      CriterioRangoFechas criterioRangoFechas = new CriterioRangoFechas();
      entityManager().persist(criterioFechaCarga);
      CriterioUbicacion criterioUbicacion = new CriterioUbicacion();
      entityManager().persist(criterioUbicacion);
      CriterioTitulo criterioTitulo = new CriterioTitulo();
      entityManager().persist(criterioTitulo);

      System.out.println("--- Seeder: 3 Hechos de prueba creados para eliminación.");
    });
  }

}
