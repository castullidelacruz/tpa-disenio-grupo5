package ar.edu.utn.frba.dds.dominio;

import java.util.List;

public class Visualizador {

  // hechosExtraidos = new manejoDeHechos();


  public Visualizador() {
  }

  public List<Hecho> visualizarHechos(Filtro filtro, Coleccion coleccion) {
    return coleccion.visualizarHechos(filtro);
  }

  public List<Hecho> visualizarMultiplesfiltros(List<Filtro> filtros, Coleccion coleccion) {
    return coleccion.visualizarMultiplesfiltros(filtros);
  }

}


//Interfaz con los metodos para navegar hechos y solicitar la eliminacion de un hecho
// (esta serviria para visualizador)
//Segunda interfaz que haga extends de la anterior y agregue el metodo subirHecho
// (esta serviria para contribuyente y administrador)