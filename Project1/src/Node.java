// --== CS400 File Header Information ==--
// Name: Jonathan Moskal
// Email: jmoskal@wisc.edu
// Team: Red
// Group: HA
// TA: Hang Yin
// Lecturer: Florian Heimerl
// Notes to Grader: None

class LinkedNode<KeyType, ValueType> {
    protected KeyType key;
    protected ValueType value;

    /**
     * Constructor that sets both data fields to the parameters
     *
     * @param key   the key to use when hashing to obtain an array index
     * @param value the value to be stored
     */
    public LinkedNode(KeyType key, ValueType value) {
        this.key = key;
        this.value = value;
    }

}
