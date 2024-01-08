package mbita.termoalert.parser;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import mbita.termoalert.model.*;
import org.jsoup.nodes.Element;

import java.util.Set;

@ApplicationScoped
public class ParserFactory {
    private final BuildingImpactParser buildingImpactParser;
    private final ImpactStatusParser impactStatusParser;
    private final SectorImpactParser sectorImpactParser;
    private final StreetImpactParser streetImpactParser;
    private final ThermalNodeImpactParser thermalNodeImpactParser;

    @Inject
    public ParserFactory(final BuildingImpactParser buildingImpactParser,
                         final ImpactStatusParser impactStatusParser,
                         final SectorImpactParser sectorImpactParser,
                         final StreetImpactParser streetImpactParser,
                         final ThermalNodeImpactParser thermalNodeImpactParser) {
        this.buildingImpactParser = buildingImpactParser;
        this.impactStatusParser = impactStatusParser;
        this.sectorImpactParser = sectorImpactParser;
        this.streetImpactParser = streetImpactParser;
        this.thermalNodeImpactParser = thermalNodeImpactParser;
    }

    @Produces
    @Named("buildingImpactParser")
    public Parser<String, Set<BuildingImpact>> getBuildingImpactParser() {
        return buildingImpactParser;
    }

    @Produces
    @Named("impactStatusParser")
    public Parser<String, ImpactStatus> getImpactStatusParser() {
        return impactStatusParser;
    }

    @Produces
    @Named("sectorImpactParser")
    public Parser<Element, Set<SectorImpact>> getSectorImpactParser() {
        return sectorImpactParser;
    }

    @Produces
    @Named("streetImpactParser")
    public Parser<String, Set<StreetImpact>> getStreetImpactParser() {
        return streetImpactParser;
    }

    @Produces
    @Named("thermalNodeImpactParser")
    public Parser<Element, Set<ThermalNodeImpact>> getThermalNodeImpactParser() {
        return thermalNodeImpactParser;
    }
}
