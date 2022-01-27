package winter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.TreeMap;

public class TopVotedCandidate {

    HashMap<Integer,Integer> map;
    private int[] times;

    public TopVotedCandidate(int[] persons, int[] times) {
       map = new HashMap<>();
       this.times = times;
       int[] count= new int[persons.length];
       int winner = -1;
       for(int i=0;i<times.length;i++){
           count[persons[i]]++;
           if(map.isEmpty()||count[winner]<=count[persons[i]]){
               winner = persons[i];
           }
           map.put(times[i],winner);
       }
    }

    public int q(int t) {
    int index = Arrays.binarySearch(times,t);
    return map.get(times[index<0?-index-2:index]);
    }



}
