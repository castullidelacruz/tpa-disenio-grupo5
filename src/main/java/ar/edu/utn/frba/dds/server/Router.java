package ar.edu.utn.frba.dds.server;

import ar.edu.utn.frba.dds.controllers.HechoController;
import ar.edu.utn.frba.dds.controllers.HomeController;
import io.javalin.Javalin;

public class Router {
  public void configure(Javalin app) {
    HomeController controller = new HomeController();
    HechoController hechoController = new HechoController();
    //SolicitudController solicitudController = new SolicitudController();

    app.get("/home", ctx -> ctx.render("home.hbs", controller.index(ctx)));
    app.get("/", ctx -> ctx.redirect("/home"));

    //CREACION DE HECHOS
    app.get("/hechos/nuevo", ctx -> ctx.render("creacion.hbs",
        hechoController.showCreationForm(ctx)));
    app.post("/hechos", (hechoController::create));
    app.get("/hechos/confirmacion/{solicitudId}", (hechoController::showConfirmation));
  }
}
