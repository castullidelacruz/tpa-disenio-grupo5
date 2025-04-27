package ar.edu.utn.frba.dds.dominio;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;

public class Hecho {
  private String titulo;
  private String descripcion;
  private Categoria categoria;
  private Double latitud;
  private Double longitud;
  private LocalDate fechaAcontecimiento;
  private LocalDate fechaDeCarga;
  private Fuente origen;
  private Contribuyente contribuyente;

  //deberiamos pensar como nos conviene construir los hechos
  //lo hice asi de forma providional porque no tenemos especificaciones
  public Hecho(String titulo, String descripcion, Categoria categoria,
               Double latitud, Double longitud,
               LocalDate fechaAcontecimiento, LocalDate fechaDeCarga,
               Fuente origen, Contribuyente contribuyente) {
    //this.tipoDeHecho = requireNonNull(tipoDeHecho);
    this.titulo = requireNonNull(titulo);
    this.descripcion = requireNonNull(descripcion);
    this.fechaAcontecimiento = requireNonNull(fechaAcontecimiento);
    this.latitud = requireNonNull(latitud);
    this.longitud = requireNonNull(longitud);
    this.fechaDeCarga = requireNonNull(fechaDeCarga);
    this.origen = requireNonNull(origen);
    this.categoria = requireNonNull(categoria);
    this.contribuyente = contribuyente;
  }


  public Fuente getOrigen() {
    return origen;
  }

  public Categoria getCategoria() {
    return categoria;
  }

  @Override
  public String toString() {
    return "Hecho{"
        + "titulo='" + titulo + '\''
        + ", descripcion='" + descripcion + '\''
        + ", categoria=" + categoria
        + ", latitud=" + latitud
        + ", longitud=" + longitud
        + ", fechaAcontecimiento=" + fechaAcontecimiento
        + ", fechaDeCarga=" + fechaDeCarga
        + ", origen=" + origen
        + ", contribuyente=" + contribuyente
        + '}';
  }
}
