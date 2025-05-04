package ar.edu.utn.frba.dds.dominio;

import static java.util.Objects.requireNonNull;

public class Etiqueta {
  String criterioPertenencia;

  public Etiqueta(String criterioPertenencia) {
    this.criterioPertenencia = formatear(requireNonNull(criterioPertenencia));
  }

  public String getCriterioPertenencia() {
    return criterioPertenencia;
  }

  private String formatear(String input) {
    return input.trim()
        .replaceAll("([a-z])([A-Z])", "$1_$2")  // camelCase a snake_case
        .replaceAll("[\\s]+", "_")             // espacios a guiones bajos
        .replaceAll("[^a-zA-Z0-9_]", "")       // quitar caracteres especiales
        .toUpperCase();
  }

  @Override
  public String toString() {
    return criterioPertenencia;
  }

}
