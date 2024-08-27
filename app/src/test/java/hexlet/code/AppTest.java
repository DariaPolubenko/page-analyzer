package hexlet.code;

import hexlet.code.repository.UrlRepository;
import hexlet.code.utils.NamedRoutes;
import io.javalin.Javalin;
import io.javalin.testtools.JavalinTest;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
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
    public void testUrlsPage() {
        JavalinTest.test(app, (server, client) -> {
            var response = client.get(NamedRoutes.urlsPath());
            assertThat(response.code()).isEqualTo(200);
        });
    }

    @Test
    public void testUrlPageNotFound() {
        JavalinTest.test(app, (server, client) -> {
            var response = client.get(NamedRoutes.urlsPath(Long.valueOf(1)));
            assertThat(response.code()).isEqualTo(404);
            assertThat(response.body().string()).contains("Сайт не найден");
        });
    }

    @Test
    public void testCreateUrl() {
        JavalinTest.test(app, (server, client) -> {
            var requestBody = "name=https://getbootstrap.com/docs/5.3/components/buttons/";
            var expected = "https://getbootstrap.com";

            var response1 = client.post(NamedRoutes.urlsPath(), requestBody);
            assertThat(response1.code()).isEqualTo(200);
            assertThat(response1.body().string()).contains("https://getbootstrap.com");

            var actual1 = UrlRepository.search(expected).get().getId();
            assertThat(actual1).isEqualTo(1);

            var response3 = client.get(NamedRoutes.urlsPath(Long.valueOf(1)));
            assertThat(response3.code()).isEqualTo(200);
        });
    }
/*
    @Test
    public void testUrlCheck() {
        var server = new MockWebServer();
        var url = server.url("https://www.google.com").toString();

        var response = new MockResponse()
                .

    }

 */
}
