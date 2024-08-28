package hexlet.code;

import hexlet.code.model.Url;
import hexlet.code.repository.UrlRepository;
import hexlet.code.utils.NamedRoutes;
import hexlet.code.utils.Utils;
import io.javalin.Javalin;
import io.javalin.testtools.JavalinTest;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

import org.junit.jupiter.api.*;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.sql.Timestamp;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.Assert.assertEquals;

public class AppTest {
    private static Javalin app;
    private static MockWebServer mockServer;

    @BeforeEach
    public final void setUp() throws IOException, SQLException {
        app = App.getApp();

        mockServer = new MockWebServer();
        mockServer.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody(readFixture("test.jte")));
        mockServer.start();
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


    @Test
    public void testUrlCheck() {
        var serverUrl = mockServer.url("/").toString();

        //тут ответы от фейксервера приходят корректно, но если кинуть этов се дело в JavalinTest - все ломается с ошибкой: Invalid URL port: "63438urls"
        //HttpResponse<String> response1 = Unirest.get(baseUrl).asString();
        //var code = response1.getStatus();
        //assertEquals(200, code);
        //assertThat(response1.getBody().toString()).contains("Test MockWebServer");

        JavalinTest.test(app, (server, client) -> {
            var url = new Url(serverUrl, new Timestamp(System.currentTimeMillis()));
            UrlRepository.save(url);

            var response2 = client.get(NamedRoutes.urlCheck(url.getId()));
            assertThat(response2.code()).equals(200);

        });
    }


    public static String readFixture(String filepath) throws IOException{
        var fullPath = Paths.get("src/test/resources/fixtures/" + filepath).toAbsolutePath().normalize();

        if (!Files.exists(fullPath)) {
            throw new IOException("File '" + fullPath + "' does not exist");
        }
        var content = Files.readString(fullPath);
        return content;
    }

}
