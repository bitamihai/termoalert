package mbita.termoalert.model;

import java.util.Collection;
import java.util.Set;

public class ThermalNodeImpact {
    private final String thermalNode;
    private final Set<StreetImpact> streetImpacts;

    public ThermalNodeImpact(final String thermalNode, final Collection<StreetImpact> streetImpacts) {
        this.thermalNode = thermalNode;
        this.streetImpacts = Set.copyOf(streetImpacts);
    }

    public String getThermalNode() {
        return thermalNode;
    }

    public Set<StreetImpact> getStreetImpacts() {
        return streetImpacts;
    }

    @Override
    public String toString() {
        return "ThermalNodeImpact{" +
                "thermalNode='" + thermalNode + '\'' +
                ", streetImpacts=" + streetImpacts +
                '}';
    }
}
