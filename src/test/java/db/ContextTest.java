package db;

import ar.edu.utn.frba.dds.dominio.Coleccion;
import ar.edu.utn.frba.dds.dominio.GeneradorHandleUuid;
import ar.edu.utn.frba.dds.dominio.Hecho;
import ar.edu.utn.frba.dds.dominio.criterios.Criterio;
import ar.edu.utn.frba.dds.dominio.criterios.CriterioBase;
import ar.edu.utn.frba.dds.dominio.estadistica.EstadisticaCantidadSpam;
import ar.edu.utn.frba.dds.dominio.estadistica.EstadisticaCategoriaMaxima;
import ar.edu.utn.frba.dds.dominio.estadistica.EstadisticaProvMaxHechosCategoria;
import ar.edu.utn.frba.dds.dominio.fuentes.Agregador;
import ar.edu.utn.frba.dds.dominio.fuentes.FuenteDataSet;
import ar.edu.utn.frba.dds.dominio.fuentes.FuenteDinamica;
import ar.edu.utn.frba.dds.dominio.fuentes.TipoFuente;
import ar.edu.utn.frba.dds.dominio.repositorios.RepositorioHechos;
import ar.edu.utn.frba.dds.dominio.repositorios.RepositorioSolicitudesDeCarga;
import ar.edu.utn.frba.dds.dominio.repositorios.RepositorioSolicitudesEliminacion;
import ar.edu.utn.frba.dds.dominio.solicitudes.DetectorDeSpam;
import ar.edu.utn.frba.dds.dominio.solicitudes.FactorySolicitudDeEliminacion;
import ar.edu.utn.frba.dds.dominio.solicitudes.SolicitudDeEliminacion;
import io.github.flbulgarelli.jpa.extras.test.SimplePersistenceTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ar.edu.utn.frba.dds.dominio.fuentes.Fuente;
import ar.edu.utn.frba.dds.dominio.estadistica.EstadisticaProvMaxHechosColeccion;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ContextTest implements SimplePersistenceTest {

  Hecho hecho;
  Hecho hecho2;
  Hecho hecho3;

  @BeforeEach
  public void fixtureBeforeEach() {
  hecho = new Hecho(
      "Corte de luz modificado",
      "Corte de luz en zona oeste",
      "cortes", -27.782412, -63.252387,
      LocalDate.of(2025, 1, 18),
      LocalDate.now(),
      TipoFuente.DINAMICA,
      "http://multimediavalue",
      Boolean.TRUE
  );

  hecho2 = new Hecho(
      "Corte de luz modificado",
      "Corte de luz en zona oeste",
      "cortes", -40.7344,-66.6617,
      LocalDate.of(2025, 1, 18),
      LocalDate.now(),
      TipoFuente.DINAMICA,
      "http://multimediavalue",
      Boolean.TRUE
  );

  hecho3 = new Hecho(
      "incendio en new York",
      "Corte de luz en zona oeste",
      "incedio", -40.7334,-66.6613,
      LocalDate.of(2025, 1, 18),
      LocalDate.now(),
      TipoFuente.DINAMICA,
      "http://multimediavalue",
      Boolean.TRUE
  );
  }

  @Test
  void contextUp() {
    assertNotNull(entityManager());
  }

  @Test
  void contextUpWithTransaction() throws Exception {
    withTransaction(() -> {
    });
  }

  @Test
  public void testEstadisticaCategoriaMaxima() {
    RepositorioHechos repositorio = new RepositorioHechos();

    repositorio.cargarHecho(hecho);
    repositorio.cargarHecho(hecho2);
    repositorio.cargarHecho(hecho3);

    EstadisticaCategoriaMaxima estadisticaCM = new EstadisticaCategoriaMaxima ();
    estadisticaCM.calcularEstadistica();

    Assertions.assertEquals("cortes", estadisticaCM.getCategoriaMax());

  }

  @Test
  public void testEstadisticaCantidadSpam() {
    RepositorioHechos repositorioH = new RepositorioHechos();

    FactorySolicitudDeEliminacion factory;
    DetectorDeSpam inter = mock(DetectorDeSpam.class);
    when(inter.esSpam("Motivo válido")).thenReturn(false);
    when(inter.esSpam("Motivo invalido")).thenReturn(true);
    RepositorioSolicitudesEliminacion  repositorio = new RepositorioSolicitudesEliminacion();

    repositorioH.cargarHecho(hecho); //Se necesita cargar el hecho para poder cargar la solicitud

    factory = new FactorySolicitudDeEliminacion(inter);

    SolicitudDeEliminacion solicitud1 = factory.crear(hecho, "Motivo invalido");
    repositorio.cargarSolicitudEliminacion(solicitud1);


    EstadisticaCantidadSpam estadisticaCS = new EstadisticaCantidadSpam();
    estadisticaCS.calcularEstadistica();

    Assertions.assertEquals(1, estadisticaCS.getCantidadSpam());

  }

  @Test
  public void testEstadisticaProvMaxHechosCategoria() {
    RepositorioHechos repositorioH = new RepositorioHechos();

    Hecho hecho = new Hecho(
        "Corte de luz modificado",
        "Corte de luz en zona oeste",
        "cortes", -27.782412, -63.252387,
        LocalDate.of(2025, 1, 18),
        LocalDate.now(),
        TipoFuente.DINAMICA,
        "http://multimediavalue",
        Boolean.TRUE
    );

    repositorioH.cargarHecho(hecho);

    EstadisticaProvMaxHechosCategoria estadisticaPMHC = new EstadisticaProvMaxHechosCategoria("cortes");
    estadisticaPMHC.calcularEstadistica();

    Assertions.assertEquals("Santiago del Estero", estadisticaPMHC.getProvinciaMax());
  }

  @Test
  public void testEstadisticaProvMaxHechosColeccion() {
    GeneradorHandleUuid generador = new GeneradorHandleUuid();
    RepositorioHechos repositorioHechos = new RepositorioHechos();
    FuenteDinamica dinamica = new FuenteDinamica();
    CriterioBase criterio = new CriterioBase();
    List<Criterio> criterios = new ArrayList<>(Arrays.asList(criterio));

    repositorioHechos.cargarHecho(hecho);
    repositorioHechos.cargarHecho(hecho2);
    repositorioHechos.cargarHecho(hecho3);

    dinamica.actualiza(repositorioHechos);

    Coleccion coleccion = new Coleccion("incendios forestales",
        "incendios en la patagonia",
        dinamica, criterios, generador.generar(),null);

    EstadisticaProvMaxHechosColeccion estadisticaPMHC = new EstadisticaProvMaxHechosColeccion(coleccion);
    estadisticaPMHC.calcularEstadistica();

    Assertions.assertEquals("Río Negro", estadisticaPMHC.getProvinciaMax());
  }
}

