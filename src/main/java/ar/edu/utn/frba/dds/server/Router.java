package ar.edu.utn.frba.dds.server;

import ar.edu.utn.frba.dds.controllers.HomeController;
import ar.edu.utn.frba.dds.controllers.LoginController;
import ar.edu.utn.frba.dds.controllers.RegistroController;
import io.javalin.Javalin;

public class Router {
  public void configure(Javalin app) {
    HomeController controller = new HomeController();
    LoginController loginController = new LoginController();
    RegistroController registroController = new RegistroController();
    app.get("/home", ctx -> ctx.render("home.hbs", controller.index(ctx)));
    app.get("/", ctx -> ctx.redirect("/home"));
    app.get("/register", registroController::renderRegister);
    app.post("/register", registroController::handleRegister);
    app.get("/login", loginController::renderLogin);
    app.post("/login", loginController::handleLogin);
    app.get("/logout", loginController::handleLogout);
  }
}
