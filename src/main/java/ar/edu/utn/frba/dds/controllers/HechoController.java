package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.model.entities.fuentes.Fuente;
import ar.edu.utn.frba.dds.model.entities.fuentes.FuenteDinamica;
import ar.edu.utn.frba.dds.model.entities.solicitudes.SolicitudDeCarga;
import ar.edu.utn.frba.dds.repositories.RepositorioFuentes;
import ar.edu.utn.frba.dds.repositories.RepositorioSolicitudesDeCarga;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import io.javalin.http.Context;
import org.apache.avro.reflect.Nullable;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HechoController implements WithSimplePersistenceUnit {

  private final RepositorioSolicitudesDeCarga repoSolicitudes;
  private final RepositorioFuentes repoFuentes;

  public HechoController() {
    this.repoSolicitudes = RepositorioSolicitudesDeCarga.getInstance();
    this.repoFuentes = RepositorioFuentes.getInstance();
  }

  // --- Mostrar formulario de creación de hecho ---
  public Map<String, Object> showCreationForm(@NotNull Context ctx) {

    Long fuenteId = ctx.sessionAttribute("fuenteId");
    boolean esRegistrado = ctx.sessionAttribute("usuarioRegistrado") != null;

    Fuente fuente = null;
    List<FuenteDinamica> fuentesDisponibles = List.of();

    // Si es anónimo → mostrar todas las fuentes dinámicas
    if (fuenteId == null) {
      fuentesDisponibles = repoFuentes.getFuentesDinamicas();
    } else {
      fuente = repoFuentes.getFuente(fuenteId);
    }

    List<String> categorias = List.of("Incendio", "Accidente Vial", "Contaminación", "Otro");

    Map<String, Object> model = new HashMap<>();
    model.put("titulo", "Cargar Nuevo Hecho");
    model.put("categorias", categorias);
    model.put("fuente", fuente);
    model.put("fuentesDisponibles", fuentesDisponibles);
    model.put("esRegistrado", esRegistrado);
    return model;

  }

  // --- Crear hecho ---
  public void create(Context ctx) {
    try {
      Long fuenteId = ctx.sessionAttribute("fuenteId");
      String fuenteHecho = ctx.formParam("fuenteId"); // si vino del form manualmente

      Fuente fuenteAsociada = null;

      if (fuenteId != null) {
        fuenteAsociada = repoFuentes.getFuente(fuenteId);
      } else if (fuenteHecho != null) {
        fuenteAsociada = repoFuentes.getFuente(Long.parseLong(fuenteHecho));
      }

      if (fuenteAsociada == null) {
        ctx.status(400);
        ctx.sessionAttribute("flash_error", "Debe seleccionar o tener asociada una fuente válida.");
        ctx.redirect("/hechos/nuevo");
        return;
      }

      // --- Obtener parámetros del formulario ---
      String titulo = ctx.formParam("titulo");
      String descripcion = ctx.formParam("descripcion");
      String categoria = ctx.formParam("categoria");
      String fechaAcontecimientoStr = ctx.formParam("fechaAcontecimiento");
      String multimedia = ctx.formParam("multimedia");
      String latitudStr = ctx.formParam("latitud");
      String longitudStr = ctx.formParam("longitud");

      if (titulo == null || titulo.isBlank() ||
          descripcion == null || descripcion.isBlank() ||
          categoria == null || categoria.isBlank() ||
          fechaAcontecimientoStr == null || fechaAcontecimientoStr.isBlank()) {
        ctx.status(400);
        ctx.sessionAttribute("flash_error", "Debe completar todos los campos obligatorios.");
        ctx.redirect("/hechos/nuevo");
        return;
      }

      double latitud = Double.parseDouble(latitudStr);
      double longitud = Double.parseDouble(longitudStr);
      LocalDateTime fechaAcontecimiento = LocalDateTime.parse(fechaAcontecimientoStr);

      boolean esRegistrado = ctx.sessionAttribute("usuarioRegistrado") != null;

      // --- Crear y guardar la solicitud ---
      SolicitudDeCarga solicitud = new SolicitudDeCarga(
          titulo, descripcion, categoria, latitud, longitud,
          fechaAcontecimiento, multimedia, esRegistrado, fuenteAsociada
      );

      withTransaction(() -> repoSolicitudes.registrar(solicitud));

      ctx.sessionAttribute("flash_message", "Hecho cargado correctamente. Pendiente de revisión.");
      ctx.redirect("/hechos/confirmacion/" + solicitud.getId());

    } catch (Exception e) {
      ctx.status(400);
      ctx.sessionAttribute("flash_error", "Error al procesar la carga: " + e.getMessage());
      ctx.redirect("/hechos/nuevo");
    }
  }

  // --- Confirmación ---
  public void showConfirmation(Context ctx) {
    Long solicitudId = ctx.pathParamAsClass("solicitudId", Long.class)
        .check(id -> id > 0, "ID de solicitud debe ser positivo")
        .get();

    SolicitudDeCarga solicitud = repoSolicitudes.getSolicitud(solicitudId);

    if (solicitud == null) {
      ctx.status(404);
      ctx.result("Solicitud no encontrada con ID " + solicitudId);
      return;
    }

    Map<String, Object> model = new HashMap<>();
    model.put("flash_message", ctx.sessionAttribute("flash_message"));
    model.put("solicitud", solicitud);
    model.put("registrado", solicitud.esRegistrado());

    ctx.render("confirmacion-solicitudCarga.hbs", model);
    ctx.sessionAttribute("flash_message", null);
  }
}
