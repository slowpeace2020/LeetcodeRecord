package august;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class SnapshotArray {
    int snap_id;
    List<int[]>[] arr;


    public SnapshotArray(int length){
        arr = new List[length];
        snap_id=0;
        for(int i=0;i<length;i++){
            List<int[]> element = new ArrayList<>();
            element.add(new int[2]);
            arr[i] = element;
        }
    }

    public void set(int index,int val){
        List<int[]> element = arr[index];
        int[] lastSnap =  element.get(element.size()-1);
        if(lastSnap[0]<snap_id){
            element.add(new int[]{snap_id,val});
        }
    }

    public int snap(){
        return snap_id++;
    }

    public int get(int index,int snap_id){
        List<int[]> element = arr[index];
        return binarySearch(element,snap_id);
    }

    private int binarySearch(List<int[]> snaps, int target) {
        int low = 0;
        int high = snaps.size()-1;
        while (low<high){
            int mid = low + (high-low)/2;
            if(snaps.get(mid)[0]<target){
                low = mid+1;
            }else if(snaps.get(mid)[0]==target){
                return snaps.get(mid)[1];
            }else {
                high = mid-1;
            }
        }

        return low==0?snaps.get(0)[1]:snaps.get(low-1)[1];
    }


}
