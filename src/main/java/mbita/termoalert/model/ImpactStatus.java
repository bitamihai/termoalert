package mbita.termoalert.model;

import java.util.Collection;
import java.util.Set;

public class ImpactStatus {
    private final Set<SectorImpact> sectorImpacts;
    private final long timestamp;

    public ImpactStatus(final Collection<SectorImpact> sectorImpacts) {
        this.sectorImpacts = Set.copyOf(sectorImpacts);
        this.timestamp = System.currentTimeMillis();
    }

    public Set<SectorImpact> getSectorImpacts() {
        return sectorImpacts;
    }

    public long getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return "ImpactStatus{" +
                "sectorImpacts=" + sectorImpacts +
                ", timestamp=" + timestamp +
                '}';
    }
}
