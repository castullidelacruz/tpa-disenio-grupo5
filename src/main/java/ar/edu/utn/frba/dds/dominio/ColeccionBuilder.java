package ar.edu.utn.frba.dds.dominio;

import java.util.ArrayList;
import java.util.List;

public class ColeccionBuilder {
  private String titulo;
  private String descripcion;
  private Fuente fuenteTipo;
  private Etiqueta criterioPertenencia;
  private List<Hecho> listaHechos = new ArrayList<>();
  private String fuente;

  public ColeccionBuilder(String titulo, String descripcion, Etiqueta criterioPertenencia) {
    this.titulo = titulo;
    this.descripcion = descripcion;
    this.criterioPertenencia = criterioPertenencia;
  }

  public void setFuenteTipo(Fuente fuenteTipo) {
    this.fuenteTipo = fuenteTipo;
  }

  public Etiqueta getCriterioPertenencia() {
    return criterioPertenencia;
  }

  public void setListaHechos(List<Hecho> listaHechos) {
    this.listaHechos = new ArrayList<>(listaHechos);
  }

  public void setFuente(String fuente) {
    this.fuente = fuente;
  }

  public void agregarHecho(Hecho hecho) {
    this.listaHechos.add(hecho);
  }

  public Coleccion crearColeccion() {
    return new Coleccion(titulo, descripcion, fuenteTipo,
        criterioPertenencia, listaHechos, fuente);
  }
}
