// --== CS400 File Header Information ==--
// Name: Jonathan Moskal
// Email: jmoskal@wisc.edu
// Team: HA red
// Role: Backend Developer
// TA: Hang Yin
// Lecturer: Florian Heimerl
// Notes to Grader: getQuickestPath() implementation is identical to getShortestPath().
//                  The method was simplified since DW code is not set up to compare times.
//                  getNumShortestPaths() simply returns 1 as it is unused by the Frontend.
//                  fillGraphRandINTL() was developed before knowing our data set would make
//                  distinction simple for which Airport's should be INTL.

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.zip.DataFormatException;

/**
 * The Backend class implements the BackendInterface with its many methods related to managing
 * a network of Airport's and flights connecting them. Flight cost information found online.
 *
 *  @author Jonathan Moskal
 *  @see https://www.rome2rio.com/blog/2013/01/02/170779446/
 */
public class Backend implements BackendInterface {

    protected CS400Graph<Airport> flightNetwork; // graph stores all Airports and connecting flights/edges
    protected ArrayList<Airport> allAirports; //holds a list of all airports from the data set
    private final int PERCENT_INTL = 10; // the percentage of flights randomly designated as international
    private final int LOCAL_FLIGHT_RANGE = 300; //the distance regional Airport's offer flights to (in km)
    private final double MILE_PER_KM = .6214;
    private final double COST_PER_MILE = .11;
    private final int FLAT_FEE = 50; // all flights are subject to a fixed monetary fee

    /**
     * Backend constructor that takes a FileReader as a parameter. This automatically loads in the
     * data set and initializes the graph.
     *
     * @param inputFileReader a FileReader object passed in from the Frontend that is used to read
     *                        in the data set
     */
    public Backend(FileReader inputFileReader) {
        allAirports = null;
        flightNetwork = new CS400Graph<Airport>();
        try {
            allAirports = new AirportDataReader().readDataSet(inputFileReader);
        }

        catch (IOException | DataFormatException e) {
            System.out.println("Airport Data Could Not Be Read!");
            e.printStackTrace();
        }

        fillGraph();
    }

    /**
     * This Backend constructor with no arguments should be used only for testing
     * so that a list of Airports can be hard-coded.
     */
    protected Backend() {
        allAirports = new ArrayList<Airport>();
        flightNetwork = new CS400Graph<Airport>();
    }

    /**
     * Initializes the graph structure of Airports and connecting flight edges.
     *
     * Regional Airport's connect to all other Airport's within 300km.
     * Non-international Airport's are by default regional.
     *
     * International Airport's additionally connect to all other international Airport's.
     * International status queried by name containing "International"
     */
    protected void fillGraph() {
        // create graph vertices (one per Airport)
        for (Airport airport : allAirports) {
            flightNetwork.insertVertex(airport);
        }

        // create graph edges
        // stores all Airport's that should be connected to the "current" one (the one from the "i"
        // loop)
        HashSet<Airport> connectingAirports = new HashSet<Airport>();
        // will store the two Airport's that might be connected in order to limit costly ArrayList .get() methods
        Airport current = null;
        Airport secondary = null;
        for (int i = 0; i < getNumAirports(); i++) { // assign all Airport's their flight offerings
            // (initialize graph edges)
            connectingAirports.clear();
            for (int j = 0; j < getNumAirports(); j++) { // check against all other flights and check
                // whether a connecting flight should be made
                if (i == j) { // an Airport should not get connected to itself
                    continue;
                }
                current = allAirports.get(i);
                secondary = allAirports.get(j);
                // add regional flight if applicable
                if (current.getDistance(secondary) <= 300) {
                    connectingAirports.add(secondary);
                }

                // add INTL flight if applicable
                if (current.toString().contains("International") && secondary.toString().contains("International")) {
                    connectingAirports.add(secondary);
                }
            }

            // add edges from the current Airport to all the Airport's in the set
            for (Airport destination : connectingAirports) {
                flightNetwork.insertEdge(current, destination, (int) Math.round(current.getDistance(destination)));
            }
        }
    }

    /**
     * Initializes the graph structure of Airports and connecting flight edges.
     *
     * Regional Airport's connect to all other Airport's within the local flight range.
     * Non-international Airport's are by default regional.
     *
     * International Airport's additionally connect to all other international Airport's.
     * International status randomly granted to a set percentage of the data set.
     *
     * @throws IllegalArgumentException if PERCENT_INTL is configured outside the range [0-100]
     */
    @SuppressWarnings("unused") // ignore "dead code" warnings for PERCENT_INTL value checks
    private void fillGraphRandINTL() throws IllegalArgumentException {
        // seeded for easier testing/debugging... it also makes more sense to be semi-constant
        Random randGen = new Random(100); // used for randomly assigning international status

        // create graph vertices (one per Airport)
        for(Airport airport : allAirports) {
            flightNetwork.insertVertex(airport);
        }

        if (PERCENT_INTL < 0 || PERCENT_INTL > 100) { // create graph edges
            throw new IllegalArgumentException(
                    "PERCENT_INTL field set to invalid value: " + PERCENT_INTL + ". Should be [0-100]");
        }

        else if (PERCENT_INTL != 0) { // there may be some international airports
            // set international flags
            // parallel array for which Airport's are designated international
            Boolean[] intlMarkers = new Boolean[getNumAirports()];
            int airportIndex; // the index in the intlMarkers[] to mark as international
            for (int i = 0; i < getNumAirports() * (PERCENT_INTL / 100); i++) { // for set percent of Airport's
                // while an international Airport is randomly chosen (so that PERCENT_INTL is guaranteed and
                // no Airport's are marked as international twice)
                while (true) {
                    airportIndex = randGen.nextInt(getNumAirports());
                    if (intlMarkers[airportIndex] == true) {
                        continue;
                    }
                    intlMarkers[airportIndex] = true; // make it international
                    break;
                }
            }

            // stores all Airport's that should be connected to the "current" one (the one from the "i" loop)
            HashSet<Airport> connectingAirports = new HashSet<Airport>();
            // will store the two Airport's that might be connected in order to limit costly ArrayList .get() methods
            Airport current = null;
            Airport secondary = null;
            for (int i = 0; i < getNumAirports(); i++) { // assign all Airport's their flight offerings (initialize graph edges)
                connectingAirports.clear();
                for (int j = 0; j < getNumAirports(); j++) { // check against all other flights and check whether a connecting flight should be made
                    if (i == j) { // an Airport should not get connected to itself
                        continue;
                    }
                    current = allAirports.get(i);
                    secondary = allAirports.get(j);
                    // add regional flight if applicable
                    if (current.getDistance(secondary) <= LOCAL_FLIGHT_RANGE) {
                        connectingAirports.add(secondary);
                    }

                    if (intlMarkers[i] && intlMarkers[j] == true) { // add INTL flight if applicable
                        connectingAirports.add(secondary);
                    }
                }

                // add edges from the current Airport to all the Airport's in the set
                for(Airport destination : connectingAirports) {
                    flightNetwork.insertEdge(current, destination, (int)Math.round(current.getDistance(destination)));
                }
            }
        }

        else { // no international Airport's in use
            // stores all Airport's that should be connected to the "current" one (the one from the "i" loop)
            HashSet<Airport> connectingAirports = new HashSet<Airport>();
            // will store the two Airport's that might be connected in order to limit costly ArrayList .get() methods
            Airport current = null;
            Airport secondary = null;
            for (int i = 0; i < getNumAirports(); i++) { // assign all Airport's their flight offerings (initialize graph edges)
                connectingAirports.clear();
                for (int j = 0; j < getNumAirports(); j++) { // check against all other flights and check whether a connecting flight should be made
                    current = allAirports.get(i);
                    secondary = allAirports.get(j);
                    // add regional flight if applicable
                    if (current.getDistance(secondary) <= LOCAL_FLIGHT_RANGE) {
                        connectingAirports.add(secondary);
                    }
                }

                // add edges from the current Airport to all the Airport's in the set
                for(Airport destination : connectingAirports) {
                    flightNetwork.insertEdge(current, destination, (int)Math.round(current.getDistance(destination)));
                }
            }
        }
    }

    /**
     * [TEST VERSION] Initializes the graph structure of Airports and connecting flight edges.
     *
     * Regional Airport's connect to all other Airport's within 300km.
     * Non-international Airport's are by default regional.
     *
     * International Airport's additionally connect to all other international Airport's.
     * International status set manually.
     */
    protected void fillGraphTEST() {
        // create graph vertices (one per Airport)
        for (Airport airport : allAirports) {
            flightNetwork.insertVertex(airport);
        }

        // create graph edges
        // set international flags
        // parallel array for which Airport's are designated international
        Boolean[] intlMarkers = new Boolean[] {false, true, false, true, false, false, false};

        // stores all Airport's that should be connected to the "current" one (the one from the "i"
        // loop)
        HashSet<Airport> connectingAirports = new HashSet<Airport>();
        // will store the two Airport's that might be connected in order to limit costly ArrayList .get() methods
        Airport current = null;
        Airport secondary = null;
        for (int i = 0; i < getNumAirports(); i++) { // assign all Airport's their flight offerings
            // (initialize graph edges)
            connectingAirports.clear();
            for (int j = 0; j < getNumAirports(); j++) { // check against all other flights and check
                // whether a connecting flight should be made
                if (i == j) { // an Airport should not get connected to itself
                    continue;
                }
                current = allAirports.get(i);
                secondary = allAirports.get(j);
                // add regional flight if applicable
                if (current.getDistance(secondary) <= 300) {
                    connectingAirports.add(secondary);
                }

                if (intlMarkers[i] && intlMarkers[j] == true) { // add INTL flight if applicable
                    connectingAirports.add(secondary);
                }
            }

            // add edges from the current Airport to all the Airport's in the set
            for (Airport destination : connectingAirports) {
                flightNetwork.insertEdge(current, destination, (int) Math.round(current.getDistance(destination)));
            }
        }
    }

    /**
     * Calculates and returns a shortest path between two Airport's by distance.
     *
     * @param startPoint the desired starting Airport
     * @param endPoint the desired ending Airport
     * @return the shortest path between two Airports (by distance), or an empty list if there is no path
     */
    public ArrayList<Airport> getShortestPath(Airport startPoint, Airport endPoint) {
        // get shortest path
        ArrayList<Airport> flightList = new ArrayList<Airport>();
        CS400Graph<Airport>.Path path;
        try {
            path = flightNetwork.dijkstrasShortestPath(startPoint, endPoint);
        }

        catch(NoSuchElementException NSEE) { // no such path between Airport's--return empty list
            return flightList;
        }


        for(Airport airport : path.dataSequence) { // convert to ArrayList, as specified in BackendInterface
            flightList.add(airport);
        }

        return flightList;
    }

    /**
     * Calculates and returns a shortest path between two Airport's by time.
     * Note this will be the same flight path as getShortestPath().
     *
     * @param startPoint the desired starting Airport
     * @param endPoint the desired ending Airport
     * @return the shortest path between two Airports (by time), or an empty list if there is no path
     */
    public ArrayList<Airport> getQuickestPath(Airport startPoint, Airport endPoint) {
        // get quickest path
        ArrayList<Airport> flightList = new ArrayList<Airport>();
        CS400Graph<Airport>.Path path;
        try {
            path = flightNetwork.dijkstrasShortestPath(startPoint, endPoint);
        }

        catch(NoSuchElementException NSEE) { // no such path between Airport's--return empty list
            return flightList;
        }


        for(Airport airport : path.dataSequence) { // convert to ArrayList, as specified in BackendInterface
            flightList.add(airport);
        }

        return flightList;
    }

    /**
     * Calculates the cost of flying a given number of kilometers.
     * Note that fractional cents are truncated
     *
     * @param distance the distance that will be flown in kilometers
     * @return the cost of travelling a given distance in US dollars
     */
    @Override
    public double getCost(double distance) {
        double numMiles = distance * MILE_PER_KM; // convert from kilometers to miles
        double price = FLAT_FEE + numMiles * COST_PER_MILE; // get the price
        return ((int)(price * 100)) / 100.0; // ensure we only have down to hundreds place for cents
    }

    /**
     * Calculates the cost of flying between two given Airport's.
     * Note this method was not defined in the shared BackendInterface,
     * but is added for robustness.
     * Also note that fractional cents are truncated
     *
     * @param startPoint the desired starting Airport
     * @param endPoint the desired ending Airport
     * @return the cost of travelling between two Airport's in US dollars
     */
    public double getFlightCost(Airport startPoint, Airport endPoint) {
        double distance = 0; // the total distance between two airports, as calculated by the shortest
        // flight path instead of geographically

        // get the shortest flight path
        ArrayList<Airport> flightPath = getShortestPath(startPoint, endPoint);

        // for every airport (that has a next Airport) on the flight path
        for(int i = 0; i < flightPath.size() - 1; i++) {
            distance += flightPath.get(i).getDistance(flightPath.get(i + 1));
        }

        double numMiles = distance * MILE_PER_KM; // convert from kilometers to miles
        double price = FLAT_FEE + numMiles * COST_PER_MILE; // get the price
        return ((int)(price * 100)) / 100.0; // ensure we only have down to hundreds place for cents
    }

    /**
     * Gets the number of flight paths that tie for shortest path from startPoint to endPoint.
     * Note this method remains unimplemented as it is unused by the Frontend.
     *
     * @param startPoint the desired starting Airport
     * @param endPoint the desired ending Airport
     * @return the number of such paths that could be taken
     */
    public int getNumShortestPaths(Airport startPoint, Airport endPoint) {
        // get number of equal length flights
        return 1; // In most/all cases, there will be no ties in our data set. If there were to be one,
        // a single shortest path is acceptable
    }

    /**
     * Accessor method for the list of all Airports.
     *
     * @return the list of all Airport's from the data set
     */
    public ArrayList<Airport> getAllAirports() {
        return allAirports;
    }

    /**
     * Gives the number of Airport objects that were loaded in from the data set.
     *
     * @return the number of Airports in the data set
     */
    @Override
    public int getNumAirports() {
        return allAirports.size();
    }

}

