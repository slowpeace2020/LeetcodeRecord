package unlock;

import algs4.src.main.java.edu.princeton.cs.algs4.In;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

public class SnapshotArray_1146 {

  List<TreeMap<Integer,Integer>> list = new ArrayList<>();
  int snapId = 0;
  public SnapshotArray_1146(int length) {
    for(int i =0;i<length;i++){
      TreeMap<Integer,Integer> map = new TreeMap<>();
      map.put(snapId,0);
      list.add(map);
    }
  }

  public void set(int index, int val) {
    list.get(index).put(snapId,val);
  }

  public int snap() {
    int res= snapId;
    snapId++;
    return res;
  }

  public int get(int index, int snap_id) {
    TreeMap<Integer,Integer> map = list.get(index);
    return map.floorEntry(snap_id).getValue();
  }

}
