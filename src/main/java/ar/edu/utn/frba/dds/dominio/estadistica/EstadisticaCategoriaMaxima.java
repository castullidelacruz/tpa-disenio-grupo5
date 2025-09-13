package ar.edu.utn.frba.dds.dominio.estadistica;

import ar.edu.utn.frba.dds.dominio.Hecho;
import com.opencsv.CSVWriter;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class EstadisticaCategoriaMaxima implements Estadistica, WithSimplePersistenceUnit {
  private String categoriaMax;


  @Override public void calcularEstadistica() {
    List<Hecho> hechos = entityManager()
        .createQuery("from Hecho", Hecho.class).getResultList();

    this.categoriaMax = hechos.stream()
        .map(Hecho::getCategoria)
        .collect(Collectors.toMap(
            c -> c,
            c -> 1L,
            Long::sum
        ))
        .entrySet().stream()
        .max(Map.Entry.comparingByValue())
        .map(Map.Entry::getKey)
        .orElse(null);
  }

  @Override
  public void exportarEstadistica(String path) throws IOException {
    File file = new File(path);

    if (file.exists()) {
      boolean eliminado = file.delete();
    }

    try (CSVWriter writer = new CSVWriter(
        new OutputStreamWriter(new FileOutputStream(file, true), StandardCharsets.UTF_8))) {
      String[] header = {"Fecha", "CategoriaMasFrecuente"};
      String[] data = {LocalDateTime.now().toString(), categoriaMax != null ? categoriaMax : "N/A"};

      // Escribir encabezado solo si el archivo está vacío
      if (file.length() == 0) {
        writer.writeNext(header);
      }
      writer.writeNext(data);
    }
  }

  public String getCategoriaMax() {
    return categoriaMax;
  }

}


