// --== CS400 File Header Information ==--
// Name: Vivian Lacson
// Email: vlacson@wisc.edu
// Team: HA
// TA: Hang Yin
// Lecturer: Florian Heimerl
// Notes to Grader: N/A

import org.junit.Test;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.zip.DataFormatException;
import static org.junit.Assert.*;

/**
 * This method tests the implementations of the Airport and AirportDataReader classes.
 */
public class DataWranglerTests {

    /**
     * This method tests the AirportDataReader's readDataSet method, checking to see if the correct airports are
     * loaded in and if the size of the returned list is correct. This test passes if those conditions are true,
     * fails otherwise.
     */
    @Test
    public void readAirports() {
        try {
            FileReader fileReader = new FileReader("airports.csv");
            AirportDataReader dataReader = new AirportDataReader();
            List<Airport> airports;
            airports = dataReader.readDataSet(fileReader);
            assertTrue(airports.get(0).toString().contains("Albuquerque International"));
            assertTrue(airports.get(1).toString().contains("Ted Stevens Anchorage International"));
            assertEquals(341, airports.size());
        } catch (IOException | DataFormatException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method tests that the readDataSet method reads in the airport's correct information and that the
     * Airport's toString() method is formatted correctly. Test passes if those conditions are true, fails otherwise.
     */
    @Test
    public void readAirportData() {
        try {
            FileReader fileReader = new FileReader("airports.csv");
            AirportDataReader dataReader = new AirportDataReader();
            List<Airport> airports;
            airports = dataReader.readDataSet(fileReader);
            assertEquals("(ABQ)Albuquerque International | Albuquerque, NM | (35.04022222, -106.6091944)", airports.get(0).toString());
        } catch (IOException | DataFormatException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method tests the getDistance method. It tests to see if the calculated distance between two airports are
     * correct using the haversine formula. Test passes if the calculated distance is the correct one, fails otherwise.
     */
    @Test
    public void getDistance() {
        try {
            FileReader fileReader = new FileReader("airports.csv");
            AirportDataReader dataReader = new AirportDataReader();
            List<Airport> airports;
            airports = dataReader.readDataSet(fileReader);
            assertEquals(4205.9157240824625, airports.get(0).getDistance(airports.get(1)), 0.5);
        } catch (IOException | DataFormatException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method tests to see if the readDataSet method will correctly throw a DataFormatException when given
     * coordinates that are nonexistent. Test passes if a DataFormatException is thrown, fails otherwise.
     */
    @Test
    public void nonexistentCoordinates() {
        try {
            // creates a blank file
            File file = new File("test.csv");
            file.createNewFile();

            // writes airport with coordinates out of bounds
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write("TRI,Tri-Cities Regional,Bristol,TN,USA,190.47521417,-82.40742056");
            fileWriter.close();

            // creates a file reader to be read
            FileReader fileReader = new FileReader(file);
            AirportDataReader dataReader = new AirportDataReader();
            List<Airport> airports = dataReader.readDataSet(fileReader);
            assertTrue(airports.isEmpty());
        } catch (IOException | DataFormatException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method tests the readDataSet method on an empty file. It passes if there are no airports returned, fails
     * otherwise.
     */
    @Test
    public void emptyFile() {
        try {
            // creates a blank file
            File file = new File("test.csv");
            file.createNewFile();

            // creates a file reader to be read
            FileReader fileReader = new FileReader(file);
            AirportDataReader dataReader = new AirportDataReader();
            List<Airport> airports = dataReader.readDataSet(fileReader);
            assertTrue(airports.isEmpty());
        } catch (IOException | DataFormatException e) {
            e.printStackTrace();
        }
    }
}
