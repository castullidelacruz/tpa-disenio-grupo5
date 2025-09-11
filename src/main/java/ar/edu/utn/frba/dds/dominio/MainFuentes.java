package ar.edu.utn.frba.dds.dominio;

import ar.edu.utn.frba.dds.dominio.repositorios.RepositorioFuentes;

public class MainFuentes {
  public static void main(String[] args) {
    RepositorioFuentes repositorioFuentes = new RepositorioFuentes();
    repositorioFuentes.actualizarHechos();
  }
}


