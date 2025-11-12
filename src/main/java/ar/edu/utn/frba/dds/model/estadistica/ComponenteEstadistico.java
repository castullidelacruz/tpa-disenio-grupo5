package ar.edu.utn.frba.dds.model.estadistica;

import java.util.ArrayList;
import java.util.List;

public class ComponenteEstadistico {
  public static final ComponenteEstadistico INSTANCE = new ComponenteEstadistico();

  public EstadisticaCantidadSpam estadisticaSpam = new EstadisticaCantidadSpam();
  public EstadisticaHoraHechosCategoria estadisticaHhc = new EstadisticaHoraHechosCategoria();
  public EstadisticaCategoriaMaxima estadisticaCm = new EstadisticaCategoriaMaxima();
  public EstadisticaProvMaxHechosCategoria estadisticaPmhCat =
      new EstadisticaProvMaxHechosCategoria();
  public EstadisticaProvMaxHechosColeccion estadisticaPmhCol =
      new EstadisticaProvMaxHechosColeccion();

  public List<Estadistica> estadisticas = new ArrayList<>(List.of(
      estadisticaSpam, estadisticaHhc, estadisticaCm, estadisticaPmhCat, estadisticaPmhCol
  ));

  public static ComponenteEstadistico getInstance() {
    return INSTANCE;
  }

  public void calcularEstadisticas() {
    estadisticas.forEach(Estadistica::calcularEstadistica);
  }

  public List<Estadistica> getEstadisticas() {
    return new ArrayList<>(estadisticas);
  }

  public void setEstadisticas(List<Estadistica> estadisticas) {
    this.estadisticas = estadisticas;
  }

  public EstadisticaCantidadSpam getEstadisticaSpam() {
    return estadisticaSpam;
  }

  public EstadisticaHoraHechosCategoria getEstadisticaHhc() {
    return estadisticaHhc;
  }

  public EstadisticaCategoriaMaxima getEstadisticaCm() {
    return estadisticaCm;
  }

  public EstadisticaProvMaxHechosCategoria getEstadisticaPmhCat() {
    return estadisticaPmhCat;
  }

  public EstadisticaProvMaxHechosColeccion getEstadisticaPmhCol() {
    return estadisticaPmhCol;
  }
}
