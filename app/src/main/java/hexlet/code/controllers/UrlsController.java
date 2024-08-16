package hexlet.code.controllers;

import hexlet.code.dto.BuildUrlsPage;
import hexlet.code.dto.UrlsPage;
import hexlet.code.model.Url;
import hexlet.code.repository.UrlRepository;
import hexlet.code.utils.NamedRoutes;
import io.javalin.http.Context;
import io.javalin.validation.ValidationException;

import java.sql.SQLException;
import java.sql.Timestamp;

import static io.javalin.rendering.template.TemplateUtil.model;

public class UrlsController {
    public static void mainPage(Context ctx) {
        var page = new BuildUrlsPage();
        ctx.render("index.jte", model("page", page));
    }

    public static void create(Context ctx) throws SQLException {
        try {
            var name = ctx.formParamAsClass("name", String.class)
                    .check(v -> {
                        try {
                            return UrlRepository.search(v).isEmpty();
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    },"Сайт с таким адресом уже существует")
                    .get();

            Timestamp timestamp = new Timestamp(System.currentTimeMillis());

            var url = new Url(name, timestamp);
            UrlRepository.save(url);
            ctx.sessionAttribute("flash", "Сайт добавлен!");
            ctx.redirect(NamedRoutes.urlsPath());

        } catch (ValidationException e) {
            var name = ctx.formParam("name");
            var page = new BuildUrlsPage(name, e.getErrors());
            ctx.render("index.jte", model("page", page));
        }
    }

    public static void show(Context ctx) throws SQLException {
        var urls = UrlRepository.getEntities();
        String flash = ctx.consumeSessionAttribute("flash");
        var page = new UrlsPage(urls);
        page.setFlash(flash);
        ctx.render("showUrls.jte", model("page", page));
    }
}
