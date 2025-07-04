package ar.edu.utn.frba.dds.dominio;
import java.net.URL;
/*<<<<<<< NavegacionModos
import java.time.*;
import java.util.List;
import java.util.concurrent.*;
public class Main {

    public static void main(String[] args) {
        try {
            Conexion conexion = new Conexion();
            URL url = new URL("https://prueba.url");
            RepositorioHechos repo = new RepositorioHechos();

            FuenteProxyDemo fuente = new FuenteProxyDemo(conexion, url, repo);
            fuente.obtenerHechos();

            // Crear agregador y registrar fuente (ejemplo)
            RepositorioFuentes repositorioFuentes = new RepositorioFuentes();
            repositorioFuentes.registrarFuente(fuente);
            Agregador agregador = new Agregador(repositorioFuentes, new FiltroBaseAgregador());

            // Colecciones registradas (en tu caso, podrían estar en otro repositorio)
            List<Coleccion> colecciones = List.of(
                new Coleccion("Nombre", "desc", fuente, List.of(), "handler", new AMayoriaSimple())
                // ... otras colecciones
            );

            // Lógica periódica asincrónica
            ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

            Runnable tareaConsenso = () -> {
                try {
                    System.out.println("Ejecutando tarea de consenso: " + LocalDateTime.now());

                    agregador.agregarHechos();
                    List<Hecho> hechosDelNodo = agregador.getHechos();

                    colecciones.forEach(c -> c.actualizarHechosConsensuados(hechosDelNodo));

                    System.out.println("Tarea de consenso finalizada.");
                } catch (Exception e) {
                    System.err.println("Error en la tarea de consenso:");
                    e.printStackTrace();
                }
            };

            // Definir hora objetivo (2:00 AM)
            LocalTime horaObjetivo = LocalTime.of(2, 0);
            LocalDateTime ahora = LocalDateTime.now();
            LocalDateTime proximaEjecucion = ahora.toLocalTime().isBefore(horaObjetivo)
                ? LocalDateTime.of(LocalDate.now(), horaObjetivo)
                : LocalDateTime.of(LocalDate.now().plusDays(1), horaObjetivo);

            long delayInicial = Duration.between(ahora, proximaEjecucion).toMillis();

            // Programar la tarea para que se ejecute cada 24 horas a partir de la próxima ejecución a las 2 AM
            scheduler.scheduleAtFixedRate(
                tareaConsenso,
                delayInicial,
                TimeUnit.DAYS.toMillis(1),
                TimeUnit.MILLISECONDS
            );

        } catch (Exception e) {
            System.err.println(LocalDateTime.now() + " ERROR: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
*/


import java.time.LocalDateTime;
import java.util.List;
import java.time.LocalDate;


//No esta terminado todavia
public class Main {
    public static void main(String[] args) {
        System.out.println("INICIO: Ejecución iniciada a las " + LocalDateTime.now());
        try {
          Hecho hecho = new Hecho("Corte de luz Dinamica",
        "Corte de luz en zona sur","cortes",21.2,
        12.8, LocalDate.of(2025,1,1),
        LocalDate.now(),TipoFuente.DINAMICA,"",Boolean.TRUE);
          

          FuenteDataSet fuenteDataSet = new FuenteDataSet("EjHechos.csv","d/M/yyyy",';');
          RepositorioHechos repositorioDeHechos = new RepositorioHechos();
          RepositorioHechos repositorioDeHechos2 = new RepositorioHechos();
          repositorioDeHechos2.cargarHecho(hecho);
          FuenteDinamica fuenteDinamica = new FuenteDinamica(repositorioDeHechos2);
          RepositorioFuentes repositorioDeFuentes = new RepositorioFuentes();
          ConexionMock conexion = new ConexionMock();
          URL url = new URL("http://mock.url");
          FuenteProxyDemo fuenteProxy = new FuenteProxyDemo(conexion, url, repositorioDeHechos);
          fuenteProxy.obtenerHechos();
          repositorioDeFuentes.registrarFuente(fuenteDinamica);
          repositorioDeFuentes.registrarFuente(fuenteDataSet);
          repositorioDeFuentes.registrarFuente(fuenteProxy);
          FiltroBaseAgregador filtroBase = new FiltroBaseAgregador();
          Agregador agregador = new Agregador(repositorioDeFuentes, filtroBase);
          agregador.agregarHechos();
          List<Hecho> hechos = agregador.getHechos();
          System.out.println("Cantidad de hechos: " + hechos.size());


        } catch (Exception e) {
            System.err.println("ERROR: " + e.getMessage());
        } 
            System.out.println("FIN: Ejecución finalizada a las " + LocalDateTime.now());
    
