package ar.edu.utn.frba.dds.dominio;

import java.time.LocalDate;

public class Hecho {
  private TipoDeHecho tipoDeHecho;
  private String titulo;
  private String descripcion;
  private LocalDate fechaAcontecimiento;
  private Double latitud;
  private Double longitud;
  private LocalDate fechaDeCarga;
  private Fuente origen;
  private Categoria categoria;

  //deberiamos pensar como nos conviene construir los hechos
  //lo hice asi de forma providional porque no tenemos especificaciones
  public Hecho(TipoDeHecho tipoDeHecho, String titulo,
               String descripcion, LocalDate fechaAcontecimiento,
               Double latitud, Double longitud, LocalDate fechaDeCarga,
               Fuente origen, Categoria categoria) {
    this.tipoDeHecho = tipoDeHecho;
    this.titulo = titulo;
    this.descripcion = descripcion;
    this.fechaAcontecimiento = fechaAcontecimiento;
    this.latitud = latitud;
    this.longitud = longitud;
    this.fechaDeCarga = fechaDeCarga;
    this.origen = origen;
    this.categoria = categoria;
  }

  public TipoDeHecho getTipoDeHecho() {
    return tipoDeHecho;
  }

  public Fuente getOrigen() {
    return origen;
  }

  public Categoria getCategoria() {
    return categoria;
  }
}
