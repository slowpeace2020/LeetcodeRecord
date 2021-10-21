package amazon;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class LRUCache {
    private int capacity;
    private Map<Integer, Pair> map;
    private PriorityQueue<Map.Entry<Integer,Pair>> queue;
    public LRUCache(int capacity) {
        this.capacity = capacity;
        this.map = new HashMap<>();
        this.queue = new PriorityQueue<>(new Comparator<>() {
            @Override
            public int compare(Map.Entry<Integer,Pair> o1, Map.Entry<Integer,Pair> o2) {
                return o1.getValue().count-o2.getValue().count;
            }
        });
    }

    public int get(int key) {
        if(map.containsKey(key)){
            int value = map.get(key).value;

            int count = 0;
            if(!queue.isEmpty()){
                count = queue.peek().getValue().count+1;
            }
            map.put(key,new Pair(value,count));
            return value;
        }
        return -1;
    }

    public void put(int key, int value) {
        if(map.size()>=this.capacity){
            queue.addAll(map.entrySet());
            Map.Entry<Integer,Pair> out = queue.poll();
            map.remove(out.getKey());
        }
        map.put(key,new Pair(value,0));
    }

    class Pair{
        private int value;
        private int count;
        public Pair(int value,int count){
            this.value = value;
            this.count = count;
        }
    }

    public static void main(String[] args) {
        LRUCache test = new LRUCache(2);
        test.put(1,1);
        test.put(2,2);
        test.get(1);
        test.put(3,3);
        test.get(2);
        test.put(4,4);
        test.get(1);
        test.get(3);
        test.get(4);

    }
}
