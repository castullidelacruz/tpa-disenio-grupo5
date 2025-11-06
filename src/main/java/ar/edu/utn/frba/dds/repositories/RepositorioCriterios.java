package ar.edu.utn.frba.dds.repositories;

import ar.edu.utn.frba.dds.model.entities.criterios.Criterio;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.util.List;

public class RepositorioCriterios implements WithSimplePersistenceUnit  {
  static RepositorioCriterios instance = new RepositorioCriterios();

  public static RepositorioCriterios getInstance() {
    return instance;
  }

  public void cargarCriterio(Criterio criterio) {
    entityManager().persist(criterio);
  }

  public List<Criterio> obtenerTodos() {
    return entityManager()
        .createQuery("from Criterio ", Criterio.class)
        .getResultList();
  }

  public List<Criterio> obtenerCriteriosPorId(List<Long> ids) {
    if (ids == null || ids.isEmpty()) {
      return List.of();
    }
    return entityManager()
        .createQuery("FROM Criterio c WHERE c.id IN :ids", Criterio.class)
        .setParameter("ids", ids)
        .getResultList();
  }

}
