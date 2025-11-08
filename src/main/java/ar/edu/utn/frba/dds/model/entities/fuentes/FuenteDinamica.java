package ar.edu.utn.frba.dds.model.entities.fuentes;

import ar.edu.utn.frba.dds.model.entities.Hecho;
import ar.edu.utn.frba.dds.repositories.RepositorioHechos;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;

@Entity
@DiscriminatorValue("FUENTE_DINAMICA")
public class FuenteDinamica extends Fuente {
  @Transient
  RepositorioHechos repoH = RepositorioHechos.getInstance();

  public FuenteDinamica() {
  }

  @Override
  public List<Hecho> getHechos() {

    return repoH.obtenerTodos().stream()
        .filter(h -> h.getFuenteOrigen() instanceof FuenteDinamica).toList();
  }

  @Override
  public void actualizarHechos() {
  }
}