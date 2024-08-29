package hexlet.code;

import hexlet.code.model.Url;
import hexlet.code.repository.UrlRepository;
import hexlet.code.utils.NamedRoutes;
import io.javalin.Javalin;
import io.javalin.testtools.JavalinTest;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.sql.Timestamp;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class AppTest {
    private static Javalin app;
    private static MockWebServer mockServer;

    @BeforeAll
    public static void setMockServer() throws  IOException {
        mockServer = new MockWebServer();
        var mockedResponse = new MockResponse()
                .setResponseCode(200)
                .setBody(readFixture("index.jte"));
        mockServer.enqueue(mockedResponse);
        mockServer.start();
    }

    @BeforeEach
    public final void setUp() throws IOException, SQLException {
        app = App.getApp();
    }

    @AfterAll
    public static void stopMockServer() throws IOException {
        mockServer.shutdown();
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
            var requestBody = "url=https://getbootstrap.com/docs/5.3/components/buttons/";
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
        JavalinTest.test(app, (server, client) -> {
            var serverUrl = mockServer.url("/").toString();
            var url = new Url(serverUrl, new Timestamp(System.currentTimeMillis()));
            UrlRepository.save(url);

            var response2 = client.post(NamedRoutes.urlCheck(url.getId()));
            assertThat(response2.code()).isEqualTo(200);
            assertThat(response2.body().string()).contains(readFixture("response.jte"));
        });
    }

    public static String readFixture(String filepath) throws IOException {
        var fullPath = Paths.get("src/test/resources/fixtures/" + filepath).toAbsolutePath().normalize();

        if (!Files.exists(fullPath)) {
            throw new IOException("File '" + fullPath + "' does not exist");
        }
        var content = Files.readString(fullPath);
        return content;
    }
}
