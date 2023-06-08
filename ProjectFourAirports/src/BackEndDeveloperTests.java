// --== CS400 File Header Information ==--
// Name: Jonathan Moskal
// Email: jmoskal@wisc.edu
// Team: HA red
// Role: Backend Developer
// TA: Hang Yin
// Lecturer: Florian Heimerl
// Notes to Grader: None

import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * This test class provides three test methods for checking the functionality of the Backend class
 * for the CS400 Project 3.
 * 
 * @author Jonathan Moskal
 */
public class BackEndDeveloperTests {

  Backend testBackend;
  Airport airport1;
  Airport airport2;
  Airport airport3;
  Airport airport4;
  Airport airport5;
  Airport airport6;
  Airport airport7;
  
  /**
   * Instantiate test Backend with predetermined 7 Airport's
   */
  @BeforeEach
  public void createBackend() {
    testBackend = new Backend();
    
    airport1 = new Airport("BEL", "Airport1", "Bellingham", "WA", 48.7519, 122.4787);
    airport2 = new Airport("SEA", "Airport2", "Seattle", "WA", 47.6062, 122.3321);
    airport3 = new Airport("NEEN", "Airport3", "Neenah", "WI", 44.1858, 88.4626);
    airport4 = new Airport("MAD", "Airport4", "Madison", "WI", 43.0731, 89.4012);
    airport5 = new Airport("MKE", "Airport5", "Milwaukee", "WI", 43.0389, 87.9065);
    airport6 = new Airport("CHI", "Airport6", "Chicago", "IL", 41.8781, 87.6298);
    airport7 = new Airport("LDN", "Airport7", "London", "UK", 51.5074, .1278);
    
    testBackend.allAirports.add(airport1);
    testBackend.allAirports.add(airport2); //international
    testBackend.allAirports.add(airport3);
    testBackend.allAirports.add(airport4); //international
    testBackend.allAirports.add(airport5);
    testBackend.allAirports.add(airport6);
    testBackend.allAirports.add(airport7);
    
    testBackend.fillGraphTEST();
  }
  
  /**
   * Tests that vertices and edges of the flight network are set up as expected
   * (that is, test the fillGraph() method). 
   */
  @Test
  public void testFillGraph() {
    // check all vertices
    assertTrue(testBackend.flightNetwork.vertices.size() == 7); // should be 7 vertices, one per Airport
    for(Airport airport : testBackend.allAirports) { // each Airport should be a vertex
      assertTrue(testBackend.flightNetwork.vertices.containsKey(airport));
    }
    
    // check all edges
    // regional checks
    assertTrue(testBackend.flightNetwork.containsEdge(airport1, airport2)); //Bellingham->Seattle
    assertTrue(testBackend.flightNetwork.containsEdge(airport2, airport1)); //Seattle->Bellingham
    assertTrue(testBackend.flightNetwork.containsEdge(airport3, airport4)); //Neenah>Madison
    assertTrue(testBackend.flightNetwork.containsEdge(airport4, airport3)); //Madison->Neenah
    assertTrue(testBackend.flightNetwork.containsEdge(airport4, airport3)); //Neenah->Milwaukee
    assertTrue(testBackend.flightNetwork.containsEdge(airport5, airport3)); //Milwaukee->Neenah
    assertTrue(testBackend.flightNetwork.containsEdge(airport4, airport5)); //Madison->Milwaukee
    assertTrue(testBackend.flightNetwork.containsEdge(airport5, airport4)); //Milwaukee->Madison
    assertTrue(testBackend.flightNetwork.containsEdge(airport3, airport6)); //Neenah->Chicago
    assertTrue(testBackend.flightNetwork.containsEdge(airport6, airport3)); //Chicago->Neenah
    assertTrue(testBackend.flightNetwork.containsEdge(airport4, airport6)); //Madison->Chicago
    assertTrue(testBackend.flightNetwork.containsEdge(airport6, airport4)); //Chicago->Madison
    assertTrue(testBackend.flightNetwork.containsEdge(airport5, airport6)); //Milwaukee->Chicago
    assertTrue(testBackend.flightNetwork.containsEdge(airport6, airport5)); //Chicago->Milwaukee
    
    // international checks
    assertTrue(testBackend.flightNetwork.containsEdge(airport2, airport4)); //Seattle->Madison
    assertTrue(testBackend.flightNetwork.containsEdge(airport4, airport2)); //Madison->Seattle
    
    // make sure nothing connects to London (airport7)
    for(Airport airport : testBackend.allAirports) {
      assertTrue(!testBackend.flightNetwork.containsEdge(airport, airport7));
    }
  }
  
  /**
   * Tests the getShortestPath() (and therefore getQuickestPath()) methods
   */
  @Test
  public void testShortestPath() {
    ArrayList<Airport> shortestPath;
    shortestPath = testBackend.getShortestPath(airport3, airport5); //Neenah->Milwaukee
    assertTrue(shortestPath.size() == 2);
    assertTrue(shortestPath.get(0).equals(airport3));
    assertTrue(shortestPath.get(1).equals(airport5));
      
    shortestPath = testBackend.getShortestPath(airport2, airport4); //Seattle->Madison
    assertTrue(shortestPath.size() == 2);
    assertTrue(shortestPath.get(0).equals(airport2));
    assertTrue(shortestPath.get(1).equals(airport4));
      
    shortestPath = testBackend.getShortestPath(airport6, airport1); //Chicago->Madison->Seattle->Bellingham
    assertTrue(shortestPath.size() == 4);
    assertTrue(shortestPath.get(0).equals(airport6));
    assertTrue(shortestPath.get(1).equals(airport4));
    assertTrue(shortestPath.get(2).equals(airport2));
    assertTrue(shortestPath.get(3).equals(airport1));

    // should be no shortest path to/from London
    shortestPath = testBackend.getShortestPath(airport4, airport7); //Madison-xLondon
    assertTrue(shortestPath.size() == 0);
    shortestPath = testBackend.getShortestPath(airport7, airport5); //London-xMilwaukee
    assertTrue(shortestPath.size() == 0);
  }
  
  /**
   * Tests both the getCost() and getFlightCost() methods
   * 
   * Note: the following fields in Backend.java should be set as follows for this test method
   * MILE_PER_KM = .6214
   * COST_PER_MILE = .11
   * FLAT_FEE = 50
   */
  @Test
  public void testCosts() {
    // test getCost() (Neenah->Bellingham)
    // cost = 2631.1914298743977 * .6214 * .11 + 50 = 229.85245899763456 -> $229.85
    assertTrue(testBackend.getCost(airport3.getDistance(airport1)) == 229.85);
    
    // test getFlightCost() (Neenah->Bellingham) [(Neenah->Madison), (Madison->Seattle), (Seattle->Bellingham)]
    // cost = (144.96307370410722 + 2602.211947048801 + 127.85882681043742) * 
    //        .6214 * .11 + 50 = 246.52006361634488 -> $246.52 
    System.out.println(testBackend.getFlightCost(airport3, airport1));
    assertTrue(testBackend.getFlightCost(airport3, airport1) == 246.52);
  }
}
