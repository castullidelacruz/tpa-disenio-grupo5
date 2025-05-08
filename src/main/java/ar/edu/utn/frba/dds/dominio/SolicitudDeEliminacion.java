package ar.edu.utn.frba.dds.dominio;

public class SolicitudDeEliminacion {
  private static final int LONGITUD_MAXIMA_MOTIVO = 500;
  private Hecho unHecho;
  private String motivo;
  private EstadoSolicitud estado = EstadoSolicitud.PENDIENTE;

  public SolicitudDeEliminacion(Hecho unHecho, String motivo) {
    if (motivo.length() > LONGITUD_MAXIMA_MOTIVO) {
      throw new RuntimeException("El motivo de la solicitud no puede superar los "
          + LONGITUD_MAXIMA_MOTIVO + " caracteres.");
    }
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

  public String getMotivo() {
    return motivo;
  }
}
