package ar.edu.utn.frba.dds.dominio.estadistica;

import static ar.edu.utn.frba.dds.dominio.estadistica.LocalizadorDeProvincias.getProvincia;

import ar.edu.utn.frba.dds.dominio.Coleccion;
import ar.edu.utn.frba.dds.dominio.Hecho;
import com.opencsv.CSVWriter;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EstadisticaProvMaxHechosColeccion implements Estadistica, WithSimplePersistenceUnit {
  private String  provincia;
  private final Coleccion coleccion;

  public EstadisticaProvMaxHechosColeccion(Coleccion coleccion) {
    this.coleccion = coleccion;
  }

  @Override public void calcularEstadistica() {

    //List<Long> idsHechos = coleccion.obtnerHechos().stream().map(Hecho::getId).toList();

    List<Hecho> hechosDeLaColeccion = this.coleccion.obtnerHechos();

    Map<String, Long> conteoPorProvincia = new HashMap<>();

    for (Hecho hecho : hechosDeLaColeccion) {
      String provincia = getProvincia(hecho.getLatitud(), hecho.getLongitud());
      conteoPorProvincia.merge(provincia, 1L, Long::sum);
    }

    this.provincia = conteoPorProvincia.entrySet()
        .stream()
        .max(Map.Entry.comparingByValue())
        .map(Map.Entry::getKey)
        .orElse(null);

    /*
    this.provincia = hechosDeLaColeccion.stream()
        .map(h -> getProvincia(h.getLatitud(), h.getLongitud())) // llamar a la API para cada hecho
        .collect(Collectors.toMap(
            p -> p,
            p -> 1L,
            Long::sum
        ))
        .entrySet().stream()
        .max(Map.Entry.comparingByValue()) // buscar la provincia más frecuente
        .map(Map.Entry::getKey)
        .orElse(null); // si no hay hechos, devuelve null

     */

  }

  @Override public void exportarEstadistica(String path) throws IOException {
    File file = new File(path);
    if (file.exists()) {
      boolean eliminado = file.delete();
    }
    try (CSVWriter writer = new CSVWriter(
        new OutputStreamWriter(new FileOutputStream(file, true), StandardCharsets.UTF_8))) {
      String[] header = {"Fecha", "Coleccion", "ProvinciaMaxima"};
      String[] data = {
          LocalDateTime.now().toString(),
          coleccion.getTitulo(),
          provincia != null ? provincia : "N/A"
      };

      if (file.length() == 0) {
        writer.writeNext(header);
      }
      writer.writeNext(data);
    }
  }

  public String getProvinciaMax() {
    return provincia;
  }


}
