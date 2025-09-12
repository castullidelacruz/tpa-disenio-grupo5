package ar.edu.utn.frba.dds.dominio.estadistica;

import java.util.ArrayList;
import java.util.List;

public class componenteEstadistico {

  private List<Estadistica> estadisticas = new ArrayList<>();

  public componenteEstadistico(List<Estadistica> estadisticas) {
    this.estadisticas = estadisticas;
  }

  public void calcularEstadisticas() {
    estadisticas.forEach(Estadistica::calcularEstadistica);
  }

  public List<Estadistica> getEstadisticas() {
    return estadisticas;
  }

  public void setEstadisticas(List<Estadistica> estadisticas) {
    this.estadisticas = estadisticas;
  }

}
