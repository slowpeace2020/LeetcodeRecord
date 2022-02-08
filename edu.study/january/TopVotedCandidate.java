package january;

import java.util.HashMap;
import java.util.TreeMap;

public class TopVotedCandidate {
    TreeMap<Integer,Integer> tmap;
    HashMap<Integer,Integer> map;
    public TopVotedCandidate(int[] persons, int[] times) {
        tmap = new TreeMap<>();
        map = new HashMap<>();
        int votes=0;
        int person=-1;
        int n = persons.length;
        for(int i=0;i<n;i++){
            int cur = persons[i];
            int time = times[i];
            map.put(cur,map.getOrDefault(cur,0)+1);
            if(map.get(cur)>=votes){
                person = cur;
                votes=map.get(cur);
            }
            tmap.put(time,person);
        }
    }

    public int q(int t) {
        return tmap.floorEntry(t).getValue();
    }



    public static void main(String[] args) {
        TopVotedCandidate top = new TopVotedCandidate(new int[]{0, 0, 0, 0, 1}, new int[]{0, 6, 39, 52, 75});
        top.q(99);
        top.q(78);
    }
}
