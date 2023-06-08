// --== CS400 File Header Information ==--
// Name: Vivian Lacson
// Email: vlacson@wisc.edu
// Team: HA
// TA: Hang Yin
// Lecturer: Florian Heimerl
// Notes to Grader: N/A

public interface AirportInterface extends Comparable<AirportInterface> {
    public String getName();
    public String getCity();
    public String getState();
    public String getCode();
    public double getLatitude();
    public double getLongitude();
    public double getDistance(AirportInterface otherAirport);
    public String toString();
    int compareTo(AirportInterface other);
}
