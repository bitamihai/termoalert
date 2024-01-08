package mbita.termoalert.model;

import java.util.Collection;
import java.util.Set;

public class SectorImpact {
    private final int sector;
    private final Set<ThermalNodeImpact> thermalNodeImpacts;
    private final String impact;
    private final String description;
    private final String eta;

    public SectorImpact(final int sector,
                        final Collection<ThermalNodeImpact> thermalNodeImpacts,
                        final String impact,
                        final String description,
                        final String eta) {
        this.sector = sector;
        this.thermalNodeImpacts = Set.copyOf(thermalNodeImpacts);
        this.impact = impact;
        this.description = description;
        this.eta = eta;
    }

    public int getSector() {
        return sector;
    }

    public Set<ThermalNodeImpact> getThermalNodeImpacts() {
        return thermalNodeImpacts;
    }

    public String getImpact() {
        return impact;
    }

    public String getDescription() {
        return description;
    }

    public String getEta() {
        return eta;
    }

    @Override
    public String toString() {
        return "SectorImpact{" +
                "sector=" + sector +
                ", thermalNodeImpacts=" + thermalNodeImpacts +
                ", impact='" + impact + '\'' +
                ", description='" + description + '\'' +
                ", eta='" + eta + '\'' +
                '}';
    }
}
