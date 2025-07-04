package ar.edu.utn.frba.dds.dominio;

import java.net.URL;
import java.time.*;
import java.util.List;
import java.util.concurrent.*;
public class Main {

    public static void main(String[] args) {
        try {

        RepositorioHechos repositorioDeHechos2 = new RepositorioHechos();
          SolicitudDeCarga solicitudDeCargaPrimera = new SolicitudDeCarga("Corte de luz","Corte de luz en zona sur"
          ,"cortes",21.2,12.8, LocalDate.of(2025,1,1),"",Boolean.TRUE,repositorioDeHechos2);
          solicitudDeCargaPrimera.aprobar();
          FuenteDinamica fuenteDinamica = new FuenteDinamica(repositorioDeHechos2);
          RepositorioFuentes repositorioDeFuentes = new RepositorioFuentes();

          repositorioDeFuentes.registrarFuente(fuenteDinamica);
          FiltroBaseAgregador filtroBase = new FiltroBaseAgregador();
          Agregador agregador = new Agregador(repositorioDeFuentes, filtroBase);
          agregador.agregarHechos();
          List<Hecho> hechos = agregador.getHechos();

        FuenteDataSet fuenteDataSet = new FuenteDataSet("/home/spinozista/tpa-2025-05/EjHechos.csv","d/M/yyyy",';');
        Coleccion coleccion = new Coleccion("hechoPrueba","hechoPrueba",fuenteDataSet, List.of(),"1",new AMayoriaSimple());
        coleccion.actualizarHechosConsensuados(hechos);


        } catch (Exception e) {
            System.err.println("ERROR: " + e.getMessage());
        }
    }
}



