package ar.edu.utn.frba.dds.dominio;

public class SolicitudDeEliminacion {
  private Hecho unHecho;
  private String motivo;

  public SolicitudDeEliminacion(Hecho unHecho, String motivo) {
    this.unHecho = unHecho;
    this.motivo = motivo;
  }


}
