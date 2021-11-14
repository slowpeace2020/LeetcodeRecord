package unlock;

import algs4.src.main.java.edu.princeton.cs.algs4.In;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RangeModule {
  class Interval{
    int start;
    int end;
    public Interval(int start, int end){
      this.start = start;
      this.end = end;
    }
  }

  class IntervalComparator implements Comparator<Interval>  {

    @Override
    public int compare(Interval a, Interval b) {
      if(a.start!=b.start){
        return a.start-b.start;
      }

      return a.end-b.end;
    }
  }

  List<Interval> list;
  public RangeModule() {
    list = new ArrayList<>();
  }

  public void addRange(int left, int right) {
    list.add(new Interval(left,right));
    adjustRange();
  }

  public boolean queryRange(int left, int right) {
      for(int i=0;i<list.size();i++){
        Interval cur = list.get(i);
        if(cur.start<=left&&cur.end>=right){
          return true;
        }
      }

      return false;
  }

  public void removeRange(int left, int right) {
    for(int i=0;i<list.size();i++){
      Interval cur = list.get(i);
      if(cur.end<=left){
        continue;
      }
      if(cur.start<=left||cur.end>=right){
        int low = Math.max(cur.start,left);
        int high = Math.min(cur.end,right);
        if(low<high){
          list.remove(i);
          if(cur.start<left){
            list.add(new Interval(Math.min(cur.start,left),low));
          }
          if(cur.end>right){
            list.add(new Interval(high,Math.max(cur.end,right)));
          }
          break;
        }
      }
    }

    adjustRange();

  }

  public void adjustRange(){
    if(list.size()==0){
      return;
    }

    List<Interval> res = new ArrayList<>();
    Collections.sort(list,new IntervalComparator());

    Interval point = list.get(0);
    for(int i=1;i<list.size();i++){
      Interval cur = list.get(i);
      if(cur.start>point.end){
        res.add(point);
        point = cur;
      }else {
        point.end = Math.max(point.end,cur.end);
      }
    }

    res.add(point);
    list = res;

  }


  public static void main(String[] args) {
    RangeModule rangeModule = new RangeModule();
    rangeModule.addRange(8,9);
    rangeModule.queryRange(1,8);
    rangeModule.removeRange(1,8);
    rangeModule.queryRange(5,8);
    rangeModule.removeRange(3,9);
    rangeModule.addRange(8,9);
    rangeModule.queryRange(4,5);
    rangeModule.removeRange(2,9);
    rangeModule.addRange(5,6);

  }

}
