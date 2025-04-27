package ar.edu.utn.frba.dds.dominio;

import java.util.List;

public class Coleccion {
  private List<Hecho> hechos;
  private String titulo;
  private String descripcion;
  private Fuente fuente;
  private Categoria criterioPertenencia;

  public Coleccion(List<Hecho> hechos, String titulo, String descripcion,
                   Fuente fuente, Categoria criterioPertenencia) {
    this.hechos = hechos;
    this.titulo = titulo;
    this.descripcion = descripcion;
    this.fuente = fuente;
    this.criterioPertenencia = criterioPertenencia;
  }

  public Coleccion() {
  }

  public void setHechos(List<Hecho> hechos) {
    this.hechos = hechos;
  }

  public List<Hecho> getHechos() {
    return hechos;
  }

  public Categoria getCriterioPertenencia() {
    return criterioPertenencia;
  }

  public String agregarHechos() {
    //deberia relevar todos los hecgos de la fuente y agregarlos a la
    //lista sin cumplen con el criterio de pertenencia

    //podriamos diseñar clases polimorficas que agreguen los hechos
    //de distinta manera segun la fuente de la que provienen
    //temabien podria ser herencia, ha yq ever que conviene
    return "Hechos agregados exitosamente a la coleccion";
  }


}
