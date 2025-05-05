package ar.edu.utn.frba.dds.dominio;

import java.util.List;

public class Administrador extends Visualizador {

  //private List<SolicitudDeEliminacion> solicitudesPrndientes;

  public Coleccion crearNuevaColeccion(ColeccionBuilder borrador) {
    return borrador.crearColeccion();
  }


  public void cargarColeccionDesdeDataSet(String fuente, ColeccionBuilder borrador) {

    CargaDataset data = new CargaDataset();
    List<Hecho> todosLosHechos;
    //List<Hecho> filtrados = new ArrayList<>();

    try {
      todosLosHechos = data.cargarHechosDesdeCsv(fuente);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }

    List<Hecho> filtrados = todosLosHechos.stream().filter(h ->
        h.getCategoria().getCriterioPertenencia()
            .equals(borrador.getCriterioPertenencia().getCriterioPertenencia())).toList();


    borrador.setFuente(fuente);
    borrador.setFuenteTipo(Fuente.DATASET);
    borrador.setListaHechos(filtrados);

    Coleccion nuevaColeccion = this.crearNuevaColeccion(borrador);

    RegistroDeColecciones.agregarColeccion(nuevaColeccion);

    System.out.println("Se extrajeron corrctamente los dados desde el archivo CSV");

  }


}
