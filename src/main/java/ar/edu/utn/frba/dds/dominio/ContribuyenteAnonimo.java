package ar.edu.utn.frba.dds.dominio;

import java.time.LocalDate;

public class ContribuyenteAnonimo extends Contribuyente {


  @Override
  public void subirHecho(String titulo, String descripcion,
                         LocalDate fechaAcontecimiento, Double latitud,
                         Double longitud, Categoria categoria, TipoDeHecho tipo,
                         Contribuyente contribuyente) {

    Hecho nuevoHecho = new Hecho(titulo, descripcion, categoria, latitud, longitud,
        fechaAcontecimiento, LocalDate.now(), Fuente.CONTRIBUYENTE, null,
        Boolean.TRUE, tipo);


  }
}
