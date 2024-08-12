package hexlet.code;

import io.javalin.Javalin;

public class App {
    public static void main(String[] args) {
        Javalin app = App.getApp();
        app.start(7070);
    }

    public static Javalin getApp() {
        var app = Javalin.create(config -> {
            config.bundledPlugins.enableDevLogging();
        });
        app.get("/", ctx -> ctx.result("Hello World"));
        return app;
    }
}
