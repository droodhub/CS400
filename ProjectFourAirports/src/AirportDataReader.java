// --== CS400 File Header Information ==--
// Name: Vivian Lacson
// Email: vlacson@wisc.edu
// Team: HA
// TA: Hang Yin
// Lecturer: Florian Heimerl
// Notes to Grader: N/A

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.zip.DataFormatException;

/**
 * This AirportDataReader class parses the given file with list of airports and returns the list of Airport instances.
 */
public class AirportDataReader implements AirportDataReaderInterface {

    /**
     * Parses the given file with list of airports and returns the list of airport instances.
     * @param inputFileReader the given file with list of airports
     * @return list of airport instances
     * @throws IOException when there is an error reading in the data
     * @throws DataFormatException when the given coordinates do not exist
     */
    @Override
    public ArrayList<Airport> readDataSet(FileReader inputFileReader) throws IOException, DataFormatException {
        ArrayList<Airport> airports = new ArrayList<>();
        BufferedReader reader = new BufferedReader(inputFileReader);
        String line = reader.readLine(); // skips past the first commented line
        while ((line = reader.readLine()) != null) {
            String[] values = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
            // if latitude and/or longitude points are out of bounds, throw an exception
            if (Double.parseDouble(values[5]) > 90 || Double.parseDouble(values[5]) < -90 ||
                    Double.parseDouble(values[6]) > 180 || Double.parseDouble(values[6]) < -180) {
                throw new DataFormatException("The coordinates do not exist!");
            }
            airports.add(new Airport(values[0], values[1], values[2], values[3], Double.parseDouble(values[5]), Double.parseDouble(values[6])));
        }
        return airports;
    }
}
