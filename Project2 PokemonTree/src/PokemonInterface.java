// --== CS400 File Header Information ==--
// Name: Andrew McAvoy
// Email: apmcavoy@wisc.edu
// Team: HA
// Role: red
// TA: None? No TA was contacted
// Lecturer: Gary Dahl
// Notes to Grader: NA
import java.util.List;

public interface PokemonInterface extends Comparable<PokemonInterface>{
    public String getName();
    public List<String> getTypes();
    public int getTotal();
    public int getHP();
    public int getAttack();
    public int getDefense();
    public int getSPAttack();
    public int getSPDefense();
    public int getSpeed();
    public int compareTo(PokemonInterface other);
}
