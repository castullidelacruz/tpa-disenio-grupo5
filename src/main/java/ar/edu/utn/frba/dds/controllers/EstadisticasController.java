package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.model.estadistica.ComponenteEstadistico;
import ar.edu.utn.frba.dds.model.estadistica.Estadistica;
import ar.edu.utn.frba.dds.model.estadistica.EstadisticaCantidadSpam;
import ar.edu.utn.frba.dds.model.estadistica.EstadisticaCategoriaMaxima;
import ar.edu.utn.frba.dds.model.estadistica.EstadisticaHoraHechosCategoria;
import ar.edu.utn.frba.dds.model.estadistica.EstadisticaProvMaxHechosCategoria;
import ar.edu.utn.frba.dds.model.estadistica.EstadisticaProvMaxHechosColeccion;
import ar.edu.utn.frba.dds.repositories.RepositorioColecciones;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import io.javalin.http.Context;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class EstadisticasController implements WithSimplePersistenceUnit {

  static RepositorioColecciones repoC = RepositorioColecciones.getInstance();
  static ComponenteEstadistico repoE = ComponenteEstadistico.getInstance();

  // --- Mostrar Estadisticas ---
  public static Map<String, Object> mostrarSpam(Context ctx) throws IOException {
    //repoC.consesuareEchos();
    //repoE.calcularEstadisticas();

    repoE.getEstadisticaSpam().exportarEstadistica("descargar/estadisticas_cantidad_spam.csv");

    var cantSpam =  repoE.getEstadisticaSpam().getCantidadSpam();
    var fechaAhora = LocalDateTime.now();

    Map<String, Object> model = Map.of(
        "cantidadSpam", cantSpam,
        "fecha", fechaAhora
    );

    ctx.render("dashboard/estadisticaSpam.hbs", model);
    return model;
  }

  public static Map<String, Object> mostrarHoraPico(Context ctx) throws IOException {
    //repoC.consesuareEchos();
    //repoE.calcularEstadisticas();

    repoE.getEstadisticaHhc().exportarEstadistica("descargar/estadisticas_categoria_horaspico.csv");

    var reporte =  repoE.getEstadisticaHhc().getReporte();
    var fechaAhora = LocalDateTime.now();

    Map<String, Object> model = Map.of(
        "fecha", fechaAhora,
        "reporte", reporte
    );

    ctx.render("dashboard/estadisticaHoraPico.hbs", model);
    return model;
  }

  public static Map<String, Object> mostrarCategoriaMaxima(Context ctx) throws IOException {
    //repoC.consesuareEchos();
    //repoE.calcularEstadisticas();

    repoE.getEstadisticaCm().exportarEstadistica("descargar/estadisticas_categoria_maxima.csv");

    var reporte =  repoE.getEstadisticaCm().getReporte();
    var fechaAhora = LocalDateTime.now();

    Map<String, Object> model = Map.of(
        "fecha", fechaAhora,
        "reporte", reporte
    );
    ctx.render("dashboard/estadisticaCategoriaMaxima.hbs", model);
    return model;
  }

  public static Map<String, Object> mostrarCategoriaProvinciaMaxHechos(Context ctx)
      throws IOException {
    //repoC.consesuareEchos();
    //repoE.calcularEstadisticas();

    repoE.getEstadisticaPmhCat().exportarEstadistica("descargar/estadisticas_categoria_hechosmaximos.csv");

    var reporte =  repoE.getEstadisticaPmhCat().getReporte();
    var fechaAhora = LocalDateTime.now();

    Map<String, Object> model = Map.of(
        "fecha", fechaAhora,
        "reporte", reporte
    );
    ctx.render("dashboard/estadisticaCategoriaProvinciaMax.hbs", model);
    return model;
  }

  public static Map<String, Object> mostrarColeccionProvinciaMaxHechos(Context ctx)
      throws IOException {
    //repoC.consesuareEchos();
    //repoE.calcularEstadisticas();

    repoE.getEstadisticaPmhCol().exportarEstadistica("descargar/estadisticas_coleccion_hechosmaximos.csv");

    var reporte =  repoE.getEstadisticaPmhCol().getReporte();
    var fechaAhora = LocalDateTime.now();

    Map<String, Object> model = Map.of(
        "fecha", fechaAhora,
        "reporte", reporte
    );
    ctx.render("dashboard/estadisticaColeccionProvinciaMax.hbs", model);
    return model;
  }


}
