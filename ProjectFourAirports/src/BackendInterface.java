// --== CS400 File Header Information ==--
// Name: Jonathan Moskal
// Email: jmoskal@wisc.edu
// Team: HA red
// Role: Backend Developer
// TA: Hang Yin
// Lecturer: Florian Heimerl
// Notes to Grader: None

import java.util.ArrayList;

//For use in the Flight Mapper Project
public interface BackendInterface {

    // returns a list with each element representing the (shortest distance) path from start to destination
    public ArrayList<Airport> getShortestPath(Airport startPoint, Airport endPoint);

    // returns a list with each element representing the (quickest time) path from start to destination
    public ArrayList<Airport> getQuickestPath(Airport startPoint, Airport endPoint);

    // returns the cost in US$ of flying to particular destination based off of the distance it is away
    public double getCost(double distance);

    // returns the number of shortest paths from start to finish (distance wise)
    // note this will often be 1, but can be more
    public int getNumShortestPaths(Airport startPoint, Airport endPoint);

    // returns a list of all Airport objects in the dataset
    public ArrayList<Airport> getAllAirports();

    // returns the number of airports in the dataset
    public int getNumAirports();

}
