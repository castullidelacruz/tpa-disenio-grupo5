package ar.edu.utn.frba.dds.model.entities.solicitudes;

import static java.util.Objects.requireNonNull;

import ar.edu.utn.frba.dds.model.entities.Hecho;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@SuppressFBWarnings(
    value = {"EI_EXPOSE_REP", "EI_EXPOSE_REP2"},
    justification =
        "El campo Hecho es una entidad gestionada por JPA; "
            + "su referencia es intencional y controlada por el contexto de persistencia."
)
@Entity
public class SolicitudDeEliminacion {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @ManyToOne
  private Hecho hecho;
  @Column
  private String motivo;
  @Enumerated(EnumType.STRING)
  private EstadoSolicitud estado;
  @Column
  private Boolean esSpam;

  public SolicitudDeEliminacion(Hecho hecho, String motivo,
      EstadoSolicitud estado,  Boolean esSpam) {
    if (motivo.length() > 500) {
      throw new RuntimeException("El motivo es demasiado extenso.");
    }
    this.hecho = hecho;
    this.motivo = requireNonNull(motivo);
    this.estado = requireNonNull(estado);
    this.esSpam = esSpam;
  }

  public SolicitudDeEliminacion() {
  }

  public EstadoSolicitud getEstado() {
    return estado;
  }

  public void setEstado(EstadoSolicitud estado) {
    this.estado = estado;
  }

  public String getMotivo() {
    return motivo;
  }


  public Hecho getHecho() {
    return hecho;
  }

  public Boolean getEsSpam() {
    return esSpam;
  }

  public Long getId() {
    return id;
  }

  public void cambiarEstado(EstadoSolicitud evaluacion) {
    if (!estado.equals(EstadoSolicitud.PENDIENTE)) {
      throw new IllegalStateException("La solicitud ya fue evaluada.");
    }
    this.estado = evaluacion;
    if (estado.equals(EstadoSolicitud.ACEPTADA)) {
      hecho.setDisponibilidad(Boolean.FALSE);
    }
  }

  public void rechazar() {
    this.estado = EstadoSolicitud.RECHAZADA;
  }
  /*
  public void aprobar() {
    if (estado.equals(EstadoSolicitud.ACEPTADA)) {
      throw new IllegalStateException("La solicitud ya fue evaluada.");
    }
    this.estado = EstadoSolicitud.ACEPTADA;
    if (this.hecho != null) {
      this.hecho.setDisponibilidad(false);
    }
  }

   */

  public void aprobar() {
    if (estado.equals(EstadoSolicitud.ACEPTADA)) {
      throw new IllegalStateException("La solicitud ya fue evaluada.");
    }
    this.estado = EstadoSolicitud.ACEPTADA;
  }

  public boolean requiereEliminacion() {
    return this.estado == EstadoSolicitud.ACEPTADA;
  }

}
