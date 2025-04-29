package ar.edu.utn.frba.dds.dominio;

import java.util.ArrayList;
import java.util.List;

public class Administrador {

  private List<SolicitudDeEliminacion> solicitudesPrndientes;

  public Coleccion crearColeccion(String titulo, String descripcion, Fuente fuenteTipo,
                                  Categoria criterioPertenencia, List<Hecho> listaHechos,
                                  String fuente){

    Coleccion nuevaColeccion = new Coleccion(titulo, descripcion, fuenteTipo,
        criterioPertenencia, listaHechos, fuente);
    //deberia validar que no exista ya esa coleccion
    //ver con que criterio la identificamos univocamente
    RegistroDeColecciones.agregarColeccion(nuevaColeccion);

    return nuevaColeccion;
  }

  public Coleccion traerColeccionDesdeDataSet(Coleccion unaColeccion) {

    CargaDataset data = new CargaDataset();
    List<Hecho> todosLosHechos = new ArrayList<>();
    List<Hecho> filtrados = new ArrayList<>();

    try {
      todosLosHechos = data.cargarHechosDesdeCsv(unaColeccion.getFuente());
    } catch (Exception e) {
      throw new RuntimeException(e);
    }

    for (Hecho h : todosLosHechos) {
      if (h.getCategoria() == unaColeccion.getCriterioPertenencia()) {
        filtrados.add(h);
      }
    }

    unaColeccion.setListaHechos(filtrados);

    return unaColeccion;
  }


}
