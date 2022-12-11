package november;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FallingSquare {
    class SementTree {
        int start, end;
        int info;
        boolean isDivide;
        SementTree left, right;

        public SementTree(int start, int end) {
            this.start = start;
            this.end = end;
            this.isDivide = false;
        }

        public SementTree(int start, int end, int info) {
            this.start = start;
            this.end = end;
            this.info = info;
            this.isDivide = false;
        }
    }

    private void update(SementTree root, int start, int end, int height) {
        if (root == null || root.start > end || root.end < start) {
            return;
        }
        if (root.start >= start && root.end <= end) {
            root.info += height;
            root.isDivide = false;
            return;
        }

        int mid = (root.start + root.end) >> 1;
        if (end <= mid) {
            if (root.left == null) {
                root.left = new SementTree(root.start, mid);
                if (!root.isDivide) {
                    root.left.info = root.info;
                }
            }
            root.isDivide = true;
            update(root.left, start, end, height);
        } else if (start > mid) {
            if (root.right == null) {
                root.right = new SementTree(mid + 1, root.end);
                if (!root.isDivide) {
                    root.right.info = root.info;
                }
            }
            root.isDivide = true;
            update(root.right, start, end, height);
        } else {
            if (root.left == null) {
                root.left = new SementTree(root.start, mid);
                if (!root.isDivide) {
                    root.left.info = root.info;
                }
            }
            update(root.left, start, end, height);
            if (root.right == null) {
                root.right = new SementTree(mid + 1, root.end);
                if (!root.isDivide) {
                    root.right.info = root.info;
                }
            }
            root.isDivide = true;
            update(root.right, start, end, height);
        }

        int info = root.info;
        if (root.left != null) {
            info = root.left.info;
        }

        if (root.right != null) {
            info = Math.max(info, root.right.info);
        }

        root.info = info;
    }

    public List<Integer> fallingSquares(int[][] positions) {
        SementTree root = new SementTree(1, (int) 1e8);
        List<Integer> res = new ArrayList<>();
        for (int[] pos : positions) {
            update(root, pos[0], pos[0] + pos[1], pos[1]);
            res.add(root.info);
        }

        return res;
    }


    //    private int query(SementTree root, int start,int end){
//        if(root==null||root.start>end||root.end<start){
//            return 0;
//        }
//
//        if(root.start>=start&&root.end<=end){
//            return root.info;
//        }
//
//        int mid = (root.start+root.end)>>1;
//        if(start>mid){
//            return query(root.right,start,end);
//        }else if(end<=mid){
//            return query(root.left,start,end);
//        }
//
//        return Math.max(query(root.left,start,end),query(root.right,start,end));
//    }

    class SegmentNode{
        public SegmentNode left , right;
        public int start, end, maxHeight;
        public SegmentNode(int start, int end, int maxHeight){
            this.start = start;
            this.end = end;
            this.maxHeight = maxHeight;
        }
    }

    public int query(SegmentNode root, int start, int end){
        if(start<=root.start && end>=root.end) return root.maxHeight;
        if(start>=root.end || end<=root.start) return  0;
        if (root.left==null) return root.maxHeight;
        int mid = root.start + (root.end - root.start) / 2;
        if (end <= mid) {
            return query(root.left, start, end);
        } else if (start >= mid) {
            return query(root.right, start, end);
        }
        return Math.max(query(root.left,start,mid),query(root.right,mid,end));
    }

    public void add(SegmentNode root, int start, int end, int maxHeight){
        if(start<=root.start && end>=root.end){
            root.maxHeight = maxHeight;
            root.left = null;
            root.right = null;
            return;
        }
        if(start>=root.end || root.start>=end) return;
        if(root.left==null){
            int mid = root.start + (root.end - root.start) / 2;
            root.left = new SegmentNode(root.start,mid,0);
            root.right = new SegmentNode(mid,root.end,0);
        }
        add(root.left,start,end,maxHeight);
        add(root.right,start,end,maxHeight);
        root.maxHeight = Math.max(root.left.maxHeight,root.right.maxHeight);
    }

    public List<Integer> fallingSquaresII(int[][] positions) {
        SegmentNode root = new SegmentNode(0,Integer.MAX_VALUE,0);
        List<Integer> ans = new ArrayList<>();
        int max = 0;
        for(int[] p : positions){
            int left = p[0], height = p[1], right = left + height;
            int maxHeight = query(root, left, right) + height;
            max = Math.max(max,maxHeight);
            ans.add(max);
            add(root, left, right, maxHeight);
        }
        return ans;
    }


    public static void main(String[] args) {
        FallingSquare square = new FallingSquare();
        square.fallingSquares(new int[][]{{1, 2}, {2, 3}, {6, 1}});
    }

}
