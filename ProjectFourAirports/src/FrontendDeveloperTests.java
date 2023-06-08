// --== CS400 File Header Information ==--
// Name: Ryan Follis
// Email: rfollis@wisc.edu
// Team: Red Team
// Group: HA
// TA: Hang Yin
// Lecturer: Florian Heimerl
// Notes to Grader: <optional extra notes>

import org.junit.Test;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.zip.DataFormatException;

import static org.junit.Assert.*;

/**
 * This class tests some of the main functionalities of the Frontend file in our airline app.
 * @author Ryan Follis
 *
 */
public class FrontendDeveloperTests {

    /**
     * This method tests to make sure the proper airports are part of the shortest path
     * between MKE and JFK airports. The airports included are supposed to be General Mitchell
     * International, Lehigh Valley International, and John F Kennedy Intl.
     */
    @Test
    public void testShortestMethod() {
        InputStream systemIn = System.in;
        PrintStream systemOut = System.out;
        
        String input = "shortest MKE JFK exit";
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        Frontend frontend = new Frontend();
        try {
            frontend.run();
        } catch (DataFormatException e) {
            e.printStackTrace();
        }
        System.setIn(systemIn);
        System.setOut(systemOut);
        String output = outputStream.toString();
        
        if(!output.contains("General Mitchell International") || !output.contains("Lehigh Valley "
                + "International") || !output.contains("John F Kennedy Intl")) {
            fail("The shortest() method did not return the expected output when run.");
        }
    }
    
    /**
     * This method tests to make sure the proper price is output for the journey between
     * MKE and JFK airports. The price should be $132.20.
     */
    @Test
    public void testPriceMethod() {
        InputStream systemIn = System.in;
        PrintStream systemOut = System.out;
        
        String input = "price MKE JFK exit";
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        Frontend frontend = new Frontend();
        try {
            frontend.run();
        } catch (DataFormatException e) {
            e.printStackTrace();
        }
        System.setIn(systemIn);
        System.setOut(systemOut);
        String output = outputStream.toString();
        
        if(!output.contains("The price of a flight between the two provided airports is: $132.20")) {
            fail("The price() method did not return the expected output when run.");
        }
    }
    
    /**
     * This method tests to make sure the display method scrolls properly. After scrolling down
     * three times from the initial ten airports listed, the bottom three airports to appear on
     * screen should be Chicago Midway, Memphis International, and Miami International.
     */
    @Test
    public void testDisplayMethod() {
        InputStream systemIn = System.in;
        PrintStream systemOut = System.out;
        
        String input = "display d d d exit exit";
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        Frontend frontend = new Frontend();
        try {
            frontend.run();
        } catch (DataFormatException e) {
            e.printStackTrace();
        }
        System.setIn(systemIn);
        System.setOut(systemOut);
        String output = outputStream.toString();
        
        if(!output.contains("Chicago Midway") || !output.contains("Memphis International")
                || !output.contains("Miami International")) {
            fail("The display() method did not return the expected output when run.");
        }
    }
    
}








