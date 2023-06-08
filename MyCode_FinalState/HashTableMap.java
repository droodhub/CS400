import java.util.NoSuchElementException;
import java.util.LinkedList;
public class HashTableMap<KeyType, ValueType> implements MapADT<KeyType, ValueType> {
    private int capacity;
    private int size;
    private LinkedList<Node>[] data;

    public HashTableMap(int capacity){
    this.capacity = capacity;
    data = (LinkedList<Node>[]) new LinkedList[capacity];
    for(int i = 0; i < data.length; i++)
        data[i] = new LinkedList<Node>();
    }

    public HashTableMap(){
    capacity = 10;
    data = (LinkedList<Node>[]) new LinkedList[capacity];
    for(int i = 0; i < data.length; i++)
        data[i] = new LinkedList<Node>();
    }

    @Override
    public boolean put(KeyType key, ValueType value) {
        if(key == null || containsKey(key))
            return false;
    Node n = new Node(key, value);
    int index = Math.abs(key.hashCode() % capacity);
    data[index].add(n);
    size++;
    if((double)size/(double)capacity >= .85){
       data = resize();

    }
    return true;
    }
    private LinkedList<Node>[] resize(){
        LinkedList<Node>[] newData = (LinkedList<Node>[]) new LinkedList[capacity*2];
        for(int i = 0; i < newData.length; i++)
            newData[i] = new LinkedList<Node>();
        capacity = 2*capacity;
        int oldSize = size;
        size = 0;
        for(int i = 0; i < oldSize; i++){
            for(int j = 0; j < data[i].size(); j++){
               int index = Math.abs(data[i].get(j).getKey().hashCode() % capacity);
                Node n = new Node(data[i].get(j).getKey(), data[i].get(j).getValue());
               newData[index].add(n);
               size++;
            }
        }
        return newData;
    }
    @Override
    public ValueType get(KeyType key) throws NoSuchElementException {
        int index = Math.abs(key.hashCode() % capacity);
        for(int i = 0; i < data[index].size(); i++){
        if(data[index].get(i).getKey().equals(key))
            return (ValueType) data[index].get(i).getValue();
        }
        throw new NoSuchElementException("The key does not exist in the array");
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean containsKey(KeyType key) {
        int index = Math.abs(key.hashCode() % capacity);
        if(data[index] == null)
            return false;
            for (int i = 0; i < data[index].size(); i++) {
                if (data[index].get(i).getKey().equals(key))
                    return true;
            }

        return false;
    }

    @Override
    public ValueType remove(KeyType key) {
        int index = key.hashCode() % capacity;
        for(int i = 0; i < data[index].size(); i++){
            if(data[index].get(i).getKey().equals(key)) {
                ValueType v = (ValueType) data[index].get(i).getValue();
                data[index].remove(i);
                size--;
                return v;
            }
        }
        return null;
    }

    @Override
    public void clear() {
    for(int i = 0; i < data.length; i++)
        data[i] = null;
        size = 0;
    }

}
