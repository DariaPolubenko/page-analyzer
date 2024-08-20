package hexlet.code;

import hexlet.code.repository.UrlRepository;
import hexlet.code.utils.NamedRoutes;
import io.javalin.Javalin;
import io.javalin.testtools.JavalinTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.SQLException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class AppTest {
    private static Javalin app;

    @BeforeEach
    public final void setUp() throws IOException, SQLException {
        app = App.getApp();
    }

    @Test
    public void testMainPage() {
        JavalinTest.test(app, (server, client) -> {
            var response = client.get(NamedRoutes.mainPath());
            assertThat(response.code()).isEqualTo(200);
        });
    }

    @Test
    public void testUrlsPage1() {
        JavalinTest.test(app, (server, client) -> {
            var response = client.get(NamedRoutes.urlsPath());
            assertThat(response.code()).isEqualTo(200);
        });
    }

    @Test
    public void testUrlNotFound() {
        JavalinTest.test(app, (server, client) -> {
            var response = client.get(NamedRoutes.urlsPath(Long.valueOf(1)));
            assertThat(response.code()).isEqualTo(404);
            assertThat(response.body().string()).contains("Сайт не найден");
        });
    }

    @Test
    public void testCreateUrl() {
        JavalinTest.test(app, (server, client) -> {
            var requestBody1 = "name=https://getbootstrap.com/docs/5.3/components/buttons/";
            var expected1 = "https://getbootstrap.com";

            var response1 = client.post(NamedRoutes.urlsPath(), requestBody1);
            assertThat(response1.code()).isEqualTo(200);

            var actual1 = UrlRepository.search(expected1).get().getId();
            assertThat(actual1).isEqualTo(1);

            var actual12 = UrlRepository.search(expected1).get().getName();
            assertThat(actual12).isEqualTo(expected1);

/*
            var requestBody2 = "http://localhost:7070/";
            var expected2 = "http://localhost:7070";

            var response21 = client.post(NamedRoutes.urlsPath(), requestBody2);
            assertThat(response21.code()).isEqualTo(200);

            var actual22 = UrlRepository.search(expected2).get().getId();
            assertThat(actual22).isEqualTo(2);

            var actual23 = UrlRepository.search(expected2).get().getName();
            assertThat(actual23).isEqualTo(expected2);

 */
            var response2 = client.get(NamedRoutes.urlsPath());
            assertThat(response2.code()).isEqualTo(200);

            var response3 = client.get(NamedRoutes.urlsPath(Long.valueOf(1)));
            assertThat(response3.code()).isEqualTo(200);
        });
    }
}
