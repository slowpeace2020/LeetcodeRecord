package winter;

import java.util.TreeMap;

public class MyCalendarTwo {
    class SegmentNode{
        int start;
        int end;
        int info;
        SegmentNode left,right;
        public SegmentNode(int start,int end,int info){
            this.start = start;
            this.end = end;
            this.info = info;
        }
    }
    SegmentNode root;
//    public MyCalendarTwo() {
//        root = new SegmentNode(0,(int)(1e9+1),0);
//    }


    TreeMap<Integer,Integer> treeMap;
    public MyCalendarTwo() {
        treeMap = new TreeMap<>();
    }

    public boolean book(int start, int end) {
        int count =0;
        treeMap.put(start,treeMap.getOrDefault(start,0)+1);
        treeMap.put(end,treeMap.getOrDefault(end,0)-1);

        for(Integer timepoint:treeMap.keySet()){
            count+=treeMap.get(timepoint);
            if(count>=3){
                treeMap.put(start,treeMap.getOrDefault(start,0)-1);
                treeMap.put(end,treeMap.getOrDefault(end,0)+1);
            }
            return false;
        }

        return true;
    }
    public boolean bookI(int start, int end) {
        System.out.println(start+","+end);
        int count = query(root,start,end);
        if(count>=2){
            System.out.println(false);
            return false;
        }
        add(root,start,end);
        System.out.println(true);
        return true;
    }

    private void add(SegmentNode node,int start,int end){
        if(node.start>=end||node.end<=start){
            return;
        }
        if(node.start>=start&&node.end<=end){
            node.info++;
            return;
        }

        int mid = node.start+(node.end-node.start)/2;
        if(node.left==null){
            node.left = new SegmentNode(node.start,mid,node.info);
            node.right = new SegmentNode(mid,node.end,node.info);
        }

        if(start>=mid){
            add(node.right,start,end);
        }else if(end<=mid){
            add(node.left,start,end);
        }else{
            add(node.left,start,end);
            add(node.right,start,end);
        }

        node.info = Math.max(node.left.info,node.right.info);
    }

    private int query(SegmentNode node,int start, int end){
        if(node.start>=end||node.end<=start){
            return 0;
        }

        if((node.start>=start&&node.end<=end)||node.left==null){
            return node.info;
        }

        int mid = node.start+(node.end-node.start)/2;
        if(end<=mid){
            return query(node.left,start,end);
        }else if(start>=mid){
            return query(node.right,start,end);
        }

        return Math.max(query(node.left,start,end),query(node.right,start,end));
    }

    public static void main(String[] args) {
        MyCalendarTwo two = new MyCalendarTwo();
        two.book(10,20);
        two.book(50,60);
        two.book(10,40);
        two.book(5,15);
        two.book(5,10);
        two.book(25,55);
    }
}
