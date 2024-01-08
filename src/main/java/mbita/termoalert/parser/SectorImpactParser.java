package mbita.termoalert.parser;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import mbita.termoalert.model.SectorImpact;
import mbita.termoalert.model.ThermalNodeImpact;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Set;

@ApplicationScoped
class SectorImpactParser implements Parser<Element, Set<SectorImpact>> {
    private static final Logger logger = LoggerFactory.getLogger(SectorImpactParser.class);

    private final Parser<Element, Set<ThermalNodeImpact>> thermalNodeImpactParser;

    @Inject
    public SectorImpactParser(@Named("thermalNodeImpactParser")
                                  final Parser<Element, Set<ThermalNodeImpact>> thermalNodeImpactParser) {
        this.thermalNodeImpactParser = thermalNodeImpactParser;
    }

    public Set<SectorImpact> parse(final Element element) {
        logger.debug("Parsing sector impact");
        logger.trace("Parsing sector impact from element <{}>", element);

        final Elements sectorElements = element.getElementsByTag("tr");
        logger.trace("Found {} elements:", sectorElements.stream().count());
        sectorElements.forEach(e -> logger.trace("Found sector element: <{}>", e));

        final Element headerElement = sectorElements.remove(0);
        logger.trace("Removed header element: <{}>", headerElement);

        final List<SectorImpact> sectorImpacts = sectorElements.stream()
                .map(this::parseIndividualSectorData)
                .toList();

        return Set.copyOf(sectorImpacts);
    }

    private SectorImpact parseIndividualSectorData(final Element element) {
        logger.debug("Parsing individual sector impact");
        logger.trace("Parsing individual sector impact from element <{}>", element);

        final Elements sectorImpactData = element.getElementsByTag("td");
        logger.trace("Splitted table elements to <{}>", sectorImpactData);

        final int sector = Integer.parseInt(sectorImpactData.get(0).text());
        final Set<ThermalNodeImpact> thermalNodeImpacts = thermalNodeImpactParser.parse(sectorImpactData.get(1));
        final String impact = sectorImpactData.get(2).text();
        final String description = sectorImpactData.get(3).text();
        final String eta = sectorImpactData.get(4).text();
        final SectorImpact sectorImpact = new SectorImpact(sector, thermalNodeImpacts, impact, description, eta);
        logger.trace("Built sector impact <{}>", sectorImpact);

        return sectorImpact;
    }
}
