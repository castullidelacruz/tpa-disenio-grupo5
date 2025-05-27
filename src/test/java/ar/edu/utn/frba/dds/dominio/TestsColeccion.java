package ar.edu.utn.frba.dds.dominio;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestsColeccion {
  Fuente dataset;
  List<Criterio> criterios;
  Criterio rango;
  Criterio titulo;
  @BeforeEach
  public void fixtureColeccion() {
    rango = new CriterioRangoFechas(LocalDate.of(2023, 1, 1),
        LocalDate.of(2025,12,30));
    titulo = new CriterioTitulo("incendio");
    dataset = new FuenteDataSet("datos.csv");
    criterios = new ArrayList<>(Arrays.asList(titulo,rango));
  }

  @Test
  public void importarDesdeDataset() {
    Coleccion coleccion = new Coleccion("incendios forestales",
        "incendios en la patagonia", dataset, criterios);

    List<Hecho> hechos = coleccion.obtenerTodosLosHechos();

    Assertions.assertEquals(2, hechos.size());
  }

}
