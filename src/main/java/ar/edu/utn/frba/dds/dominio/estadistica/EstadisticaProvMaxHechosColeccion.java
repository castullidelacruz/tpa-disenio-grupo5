package ar.edu.utn.frba.dds.dominio.estadistica;

import ar.edu.utn.frba.dds.dominio.Coleccion;
import ar.edu.utn.frba.dds.dominio.Hecho;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Map;

import static ar.edu.utn.frba.dds.dominio.estadistica.LocalizadorDeProvincias.getProvincia;

public class EstadisticaProvMaxHechosColeccion implements Estadistica, WithSimplePersistenceUnit {
  private String  provincia;
  private Coleccion coleccion;

  public EstadisticaProvMaxHechosColeccion(Coleccion coleccion){
    this.coleccion = coleccion;
  }
  @Override public void calcularEstadistica() {

    List<Long> idsHechos = coleccion.obtnerHechos().stream().map(Hecho::getId).toList();

    System.out.println( idsHechos + " ids de hechos");

    List<Hecho>  hechosDeLaColeccion  = entityManager()
        .createQuery("from Hecho h where h.id in :idsHechos", Hecho.class )
        .setParameter("idsHechos" , idsHechos)
        .getResultList();


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

  }
  @Override public void exportarEstadistica() {}

  public String getProvinciaMax() {
    return provincia;
  }


}
