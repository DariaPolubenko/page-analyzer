package hexlet.code.controllers;

import hexlet.code.dto.UrlCheckPage;
import hexlet.code.model.UrlCheck;
import hexlet.code.repository.UrlCheckRepository;
import hexlet.code.repository.UrlRepository;
import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.sql.SQLException;
import java.sql.Timestamp;

import static io.javalin.rendering.template.TemplateUtil.model;

public class UrlChecksController {

    public static void checkUrl(Context ctx) throws SQLException {
        var id = ctx.pathParamAsClass("id", Long.class).get();
        var url = UrlRepository.find(id).orElseThrow(() -> new NotFoundResponse("Сайт не найден"));

        HttpResponse<String> response = Unirest.get(url.getName()).asString();

        var statusCode = response.getStatus();
        var body = response.getBody();

        //Document doc = Jsoup.parse(body);
        //var title = doc.title();
        //var h1 = doc.select("h1").toString();
        //var description = doc.select("meta[name=description]").attr("content");
        var createdAt = new Timestamp(System.currentTimeMillis());

        var check = new UrlCheck(statusCode, "", "", "", id, createdAt);
        UrlCheckRepository.save(check);

        var checkPage = new UrlCheckPage(check);
        ctx.render("show.jte", model("checkPage", checkPage));
    }
}
