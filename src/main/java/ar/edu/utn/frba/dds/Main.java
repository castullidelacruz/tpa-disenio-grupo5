package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.dominio.Administrador;
import ar.edu.utn.frba.dds.dominio.Coleccion;
import ar.edu.utn.frba.dds.dominio.Etiqueta;
import ar.edu.utn.frba.dds.dominio.RegistroDeColecciones;

public class Main {
  public static void main(String[] args) {

    //ADMINISTRADOR CREA UNA NUEVA COLECCION
    /*
    DECISIONES TOMADAS:
      - el administrador establece al momento de crear la coleccion desde donde obtendra sus datos
      - esto puede ser modificado en funcion de los requerimientos
    */

    Administrador administrador1 = new Administrador();

    administrador1.traerColeccionDesdeDataSet(new Etiqueta("INCENDIO_forestal"),
        "Incendios Forestales en Argentina",
        "Compendio de noticias sobre incendios en la Republica Argentina",
        null, "datos.CSV");

    administrador1.traerColeccionDesdeDataSet(new Etiqueta("robo"),
        "Incendios Forestales en Argentina",
        "Compendio de noticias sobre incendios en la Republica Argentina",
        null, "datos.CSV");

    //PRUEBA DE QUE SER CARGO EXITOSAMENTE
    for (Coleccion c : RegistroDeColecciones.getColeccionesDisponibles()) {
      System.out.println(c.getListaHechos());
    }

  }


}
