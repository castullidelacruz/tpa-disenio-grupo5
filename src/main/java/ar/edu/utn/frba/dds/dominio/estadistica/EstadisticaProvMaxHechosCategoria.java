package ar.edu.utn.frba.dds.dominio.estadistica;
import ar.edu.utn.frba.dds.dominio.Hecho;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import ar.edu.utn.frba.dds.dominio.criterios.Criterio;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;

import static ar.edu.utn.frba.dds.dominio.estadistica.LocalizadorDeProvincias.getProvincia;

public class EstadisticaProvMaxHechosCategoria implements Estadistica , WithSimplePersistenceUnit {
  private String provincia;
  public String categoria ;

  public EstadisticaProvMaxHechosCategoria(String categoria) {
    this.categoria = categoria;
  }

  @Override
  public void calcularEstadistica(){
    List<Hecho> hechos = entityManager()
        .createQuery("from Hecho h where h.categoria  = :categoria", Hecho.class )
        .setParameter("categoria" , this.categoria)
        .getResultList();

    this.provincia = hechos.stream()
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

  }
  @Override public void exportarEstadistica() {}

  public String getProvinciaMax() {
    return provincia;
  }
}
