package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.dominio.Administrador;
import ar.edu.utn.frba.dds.dominio.Coleccion;
import ar.edu.utn.frba.dds.dominio.ColeccionBuilder;
import ar.edu.utn.frba.dds.dominio.Etiqueta;
import ar.edu.utn.frba.dds.dominio.FiltroTitulo;
import ar.edu.utn.frba.dds.dominio.Hecho;
import ar.edu.utn.frba.dds.dominio.RepositorioDeColecciones;
import ar.edu.utn.frba.dds.dominio.Visualizador;
import java.util.List;

public class Main {
  public static void main(String[] args) {

    //ADMINISTRADOR CREA UNA NUEVA COLECCION
    /*
    DECISIONES TOMADAS:

    */

    Administrador administrador1 = new Administrador();

    administrador1.cargarColeccionDesdeDataSet("datos.csv",
        new ColeccionBuilder("Inciendios forestales 2025",
            "estado de incendios anual",
            new Etiqueta("INCENDIO_forestal")));


    Coleccion cole = null;
    //PRUEBA DE QUE SER CARGO EXITOSAMENTE
    for (Coleccion c : RepositorioDeColecciones.getColeccionesDisponibles()) {
      System.out.println(c.getListaHechos());
      if (c.getTitulo().equals("Inciendios forestales 2025")) {
        cole = c;
      }
    }


    Visualizador visualizador = new Visualizador();

    List<Hecho> resultado = visualizador.visualizarHechos(
        new FiltroTitulo("Incendio en Bariloche"), cole);

    resultado.forEach(System.out::println);

  }


}
