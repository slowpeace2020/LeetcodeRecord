package unlock;

import java.util.HashMap;
import java.util.LinkedHashSet;

public class LFUCache {
    private int min;
    private final int capacity;
    private HashMap<Integer,Integer> keyToVal;
    private HashMap<Integer,Integer> keyToCount;
    private HashMap<Integer, LinkedHashSet<Integer>> countToLRUKeys;

    public LFUCache(int capacity){
        this.min = -1;
        this.capacity = capacity;
        keyToVal = new HashMap<>();
        keyToCount = new HashMap<>();
        countToLRUKeys = new HashMap<>();
    }

    public int get(int key){
        if(!keyToVal.containsKey(key))return -1;

        int count = keyToCount.get(key);
        countToLRUKeys.get(count).remove(key);
        //当前最不频繁使用次数的列表是空的，需要更新
        if(count==min && countToLRUKeys.get(count).size()==0){
            min++;
        }

        putCount(key,count+1);
        return keyToVal.get(key);
    }

    public void put(int key,int val){
        if(capacity<=0)return;
        if(keyToVal.containsKey(key)){
            keyToVal.put(key,val);
            //更新使用频率
            get(key);
            return;
        }

        if(keyToVal.size()>=capacity){
            //移除最少使用且最近使用时间最远的节点
            evict(countToLRUKeys.get(min).iterator().next());
        }



        //新节点，相当于使用了1次
        min=1;
        putCount(key,min);
        keyToVal.put(key,val);
    }

    private void evict(Integer key) {
        //移除节点
        countToLRUKeys.get(min).remove(key);
        keyToVal.remove(key);
    }

    private void putCount(int key, int count) {
        keyToCount.put(key,count);
        countToLRUKeys.computeIfAbsent(count,ignore->new LinkedHashSet<>());
        countToLRUKeys.get(count).add(key);
    }


}
