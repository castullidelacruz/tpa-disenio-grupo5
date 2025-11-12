package ar.edu.utn.frba.dds.model.entities.fuentes;

import ar.edu.utn.frba.dds.model.entities.Hecho;
import ar.edu.utn.frba.dds.repositories.RepositorioHechos;
import java.util.List;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;

@Entity
@DiscriminatorValue("FUENTE_DINAMICA")
public class FuenteDinamica extends Fuente {
  private static RepositorioHechos repositorioHechos = RepositorioHechos.getInstance();

  public static void setRepositorioHechos(RepositorioHechos repo) {
    repositorioHechos = repo;
  }

  public FuenteDinamica() {

  }

  @Override
  public List<Hecho> getHechos() {
    return repositorioHechos.obtenerTodos().stream()
        .filter(hecho -> hecho.getOrigen() == TipoFuente.DINAMICA)
        .toList();
  }

  @Override
  public void actualizarHechos() {
  }
}