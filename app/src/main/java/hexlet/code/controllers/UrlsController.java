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

        String flash = ctx.consumeSessionAttribute("flash");
        page.setFlash(flash);

        String flashType = ctx.consumeSessionAttribute("flash-type");
        page.setFlashType(flashType);

        ctx.render("index.jte", model("page", page));
    }

    public static void create(Context ctx) {
        var name = ctx.formParam("name");

        try {
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
                ctx.sessionAttribute("flash-type", "success");
                ctx.redirect(NamedRoutes.urlsPath());

            } else {
                ctx.sessionAttribute("flash", "Сайт уже существует");
                ctx.sessionAttribute("flash-type", "warning");
                ctx.redirect(NamedRoutes.urlsPath());
            }

        } catch (Exception e) {
            ctx.sessionAttribute("flash", "Некорректный URL");
            ctx.sessionAttribute("flash-type", "danger");

            ctx.redirect(NamedRoutes.mainPath());
        }

        /* } catch (URISyntaxException e) {
            ctx.sessionAttribute("flash", "Некорректный URL");
            ctx.sessionAttribute("flash-type", "danger");

            ctx.redirect(NamedRoutes.mainPath());
        }
         */
    }

    public static void show(Context ctx) throws SQLException {
        var urls = UrlRepository.getEntities();
        var page = new UrlsPage(urls);

        String flash = ctx.consumeSessionAttribute("flash");
        page.setFlash(flash);

        String flashType = ctx.consumeSessionAttribute("flash-type");
        page.setFlashType(flashType);

        ctx.render("showUrls.jte", model("page", page));
    }
}
