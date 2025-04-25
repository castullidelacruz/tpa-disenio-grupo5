package ar.edu.utn.frba.dds.dominio;

import java.util.List;

public class Coleccion {
  private List<Hecho> hechos;
  private String titulo;
  private String descripcion;
  private Fuente fuente;
  private Categoria criterioPertenencia;

  public String agregarHechos(){
    //deberia relevar todos los hecgos de la fuente y agregarlos a la
    //lista sin cumplen con el criterio de pertenencia

    //podriamos diseñar clases polimorficas que agreguen los hechos
    //de distinta manera segun la fuente de la que provienen
    //temabien podria ser herencia, ha yq ever que conviene
    return "Hechos agregados exitosamente a la coleccion";
  }


}
