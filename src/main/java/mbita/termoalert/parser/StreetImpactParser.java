package mbita.termoalert.parser;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import mbita.termoalert.model.BuildingImpact;
import mbita.termoalert.model.StreetImpact;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@ApplicationScoped
class StreetImpactParser implements Parser<String, Set<StreetImpact>> {
    private static final Logger logger = LoggerFactory.getLogger(StreetImpactParser.class);

    private final Parser<String, Set<BuildingImpact>> buildingImpactParser;

    @Inject
    public StreetImpactParser(@Named("buildingImpactParser")
                                  final Parser<String, Set<BuildingImpact>> buildingImpactParser) {
        this.buildingImpactParser = buildingImpactParser;
    }

    public Set<StreetImpact> parse(final String string) {
        logger.debug("Parsing street impact");
        logger.trace("Parsing street impact from string <{}>", string);

        final String[] streetData = string.split("• ");
        logger.trace("Splitted string to <{}>", Arrays.toString(streetData));

        final Pattern pattern = Pattern.compile("(.*) - (.*)");
        final List<StreetImpact> streetImpacts = Arrays.stream(streetData)
                .map(pattern::matcher)
                .filter(Matcher::find)
                .map(matcher -> new StreetImpact(matcher.group(1), buildingImpactParser.parse(matcher.group(2))))
                .toList();
        logger.trace("Build street impact list <{}>", streetImpacts);

        return Set.copyOf(streetImpacts);
    }
}
