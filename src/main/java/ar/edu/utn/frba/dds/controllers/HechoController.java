package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.model.entities.fuentes.Fuente;
import ar.edu.utn.frba.dds.model.entities.fuentes.FuenteDinamica;
import ar.edu.utn.frba.dds.model.entities.solicitudes.SolicitudDeCarga;
import ar.edu.utn.frba.dds.repositories.RepositorioFuentes;
import ar.edu.utn.frba.dds.repositories.RepositorioSolicitudesDeCarga;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import io.javalin.http.Context;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HechoController implements WithSimplePersistenceUnit {
  private RepositorioSolicitudesDeCarga repoSolicitudes;
  private RepositorioFuentes repoFuentes;


  public HechoController() {
    this.repoSolicitudes = RepositorioSolicitudesDeCarga.getInstance();
    this.repoFuentes = RepositorioFuentes.getInstance();
  }

  public Map<String, Object> showCreationForm(Context ctx) {
    // 1. Obtener el ID de la Fuente del Query Parameter
    Long fuenteId = null;
    try {
      String idStr = ctx.queryParam("fuenteId");
      if (idStr != null) {
        fuenteId = Long.parseLong(idStr);
      }
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("ID de fuente no válido.");
    }

    // Si no se proporciona un ID, o si la fuente no existe, maneja el error
    if (fuenteId == null || repoFuentes.getFuente(fuenteId) == null) {
      ctx.status(404);
      // Si quieres un error menos rudo, podrías redirigir con un mensaje flash
      throw new RuntimeException("Debe especificar una Fuente Dinámica válida.");
    }

    // 2. Construir el Modelo
    List<String> categorias = List.of("Incendio", "Accidente Vial", "Contaminación", "Otro");

    return Map.of( // <-- Retornamos el Map directamente
        "titulo", "Crear Nuevo Hecho en Fuente: " + fuenteId,
        "categorias", categorias,
        "fuenteId", fuenteId
    );
  }

  public void create(Context ctx) {
    try {
      // 1. Obtener parámetros del formulario (Ejemplo, necesitas todos los campos del Hecho)
      String titulo = ctx.formParam("titulo");
      String descripcion = ctx.formParam("descripcion");
      String categoria = ctx.formParam("categoria");
      Double latitud = Double.parseDouble(ctx.formParam("latitud"));
      Double longitud = Double.parseDouble(ctx.formParam("longitud"));
      String fechaAcontecimientoStr = ctx.formParam("fechaAcontecimiento");
      String multimedia = ctx.formParam("multimedia");
      // Nota: Aquí se implementaría la lógica de sesión para saber si es 'registrado'
      boolean esRegistrado = Boolean.parseBoolean(ctx.formParam("esRegistrado"));
      Long fuenteId = Long.parseLong(ctx.formParam("fuenteId"));

      // Convertir la fecha (debería ser un formato estándar ISO)
      LocalDateTime fechaAcontecimiento = LocalDateTime.parse(fechaAcontecimientoStr);

      Fuente fuenteAsociada = repoFuentes.getFuente(fuenteId);
      if (fuenteAsociada == null) {
        throw new IllegalArgumentException("Fuente dinámica no encontrada.");
      }

      // 3. Crear la SolicitudDeCarga
      SolicitudDeCarga solicitud = new SolicitudDeCarga(
          titulo, descripcion, categoria, latitud, longitud,
          fechaAcontecimiento, multimedia, esRegistrado, fuenteAsociada
      );

      // 4. Persistir la Solicitud (queda en estado PENDIENTE)
      withTransaction(() -> {
        repoSolicitudes.registrar(solicitud);
      });

      // 5. Redirigir al usuario
      ctx.sessionAttribute("flash_message", "Hecho cargado. Pendiente de revisión.");
      ctx.redirect("hechos/confirmacion/" + solicitud.getId());

    } catch (Exception e) {
      // Manejo básico de errores (ej: formato de número o fecha incorrecto)
      ctx.status(400); // Bad Request
      ctx.sessionAttribute("flash_error", "Error al procesar la carga: " + e.getMessage());
      ctx.redirect("/hechos/nuevo");
    }
  }


  public void showConfirmation(Context ctx) {
    Long solicitudId = ctx.pathParamAsClass("solicitudId", Long.class).get();

    // 1. Buscar la solicitud recién creada

    SolicitudDeCarga solicitud = repoSolicitudes.getSolicitud(solicitudId);

    System.out.println(solicitud);

    if (solicitud == null) {
      ctx.status(404);
      ctx.result("Solicitud no encontrada.");
      return;
    }

    // 2. Construir el modelo para la confirmación
    Map<String, Object> model = new HashMap<>();

    // 2. Construir el modelo para la confirmación
    model.put("flash_message", ctx.sessionAttribute("flash_message"));
    model.put("solicitud", solicitud);
    model.put("registrado", solicitud.esRegistrado());

    // 3. Renderizar la confirmación
    ctx.render("confirmacion-solicitudCarga.hbs", model);
    ctx.sessionAttribute("flash_message", null);
  }
}
