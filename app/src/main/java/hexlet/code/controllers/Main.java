package hexlet.code.controllers;

import io.javalin.http.Context;

public class Main {
    public static void mainPage(Context ctx) {
        ctx.render("index.jte");
    }
}
