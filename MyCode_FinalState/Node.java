public class Node<KeyType, ValueType> {
    KeyType key;
    ValueType value;
    public Node(KeyType key, ValueType value){
        this.key = key;
        this.value = value;
    }
    public KeyType getKey(){
        return key;
    }
    public ValueType getValue(){
        return value;
    }
}
