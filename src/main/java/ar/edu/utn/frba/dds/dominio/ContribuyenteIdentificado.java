package ar.edu.utn.frba.dds.dominio;

import java.time.LocalDate;

public class ContribuyenteIdentificado extends Contribuyente {
  private String nommbre;
  private String apellido;
  private Integer edad;

  @Override
  public void subirHecho(TipoDeHecho tipoDeHecho, String titulo,
                         String descripcion, LocalDate fechaAcontecimiento,
                         Double latitud, Double longitud, LocalDate fechaDeCarga,
                         Fuente origen, Categoria categoria) {


  }
}
