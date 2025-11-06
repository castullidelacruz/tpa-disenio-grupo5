package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.model.entities.Coleccion;
import ar.edu.utn.frba.dds.model.entities.GeneradorHandleUuid;
import ar.edu.utn.frba.dds.model.entities.algoritmosconcenso.AlgoritmoDeConsenso;
import ar.edu.utn.frba.dds.model.entities.criterios.Criterio;
import ar.edu.utn.frba.dds.model.entities.fuentes.Fuente;
import ar.edu.utn.frba.dds.repositories.RepositorioColecciones;
import ar.edu.utn.frba.dds.repositories.RepositorioCriterios;
import ar.edu.utn.frba.dds.repositories.RepositorioFuentes;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import io.javalin.http.Context;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ColeccionController implements WithSimplePersistenceUnit {

  private final RepositorioColecciones repoColecciones = RepositorioColecciones.getInstance();
  private final RepositorioFuentes repoFuentes = RepositorioFuentes.getInstance();
  private final RepositorioCriterios repoCriterios = RepositorioCriterios.getInstance();

  public void mostrarFormulario(Context ctx) {

    var todasLasFuentes = repoFuentes.getFuentes();
    var todosLosCriterios = repoCriterios.obtenerTodos();
    var todosLosAlgoritmos = List.of(AlgoritmoDeConsenso.values());

    Map<String, Object> model = new HashMap<>();
    model.put("todasLasFuentes", todasLasFuentes);
    model.put("todosLosCriterios", todosLosCriterios);
    model.put("todosLosAlgoritmos", todosLosAlgoritmos);

    String flashMessage = ctx.sessionAttribute("flash_message");
    if (flashMessage != null) {
      model.put("flash_message", flashMessage);
      ctx.sessionAttribute("flash_message", null);
    }

    String flashError = ctx.sessionAttribute("flash_error");
    if (flashError != null) {
      model.put("flash_error", flashError);
      ctx.sessionAttribute("flash_error", null);
    }

    ctx.render("/dashboard/creacion-coleccion.hbs", model);
  }

  public void crearColeccion(Context ctx) {
    String titulo = ctx.formParam("titulo");
    String descripcion = ctx.formParam("descripcion");
    Long fuenteId = ctx.formParamAsClass("fuenteId", Long.class).get();
    AlgoritmoDeConsenso algoritmo = AlgoritmoDeConsenso.valueOf(ctx.formParam("algoritmo"));
    List<Long> criterioIds = ctx.formParamsAsClass("criterioIds", Long.class).getOrDefault(List.of());

    try {
      withTransaction(() -> {
        Fuente fuente = repoFuentes.getFuente(fuenteId);
        List<Criterio> criterios = repoCriterios.obtenerCriteriosPorId(criterioIds);
        GeneradorHandleUuid generador = new GeneradorHandleUuid();
        String handler = generador.generar();

        Coleccion nuevaColeccion = new Coleccion(
            titulo,
            descripcion,
            fuente,
            criterios,
            handler,
            algoritmo
        );

        repoColecciones.cargarColeccion(nuevaColeccion);
      });

      ctx.sessionAttribute("flash_message", "Colección creada exitosamente!");

    } catch (Exception e) {
      e.printStackTrace();
      ctx.sessionAttribute("flash_error", "Error al crear la colección: " + e.getMessage());
    }

    ctx.redirect("/dashboard/colecciones/crear");
  }
}