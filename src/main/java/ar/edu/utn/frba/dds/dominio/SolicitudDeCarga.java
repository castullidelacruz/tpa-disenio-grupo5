package ar.edu.utn.frba.dds.dominio;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class SolicitudDeCarga implements Solicitud {
  private Hecho hecho;
  private boolean registrado;
  private String sugerencia = "";
  private EstadoSolicitud estado = EstadoSolicitud.PENDIENTE;
  private RepositorioHechos repositorioH;
  //borre el reposolicitudes

  public SolicitudDeCarga(Hecho hecho, boolean registerBoolean,
                          RepositorioHechos rh) {
    this.hecho = hecho;
    this.registrado = registerBoolean;
    this.repositorioH = rh;
  }

  public EstadoSolicitud getEstado() {
    return estado;
  }

  public String getSugerencia() {
    return sugerencia;
  }

  public void setEstado(EstadoSolicitud estado) {
    this.estado = estado;
  }

  public void setSugerencia(String s) {
    this.sugerencia = s;
  }

  public void aprobar() {
    if (estado.equals(EstadoSolicitud.ACEPTADA)) {

      throw new IllegalStateException("La solicitud ya fue evaluada.");
    } else {

      this.estado = EstadoSolicitud.ACEPTADA;
      repositorioH.cargarHecho(hecho);
    }
  }

  public void rechazar() {

    this.estado = EstadoSolicitud.RECHAZADA;
  }

  public void sugerir(String sugerencia) {
    this.sugerencia = sugerencia;
  }

  public void cambiarEstado(EstadoSolicitud evaluacion) {
  }

  public void modificarHecho(Hecho hechoModificador) {
    if (estado.equals(EstadoSolicitud.ACEPTADA) && registrado
        && (ChronoUnit.DAYS.between(hecho.getFechaDeCarga(), LocalDate.now())) <= 7) {
      hecho.modificar(hechoModificador);
    } else {
      throw new RuntimeException("No se puede modificar este hecho");
    }
  }
}
