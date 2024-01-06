package mbita;

import jakarta.enterprise.context.ApplicationScoped;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

@ApplicationScoped
public class App {
    private static final Logger logger = LoggerFactory.getLogger(App.class);
    private static final long HTTP_CLIENT_CONNECT_TIMEOUT_SECONDS = 20;
    public static final int HTTP_CLIENT_REQUEST_TIMEOUT_MINUTES = 2;
    public static final String HTTP_CLIENT_URI = "https://www.cmteb.ro/functionare_sistem_termoficare.php";

    public void run() throws IOException, InterruptedException {
        logger.debug("Creating HTTP client");
        final HttpClient client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .followRedirects(HttpClient.Redirect.NORMAL)
                .connectTimeout(Duration.ofSeconds(HTTP_CLIENT_CONNECT_TIMEOUT_SECONDS))
                .build();
        logger.trace("Created HTTP client: <{}>", client);

        logger.debug("Creating HTTP request");
        final HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(HTTP_CLIENT_URI))
                .timeout(Duration.ofMinutes(HTTP_CLIENT_REQUEST_TIMEOUT_MINUTES))
                .GET()
                .build();
        logger.trace("Created HTTP request: <{}>", request);

        logger.debug("Sending HTTP request");
        final long timestampBeforeRequest = System.currentTimeMillis();
        final HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        final long timestampAfterRequest = System.currentTimeMillis();
        logger.trace("Received HTTP response in {} millis: <{}>", timestampAfterRequest - timestampBeforeRequest, response);

        logger.debug("Parsing HTML response body");
        final long timestampBeforeParse = System.currentTimeMillis();
        final Document document = Jsoup.parse(response.body());
        final long timestampAfterParse = System.currentTimeMillis();
        logger.trace("Parsed HTML response in {} millis: <{}>", timestampAfterParse - timestampBeforeParse, document);

        logger.debug("Looking for full table div");
        final Element fullTableDivElement = document.getElementById("ST");
        logger.trace("Found full table div: <{}>", fullTableDivElement);

        logger.debug("Looking for entries for each sector");
        final Elements sectorElements = fullTableDivElement.getElementsByTag("tr");
        sectorElements.remove(0);
        logger.trace("Found {} elements:", sectorElements.stream().count());
        sectorElements.forEach(element -> logger.trace("Found sector element: <{}>", element));
    }
}
