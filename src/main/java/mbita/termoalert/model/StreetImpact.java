package mbita.termoalert.model;

import java.util.Collection;
import java.util.Set;

public class StreetImpact {
    private final String street;
    private final Set<BuildingImpact> buildingImpacts;

    public StreetImpact(final String street, final Collection<BuildingImpact> buildingImpacts) {
        this.street = street;
        this.buildingImpacts = Set.copyOf(buildingImpacts);
    }

    public String getStreet() {
        return street;
    }

    public Set<BuildingImpact> getBuildingImpacts() {
        return buildingImpacts;
    }

    @Override
    public String toString() {
        return "StreetImpact{" +
                "street='" + street + '\'' +
                ", buildingImpacts=" + buildingImpacts +
                '}';
    }
}
