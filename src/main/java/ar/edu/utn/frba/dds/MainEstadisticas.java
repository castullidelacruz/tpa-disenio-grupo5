package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.model.estadistica.ComponenteEstadistico;

public class MainEstadisticas {
  public static void main(String[] args) {
    ComponenteEstadistico componenteEstadistico = ComponenteEstadistico.getInstance();
    componenteEstadistico.calcularEstadisticas();
  }
}
