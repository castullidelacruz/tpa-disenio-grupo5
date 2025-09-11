package ar.edu.utn.frba.dds.dominio.algoritmosconcenso;

import ar.edu.utn.frba.dds.dominio.Hecho;
import ar.edu.utn.frba.dds.dominio.fuentes.TipoFuente;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("A_ABSOLUTAS")
public class Aabsoluta extends AlgoritmoDeConsenso {
  public Aabsoluta() {
  }

  @Override
  public boolean estaConsensuado(Hecho hecho, List<Hecho> hechosNodo) {

    Set<TipoFuente> fuentesDelNodo = hechosNodo.stream()
        .map(Hecho::getOrigen)
        .collect(Collectors.toSet());

    return fuentesDelNodo.stream()
        .allMatch(fuente ->
            hechosNodo.stream()
                .filter(h -> h.getOrigen().equals(fuente))
                .anyMatch(h -> sonIguales(h, hecho))
        );
  }

  private boolean sonIguales(Hecho a, Hecho b) {

    if (a == b) {
      return true;
    }
    if (a == null || b == null) {
      return false;
    }

    return a.equals(b);
  }
}

