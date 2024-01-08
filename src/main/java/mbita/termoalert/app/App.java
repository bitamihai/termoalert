package mbita.termoalert.app;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import mbita.termoalert.http.StatusGetter;
import mbita.termoalert.model.ImpactStatus;
import mbita.termoalert.parser.Parser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

@ApplicationScoped
public class App {
    private static final Logger logger = LoggerFactory.getLogger(App.class);

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
        impactStatusParser.parse(statusHtml);
    }
}
