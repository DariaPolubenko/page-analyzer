package hexlet.code;

import hexlet.code.controllers.Main;
import hexlet.code.repository.BaseRepository;
import hexlet.code.utils.NamedRoutes;
import hexlet.code.utils.Utils;
import io.javalin.Javalin;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.io.IOException;
import java.sql.SQLException;

import io.javalin.http.Context;
import io.javalin.rendering.template.JavalinJte;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class App {
    public static void main(String[] args) throws IOException, SQLException {
        Javalin app = App.getApp();
        app.start(Utils.getPort());
    }

    public static Javalin getApp() throws IOException, SQLException {
        var hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(Utils.getDatabaseUrl());

        var dataSource = new HikariDataSource(hikariConfig);
        BaseRepository.dataSource = dataSource;

        var sql = Utils.readResourceFile("schema.sql");
        log.info(sql);
        try (var connection = dataSource.getConnection();
             var statement = connection.createStatement()) {
            statement.execute(sql);
        }

        var app = Javalin.create(config -> {
            config.bundledPlugins.enableDevLogging();
            config.fileRenderer(new JavalinJte(Utils.createTemplateEngine()));
        });

        app.get(NamedRoutes.mainPath(), Main::mainPage);
        return app;
    }
}
