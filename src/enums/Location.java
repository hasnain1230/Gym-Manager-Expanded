package enums;

public enum Location {
    BRIDGEWATER("Bridgewater", "08837", "Somerset County"),
    EDISON("Edison", "08837", "Middlesex County"),
    FRANKLIN("Franklin", "08873", "Somerset County"),
    PISCATAWAY("Piscataway", "08854", "Middlesex County"),
    SOMERVILLE("Somerville", "08876", "Somerset County");

    private final String TOWN;
    private final String POSTALCODE;
    private final String COUNTY;
    
    private Location(String town, String postalCode, String county) {
        this.TOWN = town;
        this.POSTALCODE = postalCode;
        this.COUNTY = county;
    }

    public static Location returnEnumFromString(String location) {
        if (location.equalsIgnoreCase("bridgewater")) {
            return Location.BRIDGEWATER;
        } else if (location.equalsIgnoreCase("edison")) {
            return Location.EDISON;
        } else if (location.equalsIgnoreCase("franklin")) {
            return Location.FRANKLIN;
        } else if (location.equalsIgnoreCase("piscataway")) {
            return Location.PISCATAWAY;
        } else if (location.equalsIgnoreCase("somerville")) {
            return Location.SOMERVILLE;
        } else {
            return null;
        }
    }

    public final String getCounty() {
        return this.COUNTY;
    }

    public final String getTown() {
        return this.TOWN;
    }

    public final String getPostalCode() {
        return this.POSTALCODE;
    }

    @Override
    public final String toString() {
        return String.format("%s, %s, %s", this.TOWN, this.POSTALCODE, this.COUNTY);
    }
}
