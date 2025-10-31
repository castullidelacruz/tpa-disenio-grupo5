package ar.edu.utn.frba.dds.server;

import ar.edu.utn.frba.dds.model.entities.fuentes.Fuente;
import ar.edu.utn.frba.dds.model.entities.fuentes.FuenteDinamica;
import ar.edu.utn.frba.dds.repositories.RepositorioFuentes;
import ar.edu.utn.frba.dds.server.templates.JavalinHandlebars;
import ar.edu.utn.frba.dds.server.templates.JavalinRenderer;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import io.javalin.Javalin;
import io.javalin.config.JavalinConfig;

public class Server implements WithSimplePersistenceUnit {
  public void start() {
    inicializarDatos();
    var app = Javalin.create(config -> {
      initializeStaticFiles(config);
      initializeTemplating(config);
    });

    new Router().configure(app);
    app.start(9001);
  }

  private void inicializarDatos() {
    // Encerramos la lógica de persistencia en una transacción
    withTransaction(() -> {
      RepositorioFuentes repoFuentes = RepositorioFuentes.getInstance();

      // Intentamos buscar la fuente con ID 1 (o la primera que exista)
      // Asumo que tu repositorio ya fue modificado para usar find()
      Fuente fuenteExistente = repoFuentes.getFuente(1L);

      if (fuenteExistente == null) {
        // Si la fuente no existe, la creamos y la persistimos.
        // JPA asignará un ID autoincremental (probablemente 1).
        FuenteDinamica fuenteDinamica = new FuenteDinamica();
        entityManager().persist(fuenteDinamica);
        System.out.println("--- Seeder: Fuente Dinámica inicial creada con ID: " + fuenteDinamica.getId());
      } else {
        System.out.println("--- Seeder: Fuente Dinámica inicial ya existía con ID: " + fuenteExistente.getId());
      }
    });
  }

  private void initializeTemplating(JavalinConfig config) {
    config.fileRenderer(
        new JavalinRenderer().register("hbs", new JavalinHandlebars())
    );
  }

  private static void initializeStaticFiles(JavalinConfig config) {
    config.staticFiles.add(staticFileConfig -> {
      staticFileConfig.hostedPath = "/assets";
      staticFileConfig.directory = "/assets";
    });
  }
}
