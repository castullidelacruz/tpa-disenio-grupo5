package ar.edu.utn.frba.dds.dominio.repositorios;

import ar.edu.utn.frba.dds.dominio.algoritmosconcenso.AlgoritmoDeConsenso;
import ar.edu.utn.frba.dds.dominio.criterios.Criterio;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import java.util.List;

public class RepositorioCriterios implements WithSimplePersistenceUnit  {

  public void cargarCriterio(Criterio criterio) {
    entityManager().persist(criterio);
  }

  public List<Criterio> obtenerTodos() {
    return entityManager()
        .createQuery("from Criterio ", Criterio.class)
        .getResultList();
  }

}
