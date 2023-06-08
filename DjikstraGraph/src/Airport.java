// --== CS400 File Header Information ==--
// Name: Vivian Lacson
// Email: vlacson@wisc.edu
// Team: HA
// TA: Hang Yin
// Lecturer: Florian Heimerl
// Notes to Grader: N/A

/**
 * This Airport class stores the airport's information regarding its name and location.
 */
public class Airport implements AirportInterface {
    private String name; // name of the airport
    private String city; // city the airport is located in
    private String state; // state the airport is located in
    private double latitude; // coordinate of the airport
    private double longitude; // coordinate of the airport
    private static final int EARTH_RADIUS = 6371; // radius of the earth in km

    /**
     * Creates an Airport instance with the given information.
     *
     * @param name of the airport
     * @param city the airport is located in
     * @param state the airport is located in
     * @param latitude of the airport's location
     * @param longitude of the airport's location
     */
    public Airport(String name, String city, String state, double latitude, double longitude) {
        this.name = name;
        this.city = city;
        this.state = state;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    /**
     * Returns the name of the airport.
     * @return the name of the airport.
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Returns the city the airport is located in.
     * @return the city the airport is located in.
     */
    @Override
    public String getCity() {
        return city;
    }

    /**
     * Returns the state the airport is located in.
     * @return the state the airport is located in.
     */
    @Override
    public String getState() {
        return state;
    }

    /**
     * Returns the latitude the airport is located at.
     * @return the latitude the airport is located at.
     */
    @Override
    public double getLatitude() {
        return latitude;
    }

    /**
     * Returns the longitude the airport is located at.
     * @return the longitude the airport is located at.
     */
    @Override
    public double getLongitude() {
        return longitude;
    }

    /**
     * Returns the calculated distance between this airport and another airport using the longitude and latitude
     * values in km using the haversine formula.
     * @param otherAirport whose distance between this airport will be calculated
     * @return the distance between the two airports in km
     */
    @Override
    public double getDistance(AirportInterface otherAirport) {
        // difference of latitudes and longitudes
        double latDifference = Math.toRadians(otherAirport.getLatitude() - latitude);
        double longDifference = Math.toRadians(otherAirport.getLongitude() - longitude);

        // haversine formula
        double a =
                Math.sin(latDifference / 2) * Math.sin(latDifference / 2) + Math.sin(longDifference / 2) * Math.sin(longDifference / 2) * Math.cos(Math.toRadians(latitude)) * Math.cos(Math.toRadians(otherAirport.getLatitude()));
        double c = 2 * Math.asin(Math.sqrt(a));

        // multiply radius of earth with c
        return EARTH_RADIUS * c;
    }

    /**
     * Displays airport's information in a formatted String.
     * @return airport information in a formatted String.
     */
    @Override
    public String toString() {
        return name + " | " + city + ", " + state + " | (" + latitude + ", " + longitude + ")";
    }

	@Override
	public int compareTo(AirportInterface other) {
		return name.compareTo(other.getName());
	}

}
