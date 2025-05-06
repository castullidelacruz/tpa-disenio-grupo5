package ar.edu.utn.frba.dds.dominio;

public class SolicitudDeEliminacion {
  private Hecho unHecho;
  private String motivo;
  private EstadoSolicitud estado = EstadoSolicitud.PENDIENTE;

  public SolicitudDeEliminacion(Hecho unHecho, String motivo) {
    this.unHecho = unHecho;
    this.motivo = motivo;

  }

  public void solicitudAceptada() {
    estado = EstadoSolicitud.ACEPTADA;
  }

  public void solicitudRechazada() {
    estado = EstadoSolicitud.RECHAZADA;
  }

  public boolean esSolicitudPendiente() {
    return estado == EstadoSolicitud.PENDIENTE;
  }

  public EstadoSolicitud getEstado() {
    return estado;
  }

  public Hecho getHecho() {
    return unHecho;
  }

}
