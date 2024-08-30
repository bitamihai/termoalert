package mbita.termoalert.app;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import mbita.termoalert.http.StatusGetter;
import mbita.termoalert.model.ImpactStatus;
import mbita.termoalert.parser.Parser;
import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@ApplicationScoped
public class App {

    private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

    private final Parser<String, ImpactStatus> impactStatusParser;
    private final StatusGetter statusGetter;

    @Inject
    public App(final StatusGetter statusGetter,
               @Named("impactStatusParser") final Parser<String, ImpactStatus> impactStatusParser) {
        this.statusGetter = statusGetter;
        this.impactStatusParser = impactStatusParser;
    }

    public void run() throws IOException, InterruptedException {
        final String statusHtml = statusGetter.get();
        final ImpactStatus impactStatus = impactStatusParser.parse(statusHtml);
    }

    public static void main(final String[] args) throws IOException, InterruptedException {
        Weld weld = new Weld();
        WeldContainer container = weld.initialize();
        container.select(App.class).get().run();
        container.shutdown();
    }

}
