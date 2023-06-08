// --== CS400 File Header Information ==--
// Name: Vivian Lacson
// Email: vlacson@wisc.edu
// Team: HA
// TA: Hang Yin
// Lecturer: Florian Heimerl
// Notes to Grader: N/A

import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.zip.DataFormatException;

public interface AirportDataReaderInterface {
    public List<AirportInterface> readDataSet(FileReader inputFileReader) throws IOException, DataFormatException;
}