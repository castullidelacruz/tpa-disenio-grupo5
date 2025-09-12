package ar.edu.utn.frba.dds.dominio;

import ar.edu.utn.frba.dds.dominio.estadistica.Estadistica;
import ar.edu.utn.frba.dds.dominio.estadistica.EstadisticaCantidadSpam;
import ar.edu.utn.frba.dds.dominio.estadistica.EstadisticaCategoriaMaxima;
import ar.edu.utn.frba.dds.dominio.estadistica.componenteEstadistico;
import ar.edu.utn.frba.dds.dominio.repositorios.RepositorioFuentes;
import java.util.ArrayList;
import java.util.List;

public class MainEstadisticas {
  public static void main(String[] args) {

    EstadisticaCategoriaMaxima estadisticaCM = new EstadisticaCategoriaMaxima();
    EstadisticaCantidadSpam estadisticaCS = new EstadisticaCantidadSpam();

    List<Estadistica> estadisticas = new ArrayList<>();
    estadisticas.add(estadisticaCM);
    estadisticas.add(estadisticaCS);

    componenteEstadistico componenteEstadistico = new componenteEstadistico(estadisticas);
    componenteEstadistico.calcularEstadisticas();
  }
}
