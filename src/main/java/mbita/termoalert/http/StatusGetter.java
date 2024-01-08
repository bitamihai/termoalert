package mbita.termoalert.http;

import jakarta.enterprise.context.ApplicationScoped;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

@ApplicationScoped
public class StatusGetter {
    private static final Logger logger = LoggerFactory.getLogger(StatusGetter.class);
    private static final long HTTP_CLIENT_CONNECT_TIMEOUT_SECONDS = 20;
    public static final int HTTP_CLIENT_REQUEST_TIMEOUT_MINUTES = 2;
    public static final String HTTP_CLIENT_URI = "https://www.cmteb.ro/functionare_sistem_termoficare.php";

    public String get() throws IOException, InterruptedException {
        logger.debug("Getting latest status");

        final HttpClient client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .followRedirects(HttpClient.Redirect.NORMAL)
                .connectTimeout(Duration.ofSeconds(HTTP_CLIENT_CONNECT_TIMEOUT_SECONDS))
                .build();
        logger.trace("Created HTTP client: <{}>", client);

        final HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(HTTP_CLIENT_URI))
                .timeout(Duration.ofMinutes(HTTP_CLIENT_REQUEST_TIMEOUT_MINUTES))
                .GET()
                .build();
        logger.trace("Created HTTP request: <{}>", request);

        final long timestampBeforeRequest = System.currentTimeMillis();
        final HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        final long timestampAfterRequest = System.currentTimeMillis();
        logger.trace("Received HTTP response in {} millis: <{}>", timestampAfterRequest - timestampBeforeRequest, response);
        logger.trace("{}", response.body());

        return response.body();
    }
}
