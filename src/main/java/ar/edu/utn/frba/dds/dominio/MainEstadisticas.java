package ar.edu.utn.frba.dds.dominio;

import ar.edu.utn.frba.dds.dominio.criterios.Criterio;
import ar.edu.utn.frba.dds.dominio.criterios.CriterioBase;
import ar.edu.utn.frba.dds.dominio.estadistica.Estadistica;
import ar.edu.utn.frba.dds.dominio.estadistica.EstadisticaCantidadSpam;
import ar.edu.utn.frba.dds.dominio.estadistica.EstadisticaCategoriaMaxima;
import ar.edu.utn.frba.dds.dominio.estadistica.EstadisticaProvMaxHechosCategoria;
import ar.edu.utn.frba.dds.dominio.estadistica.EstadisticaProvMaxHechosColeccion;
import ar.edu.utn.frba.dds.dominio.estadistica.componenteEstadistico;
import ar.edu.utn.frba.dds.dominio.fuentes.FuenteDinamica;
import ar.edu.utn.frba.dds.dominio.repositorios.RepositorioFuentes;
import ar.edu.utn.frba.dds.dominio.repositorios.RepositorioHechos;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainEstadisticas {
  public static void main(String[] args) {

    //Creacion de funciones para estadisticas
    GeneradorHandleUuid generador = new GeneradorHandleUuid();
    FuenteDinamica dinamica = new FuenteDinamica();
    CriterioBase criterio = new CriterioBase();
    List<Criterio> criterios = new ArrayList<>(Arrays.asList(criterio));
    Coleccion coleccion = new Coleccion("incendios forestales",
        "incendios en la patagonia",
        dinamica, criterios, generador.generar(),null);
    //REVISAR SI LO PREVIO QUEDA

    //Creacion de Estadisticas
    EstadisticaCategoriaMaxima estadisticaCM = new EstadisticaCategoriaMaxima();
    EstadisticaCantidadSpam estadisticaCS = new EstadisticaCantidadSpam();
    EstadisticaProvMaxHechosCategoria estadisticaPMHCat = new EstadisticaProvMaxHechosCategoria("cortes");
    EstadisticaProvMaxHechosColeccion estadisticaPMHCol = new EstadisticaProvMaxHechosColeccion(coleccion);


    //Agrego Estadisticas a la carga
    List<Estadistica> estadisticas = new ArrayList<>();
    estadisticas.add(estadisticaCM);
    estadisticas.add(estadisticaCS);
    estadisticas.add(estadisticaPMHCat);
    estadisticas.add(estadisticaPMHCol);

    //Calculamos estadisticas
    componenteEstadistico componenteEstadistico = new componenteEstadistico(estadisticas);
    componenteEstadistico.calcularEstadisticas();
  }
}
