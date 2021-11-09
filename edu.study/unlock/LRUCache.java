package unlock;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

public class LRUCache {
    private int capacity;
    LinkedHashMap<Integer,Integer> map;
    public LRUCache(int capacity) {
        this.capacity =capacity;
        this.map = new LinkedHashMap<>();
    }

    public int get(int key) {
        int value = -1;
        if(map.containsKey(key)){
            value = map.get(key);
            map.remove(key);
            map.put(key,value);
        }
        return value;
    }

    public void put(int key, int value) {
        if(map.containsKey(key)){
            map.remove(key);
            map.put(key,value);
        }
        if(map.size()==capacity){
            for(Map.Entry<Integer,Integer> entry:map.entrySet()){
                map.remove(entry.getKey());
                break;
            }
        }
        map.put(key,value);
    }

    public static void main(String[] args) {
        LRUCache test = new LRUCache(2);
        test.put(1,1);
        test.put(2,2);
        test.get(1);
        test.put(3,3);
    }
}
