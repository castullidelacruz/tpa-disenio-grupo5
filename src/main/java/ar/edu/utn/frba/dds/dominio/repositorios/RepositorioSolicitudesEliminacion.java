package ar.edu.utn.frba.dds.dominio.repositorios;

import ar.edu.utn.frba.dds.dominio.solicitudes.EstadoSolicitud;
import ar.edu.utn.frba.dds.dominio.solicitudes.SolicitudDeEliminacion;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import java.util.List;

public class RepositorioSolicitudesEliminacion implements WithSimplePersistenceUnit {
  public RepositorioSolicitudesEliminacion() {
  }

  public void cargarSolicitudEliminacion(SolicitudDeEliminacion solicitud) {
    entityManager().persist(solicitud);
  }


  public List<SolicitudDeEliminacion> obtenerTodas() {
    return entityManager()
        .createQuery("from SolicitudDeEliminacion", SolicitudDeEliminacion.class).getResultList();
  }

  public List<SolicitudDeEliminacion> obtenerPendientesDeEliminacion() {
    return entityManager()
        .createQuery("from SolicitudDeEliminacion s where s.estado = :estado", SolicitudDeEliminacion.class)
        .setParameter("estado", EstadoSolicitud.PENDIENTE)
        .getResultList();
  }

  public List<SolicitudDeEliminacion> obtenerAceptadasDeEliminacion() {
    return entityManager()
        .createQuery("from SolicitudDeEliminacion s where s.estado = :estado", SolicitudDeEliminacion.class)
        .setParameter("estado", EstadoSolicitud.ACEPTADA
        )
        .getResultList();
  }
  /* POR AHORA NO USAMOS ESTE METODO
  public void borrarSolicitudEliminacion(SolicitudDeEliminacion solicitud) {
    entityManager().remove(entityManager().getReference(SolicitudDeEliminacion.class, solicitud.getId()));
  }
   */
}
