// --== CS400 File Header Information ==--
// Name: Andrew McAvoy
// Email: apmcavoy@wisc.edu
// Team: HA
// Role: red
// TA: None? No TA was contacted
// Lecturer: Gary Dahl
// Notes to Grader: NA
import java.util.ArrayList;
import java.util.List;

public class Pokemon implements PokemonInterface{
    private String name;
    private List<String> types;
    private int total;
    private int HP;
    private int attack;
    private int defense;
    private int SPAttack;
    private int SPDefense;
    private int speed;

    /**
     * Initializes pokemon object based on data passed to constructor
     * @param data array generated from .csv file containing all of the Pokemon's data
     */
    public Pokemon(String[] data){
        types = new ArrayList<String>();
        name = data[1];
        types.add(data[2]);
        types.add(data[3]);
        total = Integer.parseInt(data[4]);
        HP = Integer.parseInt(data[5]);
        attack = Integer.parseInt(data[6]);
        defense = Integer.parseInt(data[7]);
        SPAttack = Integer.parseInt(data[8]);
        SPDefense = Integer.parseInt(data[9]);
        speed = Integer.parseInt(data[10]);
    }
    @Override
    public String getName() {
        return name;
    }

    @Override
    public List<String> getTypes() {
        return types;
    }

    @Override
    public int getTotal() {
        return total;
    }

    @Override
    public int getHP() {
        return HP;
    }

    @Override
    public int getAttack() {
        return attack;
    }

    @Override
    public int getDefense() {
        return defense;
    }

    @Override
    public int getSPAttack() {
        return SPAttack;
    }

    @Override
    public int getSPDefense() {
        return SPDefense;
    }

    @Override
    public int getSpeed() {
        return speed;
    }

    @Override
    public int compareTo(PokemonInterface other) {
        int diff = this.getTotal() - other.getTotal();
        if(diff == 0) //need to resolve ties in Power level to properly sort an RBT -- we should have no
            //duplicate pokemon names, so this can resolve it by sorting alphabetically after we sort by Power
            return this.getName().compareTo(other.getName());
        return diff;
    }
}
