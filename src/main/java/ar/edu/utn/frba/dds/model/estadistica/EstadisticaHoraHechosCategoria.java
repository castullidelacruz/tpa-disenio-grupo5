package ar.edu.utn.frba.dds.model.estadistica;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

import ar.edu.utn.frba.dds.model.entities.Hecho;
import com.opencsv.CSVWriter;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class EstadisticaHoraHechosCategoria implements Estadistica {

  /** Mapa de conteo: categoría -> (hora -> cantidad de hechos) */
  private final Map<String, Map<Integer, Long>> conteoPorCategoriaYHora = new ConcurrentHashMap<>();

  /** Resultado precomputado: categoría -> DTO con hora pico actual */
  private final Map<String, CategoriaHoraPicoDto> horaPicoPorCategoria = new ConcurrentHashMap<>();

  /** DTO equivalente al record anterior */
  public record CategoriaHoraPicoDto(String categoria, String horapico, BigInteger cantidad) {}

  // ============================
  // Métodos públicos del contrato
  // ============================

  @Override
  public synchronized void calcularEstadistica() {
    System.out.println("📊 Estadística de hora pico (en memoria):");
    horaPicoPorCategoria.values().forEach(dto ->
        System.out.printf("Categoría: %-20s | Hora pico: %s | Cantidad: %d%n",
            dto.categoria(), dto.horapico(), dto.cantidad())
    );
  }

  @Override
  public synchronized void exportarEstadistica(String path) throws IOException {
    File file = new File(path);
    if (file.exists()) file.delete();

    try (CSVWriter writer = new CSVWriter(
        new OutputStreamWriter(new FileOutputStream(file, true), StandardCharsets.UTF_8))) {

      writer.writeNext(new String[]{"FechaExportacion", "Categoria", "HoraPico", "Cantidad"});

      for (CategoriaHoraPicoDto dto : horaPicoPorCategoria.values()) {
        writer.writeNext(new String[]{
            LocalDateTime.now().toString(),
            dto.categoria(),
            dto.horapico(),
            dto.cantidad().toString()
        });
      }
    }
  }

  // ============================
  // Métodos adicionales para integración
  // ============================

  /** Llamar a este método cuando se crea un nuevo Hecho */
  public synchronized void registrarHecho(Hecho hecho) {
    if (hecho == null || hecho.getCategoria() == null || hecho.getFechaAcontecimiento() == null) {
      return;
    }

    String categoria = hecho.getCategoria();
    int hora = hecho.getFechaAcontecimiento().getHour();

    conteoPorCategoriaYHora
        .computeIfAbsent(categoria, c -> new ConcurrentHashMap<>())
        .merge(hora, 1L, Long::sum);

    recalcularHoraPico(categoria);
  }

  /** Llamar cuando se elimina un Hecho */
  public synchronized void eliminarHecho(Hecho hecho) {
    if (hecho == null || hecho.getCategoria() == null || hecho.getFechaAcontecimiento() == null) {
      return;
    }

    String categoria = hecho.getCategoria();
    int hora = hecho.getFechaAcontecimiento().getHour();

    Map<Integer, Long> conteoHoras = conteoPorCategoriaYHora.get(categoria);
    if (conteoHoras == null) return;

    conteoHoras.computeIfPresent(hora, (h, count) -> (count > 1) ? count - 1 : null);
    if (conteoHoras.isEmpty()) {
      conteoPorCategoriaYHora.remove(categoria);
      horaPicoPorCategoria.remove(categoria);
    } else {
      recalcularHoraPico(categoria);
    }
  }

  /** Retorna una copia inmutable del reporte actual */
  public synchronized List<CategoriaHoraPicoDto> getReporte() {
    return new ArrayList<>(horaPicoPorCategoria.values());
  }

  // ============================
  // Métodos internos
  // ============================

  private void recalcularHoraPico(String categoria) {
    Map<Integer, Long> conteoHoras = conteoPorCategoriaYHora.get(categoria);
    if (conteoHoras == null || conteoHoras.isEmpty()) return;

    Map.Entry<Integer, Long> max = conteoHoras.entrySet().stream()
        .max(Map.Entry.comparingByValue())
        .orElseThrow();

    horaPicoPorCategoria.put(categoria,
        new CategoriaHoraPicoDto(
            categoria,
            String.format("%02d:00", max.getKey()),
            BigInteger.valueOf(max.getValue()))
    );
  }
}