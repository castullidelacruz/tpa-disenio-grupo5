package ar.edu.utn.frba.dds.dominio;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Administrador {

  public Administrador() {
  }

  private List<SolicitudDeEliminacion> solicitudesPrndientes;

  public Coleccion crearColeccionDesdeDataSet(String ruta) {
    DatasetCsv data = new DatasetCsv();
    Coleccion nuevaColeccion = new Coleccion(null,
        "incendios forestales", "hechos ocurridos en argentina",
        Fuente.DATASET, Categoria.INCENDIO_FORESTAL);

    List<Hecho> todosLosHechos = new ArrayList<>();
    List<Hecho> filtrados = new ArrayList<>();
    try {
      todosLosHechos = data.cargarHechosDesdeCsv(ruta);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    for (Hecho h : todosLosHechos) {
      if (h.getCategoria() == nuevaColeccion.getCriterioPertenencia()) {
        filtrados.add(h);
      }
    }

    nuevaColeccion.setHechos(filtrados);
    return nuevaColeccion;
  }

  public void revisarSolicitud() {

  }

  public void elimiarHecho() {

  }

  public void rechazarSolicitudDeEliminacion() {

  }
}
