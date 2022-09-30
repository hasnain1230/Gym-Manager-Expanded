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

    public String getCounty() {
        return this.COUNTY;
    }

    public String getTown() {
        return this.TOWN;
    }

    public String getPostalCode() {
        return this.POSTALCODE;
    }

    @Override
    public String toString() {
        return String.format("%s, %s, %s", this.TOWN, this.POSTALCODE, this.COUNTY);
    }
}
