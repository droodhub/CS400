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
import java.util.ArrayList;
import java.util.List;
import java.util.zip.DataFormatException;
import java.util.Scanner;

public class PokemonDataReader implements PokemonDataReaderInterface{
    /**
     *
     * @param reader Reader object that will parse the data passed to it
     * @return List of Pokemon objects sorted by the reader object
     * @throws FileNotFoundException throw if Reader's file does not exist
     * @throws IOException reader.close() method may throw an IOException
     * @throws DataFormatException throw if data in Reader isn't the correct length to create a Pokemon object
     */
    @Override
    public List<PokemonInterface> readDataSet(Reader reader) throws FileNotFoundException, IOException, DataFormatException {
        List<PokemonInterface> pokeList = new ArrayList<PokemonInterface>();
        Scanner reading = new Scanner(reader);
        if(reading.hasNextLine())
            reading.nextLine();
        while(reading.hasNextLine()){
            String pokeData = reading.nextLine();
            for(int i = 0; i < pokeData.length(); i++){
                if(pokeData.charAt(i) == ','){
                    pokeData = pokeData.substring(0, i) + "_" + pokeData.substring(i+1, pokeData.length());
                }
            }
            String[] pokeStats = pokeData.split("_");
            if(pokeStats.length != 11) {
                System.out.println("Incorrect Pokemon stats length: " + pokeStats.length);
                reader.close();
                throw new DataFormatException("Improper amount of data to create a pokemon");
            }
            PokemonInterface pokemon = new Pokemon(pokeStats);
            pokeList.add(pokemon);
            }
        reader.close();
        return pokeList;
        }

    }
