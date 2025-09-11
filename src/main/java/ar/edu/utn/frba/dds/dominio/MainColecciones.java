package ar.edu.utn.frba.dds.dominio;

import ar.edu.utn.frba.dds.dominio.repositorios.RepositorioColecciones;

public class MainColecciones {
    public static void main(String[] args) {
        RepositorioColecciones repositorioColecciones = RepositorioColecciones.getInstance();
        repositorioColecciones.consesuareEchos();
    }
}
