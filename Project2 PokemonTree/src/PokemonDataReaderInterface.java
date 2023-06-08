// --== CS400 File Header Information ==--
// Name: Andrew McAvoy
// Email: apmcavoy@wisc.edu
// Team: HA
// Role: red
// TA: None? No TA was contacted
// Lecturer: Gary Dahl
// Notes to Grader: NA
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;
import java.util.List;
import java.util.zip.DataFormatException;
public interface PokemonDataReaderInterface {
    public List<PokemonInterface> readDataSet(Reader reader)throws FileNotFoundException, IOException, DataFormatException;
}
