package mbita.termoalert.parser;

import jakarta.enterprise.context.ApplicationScoped;
import mbita.termoalert.model.BuildingImpact;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

@ApplicationScoped
class BuildingImpactParser implements Parser<String, Set<BuildingImpact>> {
    private static final Logger logger = LoggerFactory.getLogger(BuildingImpactParser.class);

    public Set<BuildingImpact> parse(final String string) {
        logger.debug("Parsing building impact");
        logger.trace("Parsing building impact from string <{}>", string);

        final String trimmedString = string.trim()
                .replaceAll("\\s*bl\\.\\s*", "")
                .replaceAll("\\s*[,|;]\\s*", ",");
        logger.trace("Trimmed string to <{}>", trimmedString);

        final String[] buildingData = trimmedString.split(",");
        logger.trace("Splitted string to <{}>", Arrays.toString(buildingData));

        final List<BuildingImpact> buildingImpactList = Arrays.stream(buildingData)
                .filter(s -> !s.isEmpty())
                .map(BuildingImpact::new)
                .toList();
        logger.trace("Built building impact list <{}>", buildingImpactList);

        return Set.copyOf(buildingImpactList);
    }
}
