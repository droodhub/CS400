import java.util.NoSuchElementException;

public class TestHashTable {
    public static void main(String[] args){
        System.out.println(test1());
        System.out.println(test2());
        System.out.println(test3());
        System.out.println(test4());
        System.out.println(test5());
    }
    //tests the put method
    public static boolean test1(){
        HashTableMap<Integer, Integer> test = new HashTableMap<Integer, Integer>();
       test.put(7, 7);
        test.put(8, 10);
        test.put(6, 4);
        test.put(11, 5);
        test.put(88, 17);
        if(test.size() != 5) {
            System.out.println("Put and size working improperly");
            return false;
        }
        return true;
    }
    //tests putting the same key in multiple times and resizing the array
    public static boolean test2(){
        HashTableMap<Integer, Integer> test = new HashTableMap<Integer, Integer>();
        test.put(7, 7);
        test.put(8, 10);
        test.put(6, 4);
        test.put(11, 5);
        test.put(88, 17);
        test.put(12, 69);
        test.put(1, 1);
        test.put(14, 89);
        test.put(22, 77);
        test.put(102, 170);
        test.put(200, 200);
        if(test.size() != 11){
            System.out.println("Resizing not working correctly");
            return false;
        }
          if(test.put(200, 200)){
              System.out.println("Duplicate keys allowed into the array");
              return false;
          }
          return true;
    }
    //test ContainsKey() method
    public static boolean test3(){
        HashTableMap<Integer, Integer> test = new HashTableMap<Integer, Integer>(25);
        test.put(7, 7);
        test.put(8, 10);
        test.put(6, 4);
        test.put(11, 5);
        test.put(88, 17);
        test.put(12, 69);
        test.put(1, 1);
        test.put(14, 89);
        test.put(22, 77);
        test.put(102, 170);
        if(!test.containsKey(7)){
            System.out.println("ContainKey returns false when true expected");
            return false;
        }
        if(test.containsKey(99)){
            System.out.println("ContainsKey returns true when false expected");
            return false;
        }
        return true;
    }
    //tests remove method and get method
    public static boolean test4(){
        HashTableMap<Integer, Integer> test = new HashTableMap<Integer, Integer>(25);
        test.put(7, 7);
        test.put(8, 10);
        test.put(6, 4);
        test.put(11, 5);
        test.put(88, 17);
        test.put(12, 69);
        test.put(1, 1);
        test.put(14, 89);
        test.put(22, 77);
        test.put(102, 170);
        if(test.get(22) != 77){
            System.out.println("Incorrect value returned for get(22)");
            return false;
        }
        try{test.get(0);
        return false;}
        catch(NoSuchElementException e){
            System.out.println("Caught exception when calling get() for a key not in the array");
        }
        if(test.remove(22) != 77){
            System.out.println("Incorrect value returned for remove(22)");
            return false;
        }
        if(test.remove(0) != null){
            System.out.println("Remove() return for a key not in the array was not null");
            return false;
        }
        return true;
    }
    //tests clear method
    public static boolean test5(){
        HashTableMap<Integer, Integer> test = new HashTableMap<Integer, Integer>(25);
        test.put(7, 7);
        test.put(8, 10);
        test.put(6, 4);
        test.put(11, 5);
        test.put(88, 17);
        test.put(12, 69);
        test.put(1, 1);
        test.put(14, 89);
        test.put(22, 77);
        test.put(102, 170);
        if(test.size() != 10)
            return false;
        test.clear();
        if(test.size() != 0)
            return false;
        if(test.containsKey(11))
            return false;
        return true;
    }
}
