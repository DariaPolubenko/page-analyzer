package hexlet.code.controllers;

import hexlet.code.model.UrlCheck;
import hexlet.code.repository.CheckRepository;
import hexlet.code.repository.UrlRepository;
import hexlet.code.utils.NamedRoutes;
import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.net.MalformedURLException;
import java.sql.SQLException;

public class CheckController {
    public static void checkUrl(Context ctx) throws SQLException {
        var urlId = ctx.pathParamAsClass("id", Long.class).get();
        var url = UrlRepository.find(urlId).orElseThrow(() -> new NotFoundResponse("Сайт не найден"));

        try {
            var check = getCheck(url.getName(), urlId);
            CheckRepository.saveCheck(check);
            ctx.redirect(NamedRoutes.urlsPath(urlId));

        } catch (MalformedURLException e) {
            ctx.sessionAttribute("flash", "Страница не существует");
            ctx.sessionAttribute("flash-type", "danger");
            ctx.redirect(NamedRoutes.urlsPath());
        }
    }

    public static UrlCheck getCheck(String url, Long urlId) throws MalformedURLException {
        try {
            HttpResponse<String> response = Unirest.get(url).asString();
            var statusCode = response.getStatus();
            var body = response.getBody();

            Document doc = Jsoup.parse(body);
            var title = doc.title();
            var h1 = doc.select("h1").text();
            var description = doc.select("meta[name=description]").attr("content");

            return new UrlCheck(statusCode, title, h1, description, urlId);

        } catch (Exception e) {
            throw new MalformedURLException();
        }
    }
}
