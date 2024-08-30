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

    private static final Logger LOGGER = LoggerFactory.getLogger(BuildingImpactParser.class);

    public Set<BuildingImpact> parse(final String string) {
        LOGGER.debug("Parsing building impact");
        LOGGER.trace("Parsing building impact from string <{}>", string);

        final String trimmedString = string.trim()
                .replaceAll("\\s*bl\\.\\s*", "")
                .replaceAll("\\s*[,|;]\\s*", ",");
        LOGGER.trace("Trimmed string to <{}>", trimmedString);

        final String[] buildingData = trimmedString.split(",");
        LOGGER.trace("Splitted string to <{}>", Arrays.toString(buildingData));

        final List<BuildingImpact> buildingImpactList = Arrays.stream(buildingData)
                .filter(s -> !s.isEmpty())
                .map(BuildingImpact::new)
                .toList();
        LOGGER.trace("Built building impact list <{}>", buildingImpactList);

        return Set.copyOf(buildingImpactList);
    }

}
