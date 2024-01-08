package mbita.termoalert.model;

public class BuildingImpact {
    private final String building;

    public BuildingImpact(final String building) {
        this.building = building;
    }

    public String getBuilding() {
        return building;
    }

    @Override
    public String toString() {
        return "BuildingImpact{" +
                "building='" + building + '\'' +
                '}';
    }
}
