package ar.edu.utn.frba.dds.dominio;

import java.time.LocalDate;

public abstract class Contribuyente extends Usuario {

  public abstract void subirHecho(TipoDeHecho tipoDeHecho, String titulo,
                                  String descripcion, LocalDate fechaAcontecimiento,
                                  Double latitud, Double longitud, LocalDate fechaDeCarga,
                                  Fuente origen, Categoria categoria);


}
