// --== CS400 File Header Information ==--
// Name: Jonathan Moskal
// Email: jmoskal@wisc.edu
// Team: Red
// Group: HA
// TA: Hang Yin
// Lecturer: Florian Heimerl
// Notes to Grader: None

import java.util.LinkedList;
import java.util.NoSuchElementException;

/**
 * This class models a hash map data structure as defined in the MapADT interface
 *
 * @author Jonathan Moskal
 */
public class HashTableMap<KeyType, ValueType> implements MapADT<KeyType, ValueType> {

    private LinkedList<LinkedNode<KeyType, ValueType>>[] hashMap; // stores all ValueType's as indexed
    // by their KeyType
    private int size = 0; // the number of values currently stored in the hash map
    // the percentage full the hash map can become before resizing occurs
    private final double LOAD_THRESHOLD = .85;
    private int capacity; // the current capacity of the hash map

    /**
     * Constructor that sets the hash map's starting capacity to the given number
     *
     * @param capacity the initial hash map capacity
     * @throws IllegalArgumentException if the provided capacity is 0 or negative
     */
    @SuppressWarnings("unchecked") // workaround used to have an array of linked lists
    public HashTableMap(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be greater than zero!");
        }

        this.hashMap = (LinkedList<LinkedNode<KeyType, ValueType>>[]) new LinkedList[capacity];
        this.capacity = capacity;
    }

    /**
     * Constructor that sets the hash map's starting capacity to 10
     */
    @SuppressWarnings("unchecked") // workaround used to have an array of linked lists
    public HashTableMap() {
        this.hashMap = (LinkedList<LinkedNode<KeyType, ValueType>>[]) new LinkedList[10];
        this.capacity = 10;
    }

    /**
     * Adds an element at the hashed index to the collection
     *
     * @param key   the key to use when hashing to obtain an array index
     * @param value the value to be stored
     * @returns true if the value was added to the array, else false
     */
    @Override
    public boolean put(KeyType key, ValueType value) {
        if (key == null || this.containsKey(key)) { // do not store null or identical key-value pairs
            return false;
        }

        if (hashMap[this.hashCode(key)] == null) { // if a chain is not started, start one
            hashMap[this.hashCode(key)] = new LinkedList<LinkedNode<KeyType, ValueType>>();
        }

        // add the pair to the end of the chain
        hashMap[this.hashCode(key)].addLast(new LinkedNode<KeyType, ValueType>(key, value));

        this.size++;

        if (this.checkThreshold()) {
            this.resize();
        }

        return true;
    }

    /**
     * Accesses the value associated with its key
     *
     * @param key the key to use to get the index of the value
     * @returns a reference to the value stored or null if the key doesn't address a value
     * @throws NoSuchElementException when the value is not in the hash map
     */
    @Override
    public ValueType get(KeyType key) throws NoSuchElementException {
        if (!this.containsKey(key)) {
            throw new NoSuchElementException("The desired value is not stored in the map!");
        }

        int elementNum = 0;
        while (true) {
            if (!hashMap[this.hashCode(key)].get(elementNum).key.equals(key)) { // while the keys don't
                // match
                elementNum++;
                continue;
            }
            return hashMap[this.hashCode(key)].get(elementNum).value;
        }

    }

    /**
     * Gets the current size of the hash map
     *
     * @returns the number of values currently stored in the hash map
     */
    @Override
    public int size() {
        return this.size;
    }

    /**
     * Checks whether a given key addresses a value within the hash map
     *
     * @param key the key to hash in order to address the collection
     * @returns true if the key addresses a value in the hash map, else false
     */
    @Override
    public boolean containsKey(KeyType key) {
        if (key == null) { // their is no associated value with a null key
            return false;
        }

        if (this.hashMap[hashCode(key)] == null) { // no chain started at this address
            return false;
        }

        // progress down the chain (if it exists) until reaching its end
        for (int i = 0; i < this.hashMap[this.hashCode(key)].size(); i++) {
            if (this.hashMap[this.hashCode(key)].get(i).key.equals(key)) { // the key is present
                return true;
            }
        }

        return false; // the key is not present
    }

    /**
     * Removes the value associated with the given key from the hash map
     *
     * @param key the key to hash in order to address the colleciton
     * @returns a reference to the value removed or null if there was no such element
     */
    @Override
    public ValueType remove(KeyType key) {
        if (!this.containsKey(key)) {
            return null;
        }

        LinkedList<LinkedNode<KeyType, ValueType>> chain = hashMap[this.hashCode(key)];

        // find the index of the key to remove
        int elementNum = 0;
        while (true) {
            if (hashMap[this.hashCode(key)].get(elementNum).key.equals(key)) { // the index was reached
                break;
            }

            elementNum++; // check the next element in the chain
        }

        // store the ValueType object to be returned
        ValueType valueRemoved = hashMap[this.hashCode(key)].get(elementNum).value;

        // call LinkedList's remove() method to remove the LinkedNode
        chain.remove(elementNum);
        this.size--;

        return valueRemoved;
    }

    /**
     * Clears the hash map of all key-value pairs
     *
     */
    @Override
    @SuppressWarnings("unchecked") // workaround used to have an array of linked lists
    public void clear() {
        this.hashMap = (LinkedList<LinkedNode<KeyType, ValueType>>[]) new LinkedList[this.capacity];
        this.size = 0;
    }

    /**
     * Hashes the key in order to obtain a valid address within the array
     *
     * @param key the key to hash in order to address the collection
     * @returns the index within the array that the value will be stored in
     */
    private int hashCode(KeyType key) {
        return Math.abs(key.hashCode()) % this.capacity;
    }

    /**
     * Checks whether the hashmap load factor is above the set threshold. Used to determine if
     * resizing is necessary
     *
     * @returns true when the load factor is above (or equal to) the threshhold, else false
     */
    private boolean checkThreshold() {
        if ((double) this.size / this.capacity >= this.LOAD_THRESHOLD) {
            return true;
        }

        return false;
    }

    /**
     * Doubles the size of the hashmap and re-indexes all key-value pairs
     *
     */
    @SuppressWarnings("unchecked") // workaround used to have an array of linked lists
    private void resize() {
        this.capacity = 2 * this.capacity; // double the capacity
        // create a double-sized array
        LinkedList<LinkedNode<KeyType, ValueType>>[] newHashMap =
                (LinkedList<LinkedNode<KeyType, ValueType>>[]) new LinkedList[this.capacity];

        // copy over every element of the old hashMap into the new hashmap
        for (int i = 0; i < this.capacity / 2; i++) { // for every chain
            if (hashMap[i] != null) { // if there is a chain at that index
                for (LinkedNode<KeyType, ValueType> node : hashMap[i]) { // for every element in the chain
                    if (newHashMap[this.hashCode(node.key)] == null) { // if a chain is not started, start one
                        newHashMap[this.hashCode(node.key)] = new LinkedList<LinkedNode<KeyType, ValueType>>();
                    }
                    // add the pair to the end of the chain
                    newHashMap[this.hashCode(node.key)]
                            .addLast(new LinkedNode<KeyType, ValueType>(node.key, node.value));
                }
            }
        }

        this.hashMap = newHashMap;
    }

    /**
     * Capacity accessor method used for testing purposes
     *
     * @returns the current hash map capacity
     */
    protected int getCapacity() {
        return this.capacity;
    }

}
