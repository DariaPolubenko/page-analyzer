package hexlet.code.controllers;

import hexlet.code.dto.UrlCheckPage;
import hexlet.code.model.UrlCheck;
import hexlet.code.repository.UrlCheckRepository;
import hexlet.code.repository.UrlRepository;
import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;

import java.sql.SQLException;
import java.sql.Timestamp;

import static io.javalin.rendering.template.TemplateUtil.model;

public class UrlChecksController {

    public static void checkUrl(Context ctx) throws SQLException {
        var id = ctx.pathParamAsClass("id", Long.class).get();
        var url = UrlRepository.find(id).orElseThrow(() -> new NotFoundResponse("Сайт не найден"));

        // здесь должна быть проверка
        // и сохранение полученных данных в UrlCheckRepository
        var check = new UrlCheck(202, "title", "h1", "description", url.getId(), new Timestamp(System.currentTimeMillis()));
        UrlCheckRepository.save(check);

        var checkPage = new UrlCheckPage(check);
        ctx.render("show.jte", model("checkPage", checkPage));

    }
}
