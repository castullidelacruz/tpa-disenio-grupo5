package ar.edu.utn.frba.dds.dominio;

import java.time.LocalDate;

public class ContribuyenteAnonimo extends Contribuyente {

  @Override
  public void subirHecho(TipoDeHecho tipoDeHecho, String titulo,
                         String descripcion, LocalDate fechaAcontecimiento,
                         Double latitud, Double longitud, LocalDate fechaDeCarga,
                         Fuente origen, Categoria categoria) {

  }
}
