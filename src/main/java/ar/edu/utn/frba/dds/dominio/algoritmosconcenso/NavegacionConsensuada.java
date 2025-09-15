package ar.edu.utn.frba.dds.dominio.algoritmosconcenso;

import ar.edu.utn.frba.dds.dominio.Hecho;

import java.util.List;

public class NavegacionConsensuada implements ModoNavegacion {
  @Override
  public List<Hecho> aplicar(List<Hecho> hechos, AlgoritmoDeConsenso algoritmo) {
    if (algoritmo == null) {
      return hechos;
    }
    return hechos.stream()
        .filter(h -> algoritmo.estaConsensuado(h, hechos)).toList();
  }
}
