package unlock;

import java.util.*;

public class TimeMap {
    Map<String, TreeMap<Integer,String>> map;
    public TimeMap() {
        map = new HashMap<>();
    }

    public void set(String key, String value, int timestamp) {
        TreeMap<Integer,String> timeMap = map.getOrDefault(key,new TreeMap<>());
        timeMap.put(timestamp,value);
        map.put(key,timeMap);
    }

    public String get(String key, int timestamp) {
        TreeMap<Integer,String> timeMap = map.getOrDefault(key,new TreeMap<>());
        Integer time = timeMap.floorKey(timestamp);
        if(time!=null){
            return timeMap.get(time);
        }
        return "";
    }


}
