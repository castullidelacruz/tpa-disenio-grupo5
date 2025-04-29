package ar.edu.utn.frba.dds.dominio;

import java.time.LocalDate;

public abstract class Contribuyente{

  public abstract void subirHecho(String titulo,
                                  String descripcion, LocalDate fechaAcontecimiento,
                                  Double latitud, Double longitud, Categoria categoria,
                                  TipoDeHecho tipo, Contribuyente contribuyente);

  public void crearNuevoHecho(Hecho unHecho){
    Coleccion.agregarHechoAColeccion(unHecho);
  }

}
