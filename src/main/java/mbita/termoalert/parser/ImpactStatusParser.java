package mbita.termoalert.parser;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import mbita.termoalert.model.ImpactStatus;
import mbita.termoalert.model.SectorImpact;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

@ApplicationScoped
class ImpactStatusParser implements Parser<String, ImpactStatus> {
    private static final Logger logger = LoggerFactory.getLogger(ImpactStatusParser.class);

    private final Parser<Element, Set<SectorImpact>> sectorImpactParser;

    @Inject
    public ImpactStatusParser(@Named("sectorImpactParser")
                                  final Parser<Element, Set<SectorImpact>> sectorImpactParser) {
        this.sectorImpactParser = sectorImpactParser;
    }

    public ImpactStatus parse(final String response) {
        logger.debug("Parsing impact status");

        final Document document = Jsoup.parse(response);
        logger.trace("Parsed HTML response: <{}>", document);

        final Element fullTableDivElement = document.getElementById("ST");
        logger.trace("Found full table div: <{}>", fullTableDivElement);

        final ImpactStatus impactStatus = new ImpactStatus(sectorImpactParser.parse(fullTableDivElement));
        logger.trace("Built impact status <{}>", impactStatus);

        return impactStatus;
    }
}
