package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.dominio.Hecho;
import ar.edu.utn.frba.dds.dominio.repositorios.RepoHechos;
import ar.edu.utn.frba.dds.dominio.repositorios.RepositorioHechos;
import io.github.flbulgarelli.jpa.extras.test.SimplePersistenceTest;
import java.util.List;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;

public class PersistenciaHechosTest implements SimplePersistenceTest {
  RepoHechos repoHechos = new RepoHechos();
  RepositorioHechos repositorioHechos = new RepositorioHechos();
  @Test
  public void guardarHecho() {
    withTransaction(() -> {
      Hecho hecho = new Hecho();
      hecho.setTitulo("unHecho");
      hecho.setDescripcion("unHecho descripcion");
      repoHechos.registrar(hecho);
    });

    List<Hecho> hechos = repoHechos.obtenerHechos();

    //assertEquals(1, hechos.size());
    assertEquals("unHecho", hechos.get(0).getTitulo());
    assertEquals("unHecho descripcion", hechos.get(0).getDescripcion());
  }


  @Test
  public void guardarHechoConMetodosNormales() {

    Hecho hecho = new Hecho();
    hecho.setTitulo("unHecho");
    hecho.setDescripcion("unHecho descripcion");
    repositorioHechos.cargarHecho(hecho);

    Hecho hecho2 = new Hecho();
    hecho2.setTitulo("unHecho2");
    hecho2.setDescripcion("unHecho2 descripcion");
    repositorioHechos.cargarHecho(hecho2);

    List<Hecho> hechos = repositorioHechos.obtenerTodos();

    assertEquals("unHecho", hechos.get(0).getTitulo());
    assertEquals("unHecho descripcion", hechos.get(0).getDescripcion());

    assertEquals("unHecho2", hechos.get(1).getTitulo());
    assertEquals("unHecho2 descripcion", hechos.get(1).getDescripcion());

    repositorioHechos.borrarHecho(hecho);

    List<Hecho> hechosPostBorrado = repositorioHechos.obtenerTodos();

    assertEquals("unHecho2", hechosPostBorrado.get(0).getTitulo());
    assertEquals("unHecho2 descripcion", hechosPostBorrado.get(0).getDescripcion());
  }
}
