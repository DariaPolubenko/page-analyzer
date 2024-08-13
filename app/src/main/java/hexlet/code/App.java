package hexlet.code;

import hexlet.code.repository.BaseRepository;
import io.javalin.Javalin;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

//import java.io.BufferedReader;
import java.io.IOException;
//import java.io.InputStreamReader;
//import java.nio.charset.StandardCharsets;
//import java.util.stream.Collectors;

public class App {
    public static void main(String[] args) throws IOException {
        Javalin app = App.getApp();
        app.start(getPort());
    }

    private static String getDatabaseUrl() {
        return System.getenv().getOrDefault("JDBC_DATABASE_URL", "jdbc:h2:mem:project;DB_CLOSE_DELAY=-1;");
    }

    private static int getPort() {
        String port = System.getenv().getOrDefault("DB_PORT", "7070");
        return Integer.valueOf(port);
    }

    /*
    private static String readResourceFile(String fileName) throws IOException {
        var inputStream = App.class.getClassLoader().getResourceAsStream(fileName);
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            return reader.lines().collect(Collectors.joining("\n"));
        }
    }
     */

    public static Javalin getApp() throws IOException {
        var hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(getDatabaseUrl());

        var dataSource = new HikariDataSource(hikariConfig);
        BaseRepository.dataSource = dataSource;

        //var sql = readResourceFile("schema.sql");

        var app = Javalin.create(config -> {
            config.bundledPlugins.enableDevLogging();
        });

        app.get("/", ctx -> ctx.result("Hello World"));
        return app;
    }
}
