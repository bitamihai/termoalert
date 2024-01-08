package mbita.termoalert.parser;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import mbita.termoalert.model.StreetImpact;
import mbita.termoalert.model.ThermalNodeImpact;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@ApplicationScoped
class ThermalNodeImpactParser implements Parser<Element, Set<ThermalNodeImpact>> {
    private static final Logger logger = LoggerFactory.getLogger(ThermalNodeImpactParser.class);

    private final Parser<String, Set<StreetImpact>> streetImpactParser;

    @Inject
    public ThermalNodeImpactParser(@Named("streetImpactParser")
                                       final Parser<String, Set<StreetImpact>> streetImpactParser) {
        this.streetImpactParser = streetImpactParser;
    }

    public Set<ThermalNodeImpact> parse(final Element element) {
        logger.debug("Parsing thermal node impact");
        logger.trace("Parsing thermal node impact from element <{}>", element);

        final String[] thermalNodesData = element.text().split("Punct termic: ");
        logger.trace("Splitted element string to <{}>", Arrays.toString(thermalNodesData));

        final Pattern pattern = Pattern.compile("(.*) -- (.*)");
        final List<ThermalNodeImpact> thermalNodeImpacts = Arrays.stream(thermalNodesData)
                .map(pattern::matcher)
                .filter(Matcher::find)
                .map(matcher -> new ThermalNodeImpact(matcher.group(1), streetImpactParser.parse(matcher.group(2))))
                .toList();
        logger.trace("Built thermal node impact list <{}>", thermalNodeImpacts);

        return Set.copyOf(thermalNodeImpacts);
    }
}
