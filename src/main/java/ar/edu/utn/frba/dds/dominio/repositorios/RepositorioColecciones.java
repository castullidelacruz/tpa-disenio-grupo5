package ar.edu.utn.frba.dds.dominio.repositorios;

import ar.edu.utn.frba.dds.dominio.Coleccion;

import java.util.ArrayList;
import java.util.List;

public class RepositorioColecciones {
  private final List<Coleccion> colecciones = new ArrayList<>();
  static RepositorioColecciones INSTANCE = new RepositorioColecciones();
  public static RepositorioColecciones getInstance() {
    return INSTANCE;
  }
  public void registrarFuente(Coleccion coleccion) {
    colecciones.add(coleccion);
  }
  public void eliminarFuente(Coleccion coleccion) {
    colecciones.remove(coleccion);
  }
  public List<Coleccion> getColecciones() {
    return new ArrayList<>(colecciones);
  }
  public void consesuareEchos() {
    colecciones.forEach(Coleccion::actualizarHechosConsensuados);
  }
  private RepositorioColecciones() {
  }
}
