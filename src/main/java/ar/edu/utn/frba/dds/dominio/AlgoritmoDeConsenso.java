package ar.edu.utn.frba.dds.dominio;
import java.util.Objects;
import java.util.List;

public interface AlgoritmoDeConsenso {

  boolean estaConsensuado(Hecho hecho, List<Hecho> hechosNodo);

}