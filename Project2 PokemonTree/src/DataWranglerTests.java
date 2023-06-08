// --== CS400 File Header Information ==--
// Name: Andrew McAvoy
// Email: apmcavoy@wisc.edu
// Team: HA
// Role: red
// TA: None? No TA was contacted
// Lecturer: Gary Dahl
// Notes to Grader: NA
import org.junit.Test;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Collections;
import java.util.List;
import java.util.zip.DataFormatException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DataWranglerTests {
	/**
	 * makes sure that only the desired data from .csv file is used
	 * must ignore first line in the file and first csv field in each line(1, 2, 3, etc.)
	 */
    @Test
    public void testInitializeData(){
    	try {
			PokemonDataReaderInterface list = new PokemonDataReader();
    		FileReader f = new FileReader("smallPokemon.csv");
			List<PokemonInterface> data = list.readDataSet(f);
			assertEquals("Bulbasaur", data.get(0).getName());
			assertEquals(62, data.get(1).getAttack());
		}
    	catch(Exception e){
    		e.printStackTrace();
    		fail();
		}
    }

	/**
	 * tests Pokemon class' compareTo -- both for an actual difference in Power, and for how it handles ties.
	 */
	@Test
    public void testCompareTo(){
	PokemonInterface pokemon1 = new Pokemon(new String[] {"1", "Charizard", "Fire", "Flying", "50", "20", "20", "3", "1", "1", "5"}); //will change once data format is decided upon
	PokemonInterface pokemon2 = new Pokemon(new String[] {"2", "Blastoise", "Water", "", "50", "20", "10", "15", "0", "4", "1"});; //same as above
	PokemonInterface pokemon3 = new Pokemon(new String[] {"3", "Lugia", "Dragon", "Water", "75", "25", "20", "20", "5", "3", "2"});; //same as above
	assertEquals(pokemon1.getName().compareTo(pokemon2.getName()), pokemon1.compareTo(pokemon2));
	assertEquals(-25, pokemon1.compareTo(pokemon3));
	//our compareTo() is designed to never return a tie
    }

	/**
	 * Tests to make sure that the proper exception is thrown when the data doesn't match what is desired
	 */
    @Test
    public void testReadBadData(){ //makes sure proper exception is thrown when a bad file is passed to readDataSet
	try {
		FileReader f = new FileReader("badData.csv");//intentionally removed speed field from file
		assertThrows(DataFormatException.class, () -> {
			PokemonDataReaderInterface runner = new PokemonDataReader();
			List<PokemonInterface> p = runner.readDataSet(f);
		});
	}
	catch(FileNotFoundException e){
		System.out.println("Incorrect Exception thrown");
		fail();
	}
    }

	/**
	 * tests to make sure that readDataSet reads all of the file and that it compareTo() correctly sorts Pokemon
	 */
    @Test
    public void testReadData(){
	try{
		FileReader f = new FileReader("pokemon.csv");
		PokemonDataReaderInterface p = new PokemonDataReader();
	    List<PokemonInterface> data = p.readDataSet(f);
	    assertEquals(data.size(), 151);
	    Collections.sort(data);
	    Collections.reverse(data);
	    assertEquals(data.get(0).getName(), "Mewtwo");//will be changed once file is created and strongest pokemon is determined
	}
	catch(Exception e) {e.printStackTrace();fail();}
    }

	/**
	 * tests Pokemon's constructor and accessor methods for accuracy
	 */
    @Test
    public void testPokemon(){
	PokemonInterface p = new Pokemon(new String[]{"17", "Venusaur", "Grass", "", "175", "50", "33", "28", "17", "32", "15"}); //will change when data format is finalized
	//will change null vals to proper values for pokemon object
	assertEquals(50, p.getHP());
	    assertEquals(33, p.getAttack());
	    assertEquals(28, p.getDefense());
	    assertEquals(175, p.getTotal());
	   

    }
}
