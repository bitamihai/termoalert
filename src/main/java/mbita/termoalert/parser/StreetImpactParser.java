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

    private static final Logger LOGGER = LoggerFactory.getLogger(StreetImpactParser.class);

    private final Parser<String, Set<BuildingImpact>> buildingImpactParser;

    @Inject
    public StreetImpactParser(@Named("buildingImpactParser")
                                  final Parser<String, Set<BuildingImpact>> buildingImpactParser) {
        this.buildingImpactParser = buildingImpactParser;
    }

    public Set<StreetImpact> parse(final String string) {
        LOGGER.debug("Parsing street impact");
        LOGGER.trace("Parsing street impact from string <{}>", string);

        final String[] streetData = string.split("• ");
        LOGGER.trace("Splitted string to <{}>", Arrays.toString(streetData));

        final Pattern pattern = Pattern.compile("(.*) - (.*)");
        final List<StreetImpact> streetImpacts = Arrays.stream(streetData)
                .map(pattern::matcher)
                .filter(Matcher::find)
                .map(matcher -> new StreetImpact(matcher.group(1), buildingImpactParser.parse(matcher.group(2))))
                .toList();
        LOGGER.trace("Build street impact list <{}>", streetImpacts);

        return Set.copyOf(streetImpacts);
    }

}
