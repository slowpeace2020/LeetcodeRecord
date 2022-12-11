package november;

import java.util.HashMap;
import java.util.Map;

public class RangeFreqQuery {
    SegmentTree root;
    public RangeFreqQuery(int[] arr) {
        int n = arr.length;
        root = new SegmentTree(0,n-1);
        for(int i=0;i<n;i++){
            update(root,i,arr[i]);
        }
    }

    public int query(int left, int right, int value) {
        return query(root,left,right,value);
    }

    private int query(SegmentTree root, int left,int right,int value){
        if(root==null||root.start>right||root.end<left||!root.map.containsKey(value)){
            return 0;
        }

        if(root.start>=left&&root.end<=right){
            return root.map.getOrDefault(value,0);
        }

        int mid = (root.start+root.end)>>1;
        if(left>mid){
            return query(root.right,left,right,value);
        }else if(right<=mid){
            return query(root.left,left,right,value);
        }

        return query(root.left,left,right,value)+query(root.right,left,right,value);
    }

    private void update(SegmentTree root,int index,int val){
        root.map.put(val,root.map.getOrDefault(val,0)+1);
        if(root.start==root.end){
            return;
        }

        int mid = (root.start+root.end)>>1;
        if(index<=mid){
            if(root.left==null){
                root.left = new SegmentTree(root.start,mid);
            }
            update(root.left,index,val);
        }else {
            if(root.right==null){
                root.right = new SegmentTree(mid+1,root.end);
            }
            update(root.right,index,val);
        }
    }

    class SegmentTree{
        int start,end;
        SegmentTree left,right;
        Map<Integer,Integer> map;
        public SegmentTree(int start,int end){
            map = new HashMap<>();
            this.start = start;
            this.end = end;
            left = null;
            right = null;
        }
    }

    public static void main(String[] args) {
//        ["RangeFreqQuery","query","query"]
//[[[12,33,4,56,22,2,34,33,22,12,34,56]],[1,2,4],[0,11,33]]
        RangeFreqQuery query = new RangeFreqQuery(new int[]{12,33,4,56,22,2,34,33,22,12,34,56});
        query.query(1,2,4);
    }
}
