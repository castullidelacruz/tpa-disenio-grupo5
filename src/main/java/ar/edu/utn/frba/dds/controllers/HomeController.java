package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.model.entities.Hecho;
import ar.edu.utn.frba.dds.repositories.RepositorioHechos;
import ar.edu.utn.frba.dds.server.AppRole;
import io.javalin.http.Context;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class HomeController {

  private final RepositorioHechos repoHechos;

  public HomeController() {
    this.repoHechos = RepositorioHechos.getInstance();
  }

  public Map<String, Object> index(@NotNull Context ctx) {
    AppRole rol = ctx.attribute("userRole");
    boolean esRegistrado = rol == AppRole.USER || rol == AppRole.ADMIN;
    String username = ctx.attribute("username");

    List<Hecho> hechosDisponibles = repoHechos.obtenerTodos()
        .stream()
        .filter(Hecho::getDisponibilidad)
        .toList();

    return Map.of(
        "titulo", "MetaMapa: Gestión de Mapeos Colaborativos",
        "mensaje", esRegistrado ?
            "Bienvenido, " + username + ". Podés registrar y gestionar tus hechos." :
            "Estás navegando como visitante. Podés ver hechos y cargar nuevos de forma anónima.",
        "hechos", hechosDisponibles,
        "esRegistrado", esRegistrado,
        "username", username
    );
  }

}
