package ar.edu.utn.frba.dds.dominio;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;

public class Coleccion {
  private String titulo;
  private String descripcion;
  private Fuente fuenteTipo;
  private Categoria criterioPertenencia;
  private List<Hecho> listaHechos;
  private String fuente;

  public Coleccion(String titulo, String descripcion, Fuente fuenteTipo,
                   Categoria criterioPertenencia, List<Hecho> listaHechos, String fuente) {
    this.titulo = requireNonNull(titulo);
    this.descripcion = requireNonNull(descripcion);
    this.fuenteTipo = requireNonNull(fuenteTipo);
    this.criterioPertenencia = requireNonNull(criterioPertenencia);
    this.listaHechos = listaHechos;
    this.fuente = fuente;
  }

  public Fuente getFuenteTipo() {
    return fuenteTipo;
  }

  public String getFuente() {
    return fuente;
  }

  public List<Hecho> getListaHechos() {
    return listaHechos;
  }

  public void setListaHechos(List<Hecho> listaHechos) {
    this.listaHechos = listaHechos;
  }

  public Categoria getCriterioPertenencia() {
    return criterioPertenencia;
  }

  public static String agregarHechoAColeccion(Hecho unHecho) {
    //deberia buscar en las colas colecciones disponibles a cual correspodne el hecho
    //y agregarlo.
    //por ahora puse una coleccion de colecciones donde cada una tiene su
    //propio criterio pero seguramente esas colecciones deberan almacenarse en algun lado

    List<Coleccion> colecciones = RegistroDeColecciones.getColeccionesDisponibles();
    for (Coleccion c : colecciones) {
      if (c.criterioPertenencia == unHecho.getCategoria()) {
        c.listaHechos.add(unHecho);
      }
    }
    return "Hecho agregado a coleccion correspondiente";
  }


}
