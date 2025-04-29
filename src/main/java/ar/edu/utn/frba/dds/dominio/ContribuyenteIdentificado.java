package ar.edu.utn.frba.dds.dominio;

import java.time.LocalDate;

public class ContribuyenteIdentificado extends Contribuyente {
  private String nommbre;
  private String apellido;
  private Integer edad;


  @Override
  public void subirHecho(String titulo, String descripcion,
                         LocalDate fechaAcontecimiento, Double latitud,
                         Double longitud, Categoria categoria, TipoDeHecho tipo,
                         Contribuyente contribuyente) {
    Hecho nuevoHecho = new Hecho(titulo, descripcion, categoria, latitud, longitud,
        fechaAcontecimiento, LocalDate.now(), Fuente.CONTRIBUYENTE, contribuyente,
        Boolean.TRUE, tipo);


  }
}
