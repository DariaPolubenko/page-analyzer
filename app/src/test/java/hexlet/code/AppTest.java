package hexlet.code;

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
            var requestBody = "name=https://getbootstrap.com/docs/5.3/components/buttons/";

            var response1 = client.post(NamedRoutes.urlsPath(), requestBody);
            assertThat(response1.code()).isEqualTo(200);

            var response2 = client.get(NamedRoutes.urlsPath());
            assertThat(response2.code()).isEqualTo(200);

            var response3 = client.get(NamedRoutes.urlsPath(Long.valueOf(1)));
            assertThat(response3.code()).isEqualTo(200);
        });
    }
}
