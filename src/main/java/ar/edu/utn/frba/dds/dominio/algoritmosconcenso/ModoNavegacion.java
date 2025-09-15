package ar.edu.utn.frba.dds.dominio.algoritmosconcenso;

import ar.edu.utn.frba.dds.dominio.Hecho;

import java.util.List;

public interface ModoNavegacion {
  List<Hecho> aplicar(List<Hecho> hechos, AlgoritmoDeConsenso algoritmo);
}
