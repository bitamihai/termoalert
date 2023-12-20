package mbita;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);
    private static final long HTTP_CLIENT_CONNECT_TIMEOUT_SECONDS = 20;
    public static final int HTTP_CLIENT_REQUEST_TIMEOUT_MINUTES = 2;
    public static final String HTTP_CLIENT_URI = "https://www.cmteb.ro/functionare_sistem_termoficare.php";

    public static void main(final String[] args) throws IOException, InterruptedException {
        final HttpClient client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .followRedirects(HttpClient.Redirect.NORMAL)
                .connectTimeout(Duration.ofSeconds(HTTP_CLIENT_CONNECT_TIMEOUT_SECONDS))
                .build();

        final HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(HTTP_CLIENT_URI))
                .timeout(Duration.ofMinutes(HTTP_CLIENT_REQUEST_TIMEOUT_MINUTES))
                .GET()
                .build();

        final long timestampBeforeRequest = System.currentTimeMillis();
        final HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        final long timestampAfterRequest = System.currentTimeMillis();

        logger.info("Response received with status code <{}> and body <{}>", response.statusCode(), response.body());
        logger.info("Request took {} millis", timestampAfterRequest - timestampBeforeRequest);

        final long timestampBeforeParse = System.currentTimeMillis();
        final Document document = Jsoup.parse(response.body());
        final long timestampAfterParse = System.currentTimeMillis();

        logger.info("Parsing the response body took {} millis", timestampAfterParse - timestampBeforeParse);
    }
}