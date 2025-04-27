package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.dominio.Administrador;
import ar.edu.utn.frba.dds.dominio.Coleccion;
import ar.edu.utn.frba.dds.dominio.Hecho;

public class Main {
  public static void main(String[] args) {
    Administrador admin = new Administrador();
    Coleccion colec = new Coleccion();
    colec = admin.crearColeccionDesdeDataSet("datos.csv");
    for (Hecho h : colec.getHechos()) {
      System.out.println(h);
    }
  }
}
