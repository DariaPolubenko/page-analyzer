package hexlet.code.controllers;

import hexlet.code.dto.UrlPage;
import hexlet.code.model.UrlCheck;
import hexlet.code.repository.UrlCheckRepository;
import hexlet.code.repository.UrlRepository;
import hexlet.code.utils.NamedRoutes;
import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.sql.SQLException;
import java.sql.Timestamp;

import static io.javalin.rendering.template.TemplateUtil.model;

public class UrlCheckController {

    public static void checkUrl(Context ctx) throws SQLException {
        var id = ctx.pathParamAsClass("id", Long.class).get();
        var url = UrlRepository.find(id).orElseThrow(() -> new NotFoundResponse("Сайт не найден"));
        var check = getCheck(url.getName(), id);
        UrlCheckRepository.saveCheck(check);
        ctx.redirect(NamedRoutes.urlsPath(id));
    }

    public static UrlCheck getCheck(String url, Long id) throws SQLException {
        HttpResponse<String> response = Unirest.get(url).asString();

        var statusCode = response.getStatus();
        var body = response.getBody();

        Document doc = Jsoup.parse(body);
        var title = doc.title();
        var h1 = doc.select("h1").toString();
        var description = doc.select("meta[name=description]").attr("content");
        var createdAt = new Timestamp(System.currentTimeMillis());

       return new UrlCheck(statusCode, title, h1, description, id, createdAt);
    }
}
