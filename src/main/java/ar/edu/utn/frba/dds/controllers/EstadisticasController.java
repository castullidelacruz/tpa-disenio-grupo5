package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.model.estadistica.ComponenteEstadistico;
import ar.edu.utn.frba.dds.model.estadistica.Estadistica;
import ar.edu.utn.frba.dds.model.estadistica.EstadisticaCantidadSpam;
import ar.edu.utn.frba.dds.model.estadistica.EstadisticaCategoriaMaxima;
import ar.edu.utn.frba.dds.model.estadistica.EstadisticaHoraHechosCategoria;
import ar.edu.utn.frba.dds.model.estadistica.EstadisticaProvMaxHechosCategoria;
import ar.edu.utn.frba.dds.model.estadistica.EstadisticaProvMaxHechosColeccion;
import ar.edu.utn.frba.dds.model.estadistica.LocalizadorDeProvincias;
import ar.edu.utn.frba.dds.repositories.RepositorioSolicitudesDeCarga;
import ar.edu.utn.frba.dds.repositories.RepositorioSolicitudesEliminacion;
import ar.edu.utn.frba.dds.server.AppRole;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import io.javalin.http.Context;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class EstadisticasController implements WithSimplePersistenceUnit {

  EstadisticaCantidadSpam estadisticaSpam = new EstadisticaCantidadSpam();
  EstadisticaCategoriaMaxima estadisticaCm =
      new EstadisticaCategoriaMaxima();
  //EstadisticaCantidadSpam estadisticaCs = new EstadisticaCantidadSpam();
  //EstadisticaProvMaxHechosCategoria estadisticaPmhcat =
    //  new EstadisticaProvMaxHechosCategoria();
  //EstadisticaProvMaxHechosColeccion estadisticaPmhcalt =
    //  new EstadisticaProvMaxHechosColeccion();

  //Agrego Estadisticas a la carga
  List<Estadistica> estadisticas = new ArrayList<>();
    //estadisticas.add(estadisticaSpam);
    //estadisticas.add(estadisticaCm);
    //estadisticas.add(estadisticaPmhcat);
    //estadisticas.add(estadisticaPmhcalt);

  public EstadisticasController() {
    //this.repoCarga = RepositorioSolicitudesDeCarga.getInstance();
    //this.repoEliminacion = RepositorioSolicitudesEliminacion.getInstance();
  }

  // --- Mostrar Estadisticas ---
  public static Map<String, Object> mostrarSpam(Context ctx) {

    EstadisticaCantidadSpam estadisticaSpam = new EstadisticaCantidadSpam();
    estadisticaSpam.calcularEstadistica();
    var cantSpam =  estadisticaSpam.getCantidadSpam();
    var fechaAhora = LocalDate.now();

    Map<String, Object> model = Map.of(
        "cantidadSpam", cantSpam,
        "fecha",fechaAhora
    );

    ctx.render("dashboard/estadisticaSpam.hbs", model);
    return model;
  }

  public static Map<String, Object> mostrarHoraPico(Context ctx) {

    /*
    EstadisticaHoraHechosCategoria estadisticaHHC = new EstadisticaHoraHechosCategoria("cortes");
    estadisticaHHC.calcularEstadistica();
    var cantSpam =  estadisticaHHC.gethoraPicoCategoria();
    var fechaAhora = LocalDate.now();
    */

    var fechaAhora = LocalDate.now();
    var categoria = "Categoria Random";
    var horaPico = LocalDate.now();

    Map<String, Object> model = Map.of(
        "categoria", categoria,
        "fecha",fechaAhora,
        "horaPico", horaPico
    );

    ctx.render("dashboard/estadisticaHoraPico.hbs", model);
    return model;
  }

}
