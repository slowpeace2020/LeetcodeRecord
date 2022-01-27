package winter;

import java.util.ArrayList;
import java.util.List;

public class Square {
    public List<Integer> fallingSquares(int[][] positions) {
        SegmentNode root = new SegmentNode(0,Integer.MAX_VALUE,0);
        List<Integer> ans = new ArrayList<>();
        int max = 0;
        for(int[] p : positions){
            int left = p[0], height = p[1], right = left + height;
            //当前方块下落之后的最大高度为区间的最大高度加上方块的高度
            int maxHeight = query(root, left, right) + height;
            max = Math.max(max,maxHeight);
            ans.add(max);
            //更新区间的高度为新高度
            add(root, left, right, maxHeight);
        }
        return ans;
    }

    public int query(SegmentNode root,int start,int end){
        if(start<=root.start&&end>=root.end){
            return root.maxHeight;
        }

        if(start>=root.end||end<=root.start){
            return 0;
        }

        //左右子区间成对出现，没有细分说明parent高度保持一致
        if(root.left==null){
            return root.maxHeight;
        }
        //分别查找落在左右子区间的最大高度
        int mid = root.start+(root.end-root.start)/2;
        if(end<=mid){
            return query(root.left,start,end);
        }else if(start>=mid){
            return query(root.right,start,end);
        }

        return Math.max(query(root.left,start,end),query(root.right,start,end));
    }

    public void add(SegmentNode root,int start,int end,int maxHeight){
        if(start<=root.start&&end>=root.end){
            root.maxHeight = maxHeight;
            root.left = null;
            root.right=null;
            return;
        }

        if(start>=root.end||root.start>=end){
            return;
        }

        //左右子区间成对出现，没有细分说明parent高度保持一致
        if(root.left==null){
            int mid = root.start+(root.end-root.start)/2;
            root.left=new SegmentNode(root.start,mid,0);
            root.right=new SegmentNode(mid,root.end,0);
        }

        add(root.left,start,end,maxHeight);
        add(root.right,start,end,maxHeight);
        root.maxHeight = Math.max(root.left.maxHeight,root.right.maxHeight);
    }

    class SegmentNode{
        public SegmentNode left,right;
        public int start,end,maxHeight;
        public SegmentNode(int start,int end,int maxHeight){
            this.start = start;
            this.end= end;
            this.maxHeight = maxHeight;
        }
    }
}
