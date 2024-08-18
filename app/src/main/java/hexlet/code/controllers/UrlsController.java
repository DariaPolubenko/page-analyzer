package hexlet.code.controllers;

import hexlet.code.dto.BuildUrlsPage;
import hexlet.code.dto.UrlsPage;
import hexlet.code.model.Url;
import hexlet.code.repository.UrlRepository;
import hexlet.code.utils.NamedRoutes;
import io.javalin.http.Context;
import io.javalin.validation.ValidationException;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.net.URI;

import static io.javalin.rendering.template.TemplateUtil.model;

public class UrlsController {
    public static void mainPage(Context ctx) {
        var page = new BuildUrlsPage();
        ctx.render("index.jte", model("page", page));
    }

    public static void create(Context ctx) throws SQLException, URISyntaxException, MalformedURLException {
        var name = ctx.formParam("name");
        var url = new URI(name).toURL();

        var normalizedUrl = UriComponentsBuilder.newInstance()
                .scheme(url.getProtocol())
                .host(url.getHost())
                .port(url.getPort())
                .build()
                .toUri()
                .toURL()
                .toString();

        if (UrlRepository.search(normalizedUrl).isEmpty()) {
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());

            var resultUrl = new Url(normalizedUrl, timestamp);
            UrlRepository.save(resultUrl);
            ctx.sessionAttribute("flash", "Сайт добавлен!");
            ctx.redirect(NamedRoutes.urlsPath());

        } else {
            var page = new BuildUrlsPage(normalizedUrl, "Сайт с таким адресом уже существует");
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
